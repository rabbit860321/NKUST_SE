package com.nkust_csie.se_project;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Screen_history extends AppCompatActivity {

    SQLiteDB DH = null;
    SQLiteDatabase db;

    ListView list_history;
    Button btn_back,btn_add_month,btn_sub_month;
    TextView text_yearandmonth,txt_total;
    Cursor cs;

    Calendar today;

    int M;
    int Y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_history);

        getSupportActionBar().hide();

        DH = new SQLiteDB(this);
        db = DH.getWritableDatabase();

        today = Calendar.getInstance();

        M = today.get(Calendar.MONTH)+1;
        Y = today.get(Calendar.YEAR);

        list_history = (ListView)findViewById(R.id.list_history);
        btn_back = (Button)findViewById(R.id.btn_back);
        text_yearandmonth = (TextView)findViewById(R.id.text_yearandmonth);
        txt_total = (TextView)findViewById(R.id.txt_total);
        btn_add_month = (Button)findViewById(R.id.btn_add_month);
        btn_sub_month = (Button)findViewById(R.id.btn_sub_month);

        show_Year_Month(Y,M);
        show_cost_history(Y,M);
        show_total(Y,M);

        btn_add_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                M++;
                if(M == 13){
                    Y++;
                    M = 1;
                }
                show_Year_Month(Y,M);
                show_cost_history(Y,M);
                show_total(Y,M);
            }
        });

        btn_sub_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                M--;
                if(M == 0){
                    Y--;
                    M = 12;
                }
                show_Year_Month(Y,M);
                show_cost_history(Y,M);
                show_total(Y,M);
            }
        });



        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent gotomain = new Intent(Screen_history.this,Screen_Main1.class);
                startActivity(gotomain);
                finish();
            }
        });
    }

    private void show_cost_history(int Year,int Month){
        String YM = ""+Year+""+Month;
        cs = db.query("tb_cost_history",null,"Date LIKE ? ",new String[]{YM+"%"},null,null,null,null);  //模糊查詢
        cs.moveToFirst();
        List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
        for(int i= 0;i< cs.getCount();i++){
            Map<String,Object> item = new HashMap<String,Object>();
            item.put("_id",cs.getString(0));  //id
            item.put("Date",cs.getString(1));  //日期
            item.put("Category",cs.getString(2));  //類別
            item.put("Description",cs.getString(3));  //備註
            item.put("Account",cs.getString(4));  //帳戶
            item.put("Money",cs.getString(5));  //金額
            items.add(item);
            cs.moveToNext();
        }
        SimpleAdapter SA = new SimpleAdapter(this,items,R.layout.cost_history_list_layout,new String[]{"_id","Date","Category","Money","Account","Description"},new int[]{R.id.cid,R.id.da,R.id.cl,R.id.co,R.id.ac,R.id.re});
        list_history.setAdapter(SA);
    }

    private void show_Year_Month(int Year,int Month){
        text_yearandmonth.setText(Year+"年"+Month+"月");
    }
    private void show_total(int Year,int Month){
        String YM = ""+Year+""+Month;
        float total = 0;
        cs = db.query("tb_cost_history",null,"Date LIKE ? ",new String[]{YM+"%"},null,null,null,null);  //模糊查詢
        cs.moveToFirst();
        for(int i = 0;i<cs.getCount();i++){
            total += Float.parseFloat(cs.getString(5));
            cs.moveToNext();
        }
        txt_total.setText(M+"月花了"+total);
    }
}
