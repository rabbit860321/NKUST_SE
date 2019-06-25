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

        show_account_list();

        btn_account.setOnClickListener(new View.OnClickListener() {  //新增帳戶
            @Override
            public void onClick(View v) {      //新增帳戶
                showdialog(R.layout.dialog_account);
            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {   //儲存鍵
            @Override
            public void onClick(View v) {
                settingCursor = db.query("tb_account",null,null,null,null,null,null);  //查詢tb_setting所有資料

                if(settingCursor.getCount() == 0){  //須有一筆帳戶資料以上才能進到主畫面

                    show_toast("請新增至少一筆帳戶資料");

                }else{
                    final Intent mainIntent = new Intent(Screen_Setting.this,Screen_Main1.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        });

        itlist_account.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  //點下帳戶列表的某一個item 可進行修改及刪除

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
                account_name.setFocusable(false);  //將名稱的EditText設為不可編輯
                account_money.setText(account_list_money.getText().toString().substring(1));  //去掉$符號

                dialog.setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //需判斷金額是否為空
                        if(account_money.getText().toString().isEmpty()){
                            show_toast("請輸入金額!");
                        }else{

                            ContentValues cv = new ContentValues();
                            cv.put("帳戶金額",Integer.parseInt(account_money.getText().toString()));
                            db.update("tb_account",cv,"_id" + "=" + account_list_id.getText().toString(),null);

                            show_account_list();
                        }
                    }
                });
                dialog.setNegativeButton("刪除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete("tb_account","_id" + "=" + account_list_id.getText().toString(),null);
                        show_account_list();
                    }
                });

                dialog.show();
            }
        });
    }

    private void showdialog(int layout){
        LayoutInflater inflater = LayoutInflater.from(Screen_Setting.this);
        final View EntryView = inflater.inflate(layout,null);

        AlertDialog.Builder dialog = new AlertDialog.Builder(Screen_Setting.this);
        dialog.setView(EntryView);

        final EditText account_name = EntryView.findViewById(R.id.account_name);
        final EditText account_money = EntryView.findViewById(R.id.account_money);  //找dialog畫面上的edittext


        dialog.setPositiveButton("確認", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //需判斷帳戶名稱是否重複 & 金額是否為空
                ArrayList<String> ac_name = new ArrayList<String>();
                settingCursor = db.query("tb_account",null,null,null,null,null,null);
                settingCursor.moveToFirst();

                for(int i = 0;i<settingCursor.getCount();i++){              //把所有帳戶名稱存入陣列
                    ac_name.add(settingCursor.getString(1));
                    settingCursor.moveToNext();
                }

                if(ac_name.indexOf(account_name.getText().toString()) != -1){
                    show_toast("帳戶名稱重複!");
                }else if(account_money.getText().toString().isEmpty()){
                    show_toast("請輸入金額!");
                }else{
                    ContentValues cv = new ContentValues();
                    cv.put("帳戶名稱",account_name.getText().toString());
                    cv.put("帳戶金額",Integer.parseInt(account_money.getText().toString()));
                    db.insert("tb_account", null, cv);

                    show_account_list();
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
        settingCursor = db.query("tb_account",null,null,null,null,null,null);
        List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
        settingCursor.moveToFirst();
        for(int i= 0;i< settingCursor.getCount();i++){
            Map<String,Object> item = new HashMap<String,Object>();
            item.put("_id",settingCursor.getString(0));
            item.put("帳戶名稱",settingCursor.getString(1));
            item.put("帳戶金額","$"+settingCursor.getString(2));
            items.add(item);
            settingCursor.moveToNext();
        }
        SimpleAdapter SA = new SimpleAdapter(this,items,R.layout.account_list_layout,new String[]{"_id","帳戶名稱","帳戶金額"},new int[]{R.id.account_list_id,R.id.account_list_name,R.id.account_list_money});
        itlist_account.setAdapter(SA);
    }

    private void show_toast(String text){
        Toast toast = Toast.makeText(Screen_Setting.this,
                text, Toast.LENGTH_LONG);
        //顯示Toast
        toast.show();
    }

}
