package com.example.nkust_se;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyWhat_Screen extends AppCompatActivity {

    SQLiteDB DH = null;
    SQLiteDatabase db;

    ListView MainClassList;
    ListView SecondClassList;

    ContentValues MainClassValues;
    Cursor MainClassCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_what__screen);

        // 隱藏titla Bar
        getSupportActionBar().hide();

        DH = new SQLiteDB(this);
        db = DH.getWritableDatabase();

        MainClassList = (ListView)findViewById(R.id.main_list);


        MainClassCursor = db.query("MainClassTB",new String[]{"_id","主分類"},null,null,null,null,null);

        Log.e("ASD",""+MainClassCursor.getCount());

        if(MainClassCursor.getCount() == 0){
            MainClassValues = new ContentValues();

            MainClassValues.put("主分類", "餐飲");
            db.insert("MainClassTB", null, MainClassValues);
            MainClassValues.put("主分類", "服飾美容");
            db.insert("MainClassTB", null, MainClassValues);
            MainClassValues.put("主分類", "居家生活");
            db.insert("MainClassTB", null, MainClassValues);
            MainClassValues.put("主分類", "交通");
            db.insert("MainClassTB", null, MainClassValues);
            MainClassValues.put("主分類", "學習");
            db.insert("MainClassTB", null, MainClassValues);
            MainClassValues.put("主分類", "休閒");
            db.insert("MainClassTB", null, MainClassValues);
            MainClassValues.put("主分類", "3C");
            db.insert("MainClassTB", null, MainClassValues);
            MainClassValues.put("主分類", "汽機車");
            db.insert("MainClassTB", null, MainClassValues);
            MainClassValues.put("主分類", "醫療");
            db.insert("MainClassTB", null, MainClassValues);
            MainClassValues.put("主分類", "其他");
            db.insert("MainClassTB", null, MainClassValues);
        }

        show_MainClass();
    }

    private void show_MainClass(){
        MainClassCursor = db.query("MainClassTB",new String[]{"_id","主分類"},null,null,null,null,null);
        List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
        MainClassCursor.moveToFirst();
        for(int i= 0;i< MainClassCursor.getCount();i++){
            Map<String,Object> item = new HashMap<String,Object>();
            item.put("_id",MainClassCursor.getString(0));
            item.put("主分類",MainClassCursor.getString(1));
            items.add(item);
            MainClassCursor.moveToNext();
        }
        SimpleAdapter SA = new SimpleAdapter(this,items,android.R.layout.simple_list_item_1,new String[]{"_id","主分類"},new int[]{android.R.id.text2,android.R.id.text1});
        MainClassList.setAdapter(SA);
    }
}
