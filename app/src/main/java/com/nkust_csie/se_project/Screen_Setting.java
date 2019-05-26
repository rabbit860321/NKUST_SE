package com.nkust_csie.se_project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Screen_Setting extends AppCompatActivity {

    SQLiteDB DH = null;
    SQLiteDatabase db;

    Button btn_account;
    Button btn_save;

    ListView itlist_account;

    Cursor settingCursor;
    ContentValues settingvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_setting);

        // 隱藏title Bar
        getSupportActionBar().hide();

        DH = new SQLiteDB(this);
        db = DH.getWritableDatabase();

        settingvalue = new ContentValues();
        btn_account = (Button)findViewById(R.id.btn_account);
        btn_save = (Button)findViewById(R.id.btn_save);

        itlist_account = (ListView)findViewById(R.id.itlist_account);


        settingCursor = db.query("tb_setting",null,null,null,null,null,null);  //查詢tb_setting所有資料
        Log.e("TAG","tb_setting裡有"+settingCursor.getCount()+"筆資料");

        if(settingCursor.getCount() != 0){
            show_account_list();
        }

        btn_account.setOnClickListener(new View.OnClickListener() {  //新增帳戶
            @Override
            public void onClick(View v) {      //新增帳戶
                showdialog(R.layout.dialog_account,1);
            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {   //儲存鍵
            @Override
            public void onClick(View v) {
                settingCursor = db.query("tb_setting",null,null,null,null,null,null);
                if(settingCursor.getCount() == 0){
                    Toast toast = Toast.makeText(Screen_Setting.this,
                            "請新增至少一筆帳戶資料!", Toast.LENGTH_LONG);
                    //顯示Toast
                    toast.show();
                }else{
                    final Intent mainIntent = new Intent(Screen_Setting.this,Screen_Main1.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        });

        itlist_account.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final TextView account_list_id = (TextView) view.findViewById(R.id.account_list_id);            //list item 裡text的資料
                final TextView account_list_name = (TextView) view.findViewById(R.id.account_list_name);
                final TextView account_list_money = (TextView) view.findViewById(R.id.account_list_money);

                LayoutInflater inflater = LayoutInflater.from(Screen_Setting.this);
                final View EntryView = inflater.inflate(R.layout.dialog_account_edit,null);

                AlertDialog.Builder dialog = new AlertDialog.Builder(Screen_Setting.this);
                dialog.setView(EntryView);

                final EditText account_name = EntryView.findViewById(R.id.account_name);         //編輯帳戶dialog裡兩個edit
                final EditText account_money = EntryView.findViewById(R.id.account_money);

                account_name.setText(account_list_name.getText().toString());
                account_money.setText(account_list_money.getText().toString());

                dialog.setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DH.updateData(Integer.parseInt(account_list_id.getText().toString()),account_name.getText().toString(),account_money.getText().toString());
                        show_account_list();
                    }
                });
                dialog.setNegativeButton("刪除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete("tb_setting","_id" + "=" + account_list_id.getText().toString(),null);
                        show_account_list();
                    }
                });

                dialog.show();
            }
        });
    }

    private void showdialog(int layout,final int x){  //x判斷是哪個按鈕按下
        LayoutInflater inflater = LayoutInflater.from(Screen_Setting.this);
        final View EntryView = inflater.inflate(layout,null);

        AlertDialog.Builder dialog = new AlertDialog.Builder(Screen_Setting.this);
        dialog.setView(EntryView);

        final EditText account_name = EntryView.findViewById(R.id.account_name);
        final EditText account_money = EntryView.findViewById(R.id.account_money);  //找dialog畫面上的edittext


        dialog.setPositiveButton("確認", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                settingvalue.clear();

                switch (x) {
                    case 1:  //設定帳戶
                        DH.insertData(account_name.getText().toString(),account_money.getText().toString());
                        /*settingvalue.put("Account",account_name.getText().toString());
                        settingvalue.put("Money",Float.parseFloat(account_money.getText().toString()));
                        db.insert("tb_setting", null, settingvalue);*/
                        show_account_list();
                        break;
                }
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }
    private void show_account_list(){
        settingCursor = db.query("tb_setting",null,null,null,null,null,null);
        List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
        settingCursor.moveToFirst();
        for(int i= 0;i< settingCursor.getCount();i++){
            Map<String,Object> item = new HashMap<String,Object>();
            item.put("_id",settingCursor.getString(0));
            item.put("Account",settingCursor.getString(3));
            item.put("Money",settingCursor.getString(4));
            items.add(item);
            settingCursor.moveToNext();
        }
        SimpleAdapter SA = new SimpleAdapter(this,items,R.layout.account_list_layout,new String[]{"_id","Account","Money"},new int[]{R.id.account_list_id,R.id.account_list_name,R.id.account_list_money});
        itlist_account.setAdapter(SA);
    }
}
