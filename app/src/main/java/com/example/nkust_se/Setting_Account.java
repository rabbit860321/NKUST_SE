package com.example.nkust_se;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Setting_Account extends AppCompatActivity {

    SQLiteDB DH = null;
    SQLiteDatabase db;

    ContentValues AccountValues;  //存放欲新增的帳戶資料
    Cursor AccountCursor;         //帳戶用指標
    Button add_btn;
    Button save_btn;
    ListView account_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting__account);

        AccountValues = new ContentValues();
        add_btn = (Button)findViewById(R.id.add_btn);
        save_btn = (Button)findViewById(R.id.save_btn);
        account_list = (ListView)findViewById(R.id.account_list);

        DH = new SQLiteDB(this);
        db = DH.getWritableDatabase();


        add_btn.setOnClickListener(new View.OnClickListener() {  //新增帳戶按鈕被按下
            @Override
            public void onClick(View v) {

                AlertDialog.Builder obj_Dialog = new AlertDialog.Builder(Setting_Account.this);  //彈出對話方塊
                obj_Dialog.setTitle("新增帳戶");

                TableLayout obj_TableLayout = new TableLayout(Setting_Account.this);
                TableRow obj_TableRow1 = new TableRow(Setting_Account.this);
                TableRow obj_TableRow2 = new TableRow(Setting_Account.this);

                TextView obj_tv1 = new TextView(Setting_Account.this);
                obj_tv1.setText("帳戶名稱:");
                obj_tv1.setTextColor(Color.BLACK);
                obj_tv1.setTextSize(18);
                final EditText obj_et1 = new EditText(Setting_Account.this);
                obj_et1.setWidth(300);
                obj_et1.setInputType(InputType.TYPE_CLASS_TEXT);

                TextView obj_tv2 = new TextView(Setting_Account.this);
                obj_tv2.setText("金額:");
                obj_tv2.setTextColor(Color.BLACK);
                obj_tv2.setTextSize(18);
                final EditText obj_et2 = new EditText(Setting_Account.this);
                obj_et2.setWidth(300);

                obj_TableRow1.addView(obj_tv1);
                obj_TableRow1.addView(obj_et1);
                obj_TableRow2.addView(obj_tv2);
                obj_TableRow2.addView(obj_et2);

                obj_TableLayout.addView(obj_TableRow1);
                obj_TableLayout.addView(obj_TableRow2);

                obj_Dialog.setView(obj_TableLayout);

                obj_Dialog.setPositiveButton("儲存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AccountValues.put("帳戶名", obj_et1.getText().toString());
                        AccountValues.put("金額", Integer.parseInt(obj_et2.getText().toString()));
                        db.insert("AccountTB", null, AccountValues);
                        showList();
                    }
                });
                obj_Dialog.show();
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent obj_gotomain_screen = new Intent();
                obj_gotomain_screen.setClass(Setting_Account.this,Main_Screen.class);
                startActivity(obj_gotomain_screen);
            }
        });
    }
    private void showList(){
        AccountCursor = db.query("AccountTB",new String[]{"_id","帳戶名","金額"},null,null,null,null,null);
        List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
        AccountCursor.moveToFirst();
        for(int i= 0;i< AccountCursor.getCount();i++){
            Map<String,Object> item = new HashMap<String,Object>();
            item.put("_id",AccountCursor.getString(0));
            item.put("帳戶名",AccountCursor.getString(1));
            item.put("金額",AccountCursor.getString(2));
            items.add(item);
            AccountCursor.moveToNext();
        }
        SimpleAdapter SA = new SimpleAdapter(this,items,android.R.layout.simple_expandable_list_item_2,new String[]{"帳戶名","金額"},new int[]{android.R.id.text1,android.R.id.text2});
        account_list.setAdapter(SA);
    }
}
