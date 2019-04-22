package com.example.nkust_se;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main_Screen extends AppCompatActivity {

    SQLiteDB DH = null;
    SQLiteDatabase db;

    TextView Date_View,Total_view;
    Calendar rightnow;
    Button expense_btn;
    Button income_btn;
    ListView account_list,cost_list;
    Cursor AccountCursor;
    Cursor CostCursor;
    int year;
    int month;
    int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__screen);

        DH = new SQLiteDB(this);
        db = DH.getWritableDatabase();

        // 隱藏titla Bar
        getSupportActionBar().hide();

        Date_View = (TextView)findViewById(R.id.Date_View);
        Total_view = (TextView)findViewById(R.id.Total_view);
        expense_btn = (Button)findViewById(R.id.expense_btn);
        income_btn = (Button)findViewById(R.id.income_btn);
        account_list = (ListView)findViewById(R.id.account_list);
        cost_list = (ListView)findViewById(R.id.cost_list);
        rightnow = Calendar.getInstance();

        year = rightnow.get(Calendar.YEAR);     //取出年月日
        month = rightnow.get(Calendar.MONTH)+1;
        day = rightnow.get(Calendar.DAY_OF_MONTH);

        show_account_list();
        show_todaycost();

        Date_View.setText(year+"年"+month+"月"+day+"日");

        expense_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent obj_Cost_screen = new Intent();
                obj_Cost_screen.setClass(Main_Screen.this,Cost_Screen.class);
                startActivity(obj_Cost_screen);
            }
        });

        income_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent obj_Cost_screen = new Intent();
                obj_Cost_screen.setClass(Main_Screen.this,Cost_Screen.class);
                startActivity(obj_Cost_screen);
            }
        });
   }

   private void show_account_list(){
       AccountCursor = db.query("AccountTB",new String[]{"_id","帳戶名","金額"},null,null,null,null,null);
       List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
       AccountCursor.moveToFirst();
       for(int i= 0;i< AccountCursor.getCount();i++){
           Map<String,Object> item = new HashMap<String,Object>();
           item.put("_id",AccountCursor.getString(0));
           item.put("帳戶名",AccountCursor.getString(1));
           item.put("金額","$"+AccountCursor.getString(2));
           items.add(item);
           AccountCursor.moveToNext();
       }
       SimpleAdapter SA = new SimpleAdapter(this,items,R.layout.account_layout,new String[]{"_id","帳戶名","金額"},new int[]{R.id.account_id,R.id.account_name,R.id.account_money});
       account_list.setAdapter(SA);
   }

   private void show_todaycost(){
       int total =0;   //今天花費總金額
       CostCursor = db.query("CostTB",new String[]{"_id","支出項目","金額","帳戶","日期","備註"},"日期=?",new String[]{year+"-"+month+"-"+day},null,null,null);
       List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
       CostCursor.moveToFirst();
       for(int i= 0;i< CostCursor.getCount();i++){
           Map<String,Object> item = new HashMap<String,Object>();
           item.put("_id",CostCursor.getString(0));
           item.put("支出項目",CostCursor.getString(1));
           item.put("金額","$"+CostCursor.getString(2));
           item.put("帳戶",CostCursor.getString(3));
           item.put("日期",CostCursor.getString(4));
           item.put("備註",CostCursor.getString(5));
           items.add(item);
           total = total + Integer.parseInt(CostCursor.getString(2));
           CostCursor.moveToNext();
       }
       SimpleAdapter SA = new SimpleAdapter(this,items,R.layout.cost_layout,new String[]{"支出項目","金額","帳戶","備註"},new int[]{R.id.cl,R.id.co,R.id.ac,R.id.re});
       cost_list.setAdapter(SA);
       Total_view.setText(Total_view.getText()+Integer.toString(total)+"元");
   }
}
