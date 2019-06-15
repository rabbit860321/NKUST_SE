package com.nkust_csie.se_project;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Screen_stat extends AppCompatActivity {

    SQLiteDB DH = null;
    SQLiteDatabase db;

    Calendar today;

    TextView text_yearandmonth;
    Button btn_back,btn_add_month,btn_sub_month;
    ListView list_stat;
    Cursor cs;

    int M;
    int Y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_stat);

        getSupportActionBar().hide();

        DH = new SQLiteDB(this);
        db = DH.getWritableDatabase();

        today = Calendar.getInstance();

        M = today.get(Calendar.MONTH)+1;
        Y = today.get(Calendar.YEAR);

        text_yearandmonth = (TextView)findViewById(R.id.text_yearandmonth);
        btn_add_month = (Button)findViewById(R.id.btn_add_month);
        btn_sub_month = (Button)findViewById(R.id.btn_sub_month);
        list_stat = (ListView)findViewById(R.id.list_stat);
        btn_back = (Button)findViewById(R.id.btn_back);


        show_Year_Month(Y,M);
        show_stat_view(Y,M);

        btn_add_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                M++;
                if(M == 13){
                    Y++;
                    M = 1;
                }
                show_Year_Month(Y,M);
                show_stat_view(Y,M);
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
                show_stat_view(Y,M);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent gotomain = new Intent(Screen_stat.this,Screen_Main1.class);
                startActivity(gotomain);
                finish();
            }
        });
    }

    private void show_Year_Month(int Year,int Month){
        text_yearandmonth.setText(Year+"年"+Month+"月");
    }

    private void show_stat_view(int Year,int Month){
        String YM = ""+Year+""+Month;
        float total = 0;  //這個月總共花了
        float c1 = 0;     //這個月餐飲花了
        float c2 = 0;
        float c3 = 0;
        float c4 = 0;
        float c5 = 0;
        float c6 = 0;
        float c7 = 0;
        float c8 = 0;
        float c9 = 0;
        float c10 = 0;

        cs = db.query("tb_cost_history",null,"Date LIKE ? ",new String[]{YM+"%"},null,null,null,null);  //模糊查詢
        cs.moveToFirst();
        for(int i = 0;i<cs.getCount();i++){
            total += Float.parseFloat(cs.getString(5));
            cs.moveToNext();
        }

        cs = db.query("tb_cost_history",null,"Date LIKE ? and Category LIKE ?",new String[]{YM+"%","餐飲-"+"%"},null,null,null,null);
        cs.moveToFirst();
        for(int i=0;i<cs.getCount();i++){
            c1 += Float.parseFloat(cs.getString(5));
            cs.moveToNext();
        }

        cs = db.query("tb_cost_history",null,"Date LIKE ? and Category LIKE ?",new String[]{YM+"%","服飾美容-"+"%"},null,null,null,null);
        cs.moveToFirst();
        for(int i=0;i<cs.getCount();i++){
            c2 += Float.parseFloat(cs.getString(5));
            cs.moveToNext();
        }

        cs = db.query("tb_cost_history",null,"Date LIKE ? and Category LIKE ?",new String[]{YM+"%","居家生活-"+"%"},null,null,null,null);
        cs.moveToFirst();
        for(int i=0;i<cs.getCount();i++){
            c3 += Float.parseFloat(cs.getString(5));
            cs.moveToNext();
        }

        cs = db.query("tb_cost_history",null,"Date LIKE ? and Category LIKE ?",new String[]{YM+"%","交通-"+"%"},null,null,null,null);
        cs.moveToFirst();
        for(int i=0;i<cs.getCount();i++){
            c4 += Float.parseFloat(cs.getString(5));
            cs.moveToNext();
        }

        cs = db.query("tb_cost_history",null,"Date LIKE ? and Category LIKE ?",new String[]{YM+"%","學習-"+"%"},null,null,null,null);
        cs.moveToFirst();
        for(int i=0;i<cs.getCount();i++){
            c5 += Float.parseFloat(cs.getString(5));
            cs.moveToNext();
        }

        cs = db.query("tb_cost_history",null,"Date LIKE ? and Category LIKE ?",new String[]{YM+"%","休閒-"+"%"},null,null,null,null);
        cs.moveToFirst();
        for(int i=0;i<cs.getCount();i++){
            c6 += Float.parseFloat(cs.getString(5));
            cs.moveToNext();
        }

        cs = db.query("tb_cost_history",null,"Date LIKE ? and Category LIKE ?",new String[]{YM+"%","3C-"+"%"},null,null,null,null);
        cs.moveToFirst();
        for(int i=0;i<cs.getCount();i++){
            c7 += Float.parseFloat(cs.getString(5));
            cs.moveToNext();
        }

        cs = db.query("tb_cost_history",null,"Date LIKE ? and Category LIKE ?",new String[]{YM+"%","汽機車-"+"%"},null,null,null,null);
        cs.moveToFirst();
        for(int i=0;i<cs.getCount();i++){
            c8 += Float.parseFloat(cs.getString(5));
            cs.moveToNext();
        }

        cs = db.query("tb_cost_history",null,"Date LIKE ? and Category LIKE ?",new String[]{YM+"%","醫療-"+"%"},null,null,null,null);
        cs.moveToFirst();
        for(int i=0;i<cs.getCount();i++){
            c9 += Float.parseFloat(cs.getString(5));
            cs.moveToNext();
        }

        cs = db.query("tb_cost_history",null,"Date LIKE ? and Category LIKE ?",new String[]{YM+"%","其他-"+"%"},null,null,null,null);
        cs.moveToFirst();
        for(int i=0;i<cs.getCount();i++){
            c10 += Float.parseFloat(cs.getString(5));
            cs.moveToNext();
        }

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);    //小數後兩位

        String[] category = new String[]{"餐飲", "服飾美容", "居家生活", "交通", "學習", "休閒", "3C", "汽機車", "醫療", "其他"};
        String[] percen = new String[]{nf.format(c1/total*100)+"%",nf.format(c2/total*100)+"%",nf.format(c3/total*100)+"%",nf.format(c4/total*100)+"%",
                nf.format(c5/total*100)+"%",nf.format(c6/total*100)+"%",nf.format(c7/total*100)+"%",nf.format(c8/total*100)+"%",nf.format(c9/total*100)+"%",nf.format(c10/total*100)+"%"};
        String[] money = new String[]{c1+"元",c2+"元",c3+"元",c4+"元",c5+"元",c6+"元",c7+"元",c8+"元",c9+"元",c10+"元"};

        List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
        for(int i= 0;i< 10;i++){
            Map<String,Object> item = new HashMap<String,Object>();
            item.put("Category",category[i]);  //id
            item.put("Percen",percen[i]);  //日期
            item.put("Money",money[i]);  //類別

            items.add(item);
            cs.moveToNext();
        }
        SimpleAdapter SA = new SimpleAdapter(this,items,R.layout.stat_list_layout,new String[]{"Category","Percen","Money"},new int[]{R.id.stat_ca,R.id.stat_per,R.id.stat_money});
        list_stat.setAdapter(SA);
    }
}
