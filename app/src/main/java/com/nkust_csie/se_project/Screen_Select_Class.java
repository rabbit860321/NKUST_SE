package com.nkust_csie.se_project;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Screen_Select_Class extends AppCompatActivity {

    SQLiteDB DH = null;
    SQLiteDatabase db;

    ListView list_cost_class1,list_cost_class2;
    Cursor cs;

    String now_click_left;
    String now_click_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen__select__class);

        // 隱藏title Bar
        getSupportActionBar().hide();

        DH = new SQLiteDB(this);
        db = DH.getWritableDatabase();

        list_cost_class1 = (ListView)findViewById(R.id.list_cost_class1);
        list_cost_class2 = (ListView)findViewById(R.id.list_cost_class2);

        cs = db.query("tb_cost_class1",null,null,null,null,null,null);
        if(cs.getCount() == 0){     //若cost主類別TB無資料 初始化
            DH.cost_class1_init();
        }
        cs = db.query("tb_cost_class2",null,null,null,null,null,null);
        if(cs.getCount() == 0){     //若cost主類別TB無資料 初始化
            DH.cost_class2_init();
        }

        show_costClass1();

        list_cost_class1.setOnItemClickListener(new AdapterView.OnItemClickListener() {           //當左邊列表被點擊時，判斷是支出還是收入
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final TextView Classname = (TextView) view.findViewById(android.R.id.text1);
                now_click_left = Classname.getText().toString();      //抓使用者點了哪個主類別
                show_costClass2();
            }
        });

    }

    private void show_costClass1() {
        cs = db.query("tb_cost_class1",new String[]{"主分類"},null,null,null,null,null);
        List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
        cs.moveToFirst();
        for(int i= 0;i< cs.getCount();i++){
            Map<String,Object> item = new HashMap<String,Object>();
            item.put("主分類",cs.getString(0));
            items.add(item);
            cs.moveToNext();
        }
        SimpleAdapter SA = new SimpleAdapter(this,items,android.R.layout.simple_list_item_1,new String[]{"主分類"},new int[]{android.R.id.text1});
        list_cost_class1.setAdapter(SA);
    }

    private void show_costClass2(){
        cs = db.query("tb_cost_class2",new String[]{"主分類","副分類"},"主分類=?",new String[]{now_click_left},null,null,null);
        List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
        cs.moveToFirst();
        for(int j= 0;j< cs.getCount();j++){
            Map<String,Object> item = new HashMap<String,Object>();
            item.put("主分類",cs.getString(0));
            item.put("副分類",cs.getString(1));
            items.add(item);
            cs.moveToNext();
        }
        SimpleAdapter SA = new SimpleAdapter(this,items,android.R.layout.simple_list_item_1,new String[]{"副分類"},new int[]{android.R.id.text1});
        list_cost_class2.setAdapter(SA);
    }
}
