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
    /*Button btn_fixedIncome;
    Button btn_fixedBill;
    Button btn_bill;*/
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
        /*btn_fixedIncome = (Button)findViewById(R.id.btn_fixedIncome);
        btn_fixedBill = (Button)findViewById(R.id.btn_fixedBill);
        btn_bill = (Button)findViewById(R.id.btn_bill);*/
        btn_save = (Button)findViewById(R.id.btn_save);

        itlist_account = (ListView)findViewById(R.id.itlist_account);


        settingCursor = db.query("tb_setting",null,null,null,null,null,null);  //查詢tb_setting所有資料
        Log.e("TAG","tb_setting裡有"+settingCursor.getCount()+"筆資料");
        settingCursor = db.query("tb_account",null,null,null,null,null,null);  //查詢tb_account所有資料
        Log.e("TAG","tb_account裡有"+settingCursor.getCount()+"筆資料");

        show_account_list();

        btn_account.setOnClickListener(new View.OnClickListener() {  //新增帳戶
            @Override
            public void onClick(View v) {      //新增帳戶
                showdialog(R.layout.dialog_account,1);
            }
        });

        /*btn_fixedIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog(R.layout.dialog_fixedincome,2);
            }
        });

        btn_fixedBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog(R.layout.dialog_fixedbill,3);
            }
        });

        btn_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog(R.layout.dialog_bill,4);
            }
        });*/

        btn_save.setOnClickListener(new View.OnClickListener() {   //儲存鍵
            @Override
            public void onClick(View v) {
                settingCursor = db.query("tb_account",null,null,null,null,null,null);
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
                        settingvalue.put("帳戶名稱",account_name.getText().toString());
                        settingvalue.put("金額",account_money.getText().toString());
                        db.update("tb_account",settingvalue,"_id" + "=" + account_list_id.getText().toString(),null);
                        show_account_list();
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

    private void showdialog(int layout,final int x){  //x判斷是哪個按鈕按下
        LayoutInflater inflater = LayoutInflater.from(Screen_Setting.this);
        final View EntryView = inflater.inflate(layout,null);

        AlertDialog.Builder dialog = new AlertDialog.Builder(Screen_Setting.this);
        dialog.setView(EntryView);

        final EditText account_name = EntryView.findViewById(R.id.account_name);
        final EditText account_money = EntryView.findViewById(R.id.account_money);  //找dialog畫面上的edittext

        /*final EditText income_name = EntryView.findViewById(R.id.income_name);
        final EditText income_account = EntryView.findViewById(R.id.income_account);
        final EditText income_money = EntryView.findViewById(R.id.income_money);
        final Spinner income_count = EntryView.findViewById(R.id.income_count);
        final EditText payday = EntryView.findViewById(R.id.payday);

        final EditText fixedbill_name = EntryView.findViewById(R.id.fixedbill_name);
        final EditText bill_account = EntryView.findViewById(R.id.bill_account);
        final EditText bill_money = EntryView.findViewById(R.id.bill_money);
        final Spinner fixedbill_count = EntryView.findViewById(R.id.fixedbill_count);
        final EditText fixedbill_payment_day = EntryView.findViewById(R.id.fixedbill_payment_day);

        final EditText bill_name = EntryView.findViewById(R.id.bill_name);
        final Spinner bill_count = EntryView.findViewById(R.id.bill_count);
        final EditText bill_payment_day = EntryView.findViewById(R.id.bill_payment_day);*/

        dialog.setPositiveButton("確認", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                settingvalue.clear();

                switch (x) {
                    case 1:  //設定帳戶
                        settingvalue.put("帳戶名稱",account_name.getText().toString());
                        settingvalue.put("金額",Float.parseFloat(account_money.getText().toString()));
                        db.insert("tb_account", null, settingvalue);
                        show_account_list();
                        break;
                    /*case 2:  //設定固定收入
                        settingvalue.put("分類屬性","設定固定收入");
                        settingvalue.put("描述",income_name.getText().toString());
                        settingvalue.put("帳戶",income_account.getText().toString());
                        settingvalue.put("金額",Float.parseFloat(income_money.getText().toString()));
                        settingvalue.put("幾個月幾次",income_count.getSelectedItem().toString());
                        settingvalue.put("日期",Integer.parseInt(payday.getText().toString()));
                        db.insert("tb_setting", null, settingvalue);
                        break;
                    case 3:  //設定固定帳單
                        settingvalue.put("分類屬性","設定固定帳單");
                        settingvalue.put("描述",fixedbill_name.getText().toString());
                        settingvalue.put("帳戶",bill_account.getText().toString());
                        settingvalue.put("金額",Float.parseFloat(bill_money.getText().toString()));
                        settingvalue.put("幾個月幾次",fixedbill_count.getSelectedItem().toString());
                        settingvalue.put("日期",Integer.parseInt(fixedbill_payment_day.getText().toString()));
                        db.insert("tb_setting", null, settingvalue);
                        break;
                    case 4:  //設定固定帳單
                        settingvalue.put("分類屬性","設定固定帳單");
                        settingvalue.put("描述",bill_name.getText().toString());
                        settingvalue.put("幾個月幾次",bill_count.getSelectedItem().toString());
                        settingvalue.put("日期",Integer.parseInt(bill_payment_day.getText().toString()));
                        db.insert("tb_setting", null, settingvalue);
                        break;*/
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
        settingCursor = db.query("tb_account",new String[]{"_id","帳戶名稱","金額"},null,null,null,null,null);
        List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
        settingCursor.moveToFirst();
        for(int i= 0;i< settingCursor.getCount();i++){
            Map<String,Object> item = new HashMap<String,Object>();
            item.put("_id",settingCursor.getString(0));
            item.put("帳戶名稱",settingCursor.getString(1));
            item.put("金額",settingCursor.getString(2));
            items.add(item);
            settingCursor.moveToNext();
        }
        SimpleAdapter SA = new SimpleAdapter(this,items,R.layout.account_list_layout,new String[]{"_id","帳戶名稱","金額"},new int[]{R.id.account_list_id,R.id.account_list_name,R.id.account_list_money});
        itlist_account.setAdapter(SA);
    }
}
