package com.example.nkust_se;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyWhat_Screen extends AppCompatActivity {

    SQLiteDB DH = null;
    SQLiteDatabase db;

    ListView main_list;
    ListView sec_list;

    ContentValues MainClassValues;
    ContentValues SecondClassValues;
    Cursor MainClassCursor;
    Cursor SecondClassCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_what__screen);

        // 隱藏titla Bar
        getSupportActionBar().hide();

        DH = new SQLiteDB(this);
        db = DH.getWritableDatabase();

        main_list = (ListView)findViewById(R.id.main_list);
        sec_list = (ListView)findViewById(R.id.sec_List);


        MainClassCursor = db.query("MainClassTB",new String[]{"_id","主分類"},null,null,null,null,null);
        SecondClassCursor = db.query("SecondClassTB",new String[]{"_id","主分類","副分類"},null,null,null,null,null);

        /*查詢主要支出類別資料表與副支出類別資料表有無資料
        若無,初始化這兩個資料表
         */

        if(MainClassCursor.getCount() == 0){
            MainClassInit();
        }

        if(SecondClassCursor.getCount() == 0){
            SecondClassInit();
        }

        show_MainClass();  //顯示主要支出類別

        /*主要支出類別列表被點選到,抓出主要類別名稱
        並查詢該類別有哪些副支出類別
         */
        main_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final TextView main_name = (TextView) view.findViewById(android.R.id.text1);
                //Log.e("BANG",main_name.getText().toString());
                SecondClassCursor = db.query("SecondClassTB",new String[]{"_id","主分類","副分類"},"主分類=?",new String[]{main_name.getText().toString()},null,null,null);
                List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
                SecondClassCursor.moveToFirst();
                for(int i= 0;i< SecondClassCursor.getCount();i++){
                    Map<String,Object> item = new HashMap<String,Object>();
                    item.put("_id",SecondClassCursor.getString(0));
                    item.put("主分類",SecondClassCursor.getString(1));
                    item.put("副分類",SecondClassCursor.getString(2));
                    items.add(item);
                    SecondClassCursor.moveToNext();
                }
                SimpleAdapter SA = new SimpleAdapter(BuyWhat_Screen.this,items,android.R.layout.simple_list_item_1,new String[]{"副分類"},new int[]{android.R.id.text1});
                sec_list.setAdapter(SA);
            }
        });

        sec_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  /*點下副類別後 抓出該類別的名字與主類別合併成一個字串與金額傳到下個頁面*/
                final TextView sec_name = (TextView) view.findViewById(android.R.id.text1);

                SecondClassCursor = db.query("SecondClassTB",new String[]{"主分類","副分類"},"副分類=?",new String[]{sec_name.getText().toString()},null,null,null,null);
                SecondClassCursor.moveToFirst();
                Log.e("Total",SecondClassCursor.getString(0)+"-"+sec_name.getText().toString());

                Bundle bundle = getIntent().getExtras();
                int cost = bundle.getInt("花費");  //抓出從Cost_Screen傳過來的數值
                Log.e("花費",Integer.toString(cost));
            }
        });
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
        main_list.setAdapter(SA);
    }

    private void SecondClassInit(){
        SecondClassValues = new ContentValues();

        SecondClassValues.put("主分類","餐飲");
        SecondClassValues.put("副分類","早餐");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","餐飲");
        SecondClassValues.put("副分類","午餐");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","餐飲");
        SecondClassValues.put("副分類","晚餐");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","餐飲");
        SecondClassValues.put("副分類","消夜");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","服飾美容");
        SecondClassValues.put("副分類","衣服");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","服飾美容");
        SecondClassValues.put("副分類","褲子");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","服飾美容");
        SecondClassValues.put("副分類","鞋子");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","服飾美容");
        SecondClassValues.put("副分類","剪髮");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","服飾美容");
        SecondClassValues.put("副分類","保養品");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","居家生活");
        SecondClassValues.put("副分類","家俱");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","居家生活");
        SecondClassValues.put("副分類","房租");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","居家生活");
        SecondClassValues.put("副分類","電費");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","交通");
        SecondClassValues.put("副分類","公車");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","交通");
        SecondClassValues.put("副分類","捷運");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","交通");
        SecondClassValues.put("副分類","計程車");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","學習");
        SecondClassValues.put("副分類","文具");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","學習");
        SecondClassValues.put("副分類","補習");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","休閒");
        SecondClassValues.put("副分類","電影");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","休閒");
        SecondClassValues.put("副分類","玩具");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","休閒");
        SecondClassValues.put("副分類","展覽");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","休閒");
        SecondClassValues.put("副分類","運動");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","休閒");
        SecondClassValues.put("副分類","旅行");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","3C");
        SecondClassValues.put("副分類","電話費");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","3C");
        SecondClassValues.put("副分類","電腦商品");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","3C");
        SecondClassValues.put("副分類","手機配件");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","汽機車");
        SecondClassValues.put("副分類","油錢");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","汽機車");
        SecondClassValues.put("副分類","停車費");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","汽機車");
        SecondClassValues.put("副分類","維修保養");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","汽機車");
        SecondClassValues.put("副分類","罰單");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","醫療");
        SecondClassValues.put("副分類","就醫");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","醫療");
        SecondClassValues.put("副分類","藥物");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","醫療");
        SecondClassValues.put("副分類","勞健保費");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","其他");
        SecondClassValues.put("副分類","捐款");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","其他");
        SecondClassValues.put("副分類","寵物");
        db.insert("SecondClassTB",null,SecondClassValues);
        SecondClassValues.put("主分類","其他");
        SecondClassValues.put("副分類","雜支");
        db.insert("SecondClassTB",null,SecondClassValues);
    }

    private void MainClassInit(){
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
}
