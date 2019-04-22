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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class BuyWhat_Screen extends AppCompatActivity {

    SQLiteDB DH = null;
    SQLiteDatabase db;

    ListView left_list;
    ListView right_list;

    ContentValues MainClassValues;
    ContentValues SecondClassValues;
    ContentValues income1Values;
    ContentValues income2Values;

    Cursor cost1Cursor;
    Cursor cost2Cursor;
    Cursor income1Cursor;
    Cursor income2Cursor;

    Button new_btn;
    Button back_btn;
    Button btn1,btn2;

    String now_click_left;  //用來儲存左邊的列表點選的字串
    String now_click_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_what__screen);

        // 隱藏titla Bar
        getSupportActionBar().hide();

        DH = new SQLiteDB(this);
        db = DH.getWritableDatabase();

        left_list = (ListView)findViewById(R.id.left_list);
        right_list = (ListView)findViewById(R.id.right_List);
        new_btn = (Button)findViewById(R.id.new_btn);
        back_btn = (Button)findViewById(R.id.back_btn);
        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        SecondClassValues = new ContentValues();
        MainClassValues = new ContentValues();
        income1Values = new ContentValues();
        income2Values = new ContentValues();

        back_btn.setOnClickListener(new View.OnClickListener() {  //返回建
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cost1Cursor = db.query("MainClassTB",new String[]{"_id","主分類"},null,null,null,null,null);
        cost2Cursor = db.query("SecondClassTB",new String[]{"_id","主分類","副分類"},null,null,null,null,null);
        income1Cursor = db.query("incomeTB1",new String[]{"_id","主分類"},null,null,null,null,null);
        income2Cursor = db.query("incomeTB2",new String[]{"_id","主分類","副分類"},null,null,null,null,null);

        /*查詢支出類別資料表1&支出類別資料表2&收入類別資料表1&收入類別資料表2有無資料
        若無,初始化這4個資料表
         */

        if(cost1Cursor.getCount() == 0){
            MainClassInit();
        }

        if(cost2Cursor.getCount() == 0){
            SecondClassInit();
        }

        if(income1Cursor.getCount() == 0){
            income1Init();
        }

        if(income2Cursor.getCount() == 0){
            income2Init();
        }

        show_cost1();  //一進畫面 預設你要新增支出紀錄


        left_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {           //當左邊列表被點擊時，判斷是支出還是收入
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final TextView main_name = (TextView) view.findViewById(android.R.id.text1);
                now_click_left = main_name.getText().toString();

                if(now_click_left.equals("一般收入") || now_click_left.equals("投資收入") || now_click_left.equals("意外收入")) {
                    show_income2();
                }else{
                    show_cost2();
                }

            }
        });

        right_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  /*點下副類別後 抓出該類別的名字與主類別合併成一個字串與金額傳到下個頁面*/
                final TextView sec_name = (TextView) view.findViewById(android.R.id.text1);
                now_click_right = sec_name.getText().toString();

                cost2Cursor = db.query("SecondClassTB",new String[]{"主分類","副分類"},"副分類=?",new String[]{sec_name.getText().toString()},null,null,null,null);
                cost2Cursor.moveToFirst();

                String margeclass = now_click_left + "-" + now_click_right;  //字串:主分類-副分類  ex:餐飲-早餐

                Bundle bundle = getIntent().getExtras();
                int cost = bundle.getInt("花費");  //抓出從Cost_Screen傳過來的數值

                Intent intent = new Intent();
                intent.setClass(BuyWhat_Screen.this,Check_Screen.class);
                Bundle check = new Bundle();  //Bundle用於資料傳遞 以key value方式儲存資料
                check.putInt("花費",cost);
                check.putString("品項",margeclass);    //將花費與品項名稱傳到下個頁面 做check的動作 準備存入資料表
                intent.putExtras(check);

                startActivity(intent);
            }
        });

        new_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder obj_Dialog = new AlertDialog.Builder(BuyWhat_Screen.this);  //彈出對話方塊
                obj_Dialog.setTitle("新增副類別");

                TableLayout obj_TableLayout = new TableLayout(BuyWhat_Screen.this);
                TableRow obj_TableRow1 = new TableRow(BuyWhat_Screen.this);

                final EditText obj_et1 = new EditText(BuyWhat_Screen.this);
                obj_et1.setWidth(600);
                obj_et1.setInputType(InputType.TYPE_CLASS_TEXT);

                obj_TableRow1.addView(obj_et1);

                obj_TableLayout.addView(obj_TableRow1);

                obj_Dialog.setView(obj_TableLayout);

                obj_Dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SecondClassValues.put("主分類",now_click_left);
                        SecondClassValues.put("副分類",obj_et1.getText().toString());
                        db.insert("SecondClassTB", null, SecondClassValues);

                        show_cost2();
                    }
                });
                obj_Dialog.show();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_cost1();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {  //收入按鈕被點擊
            @Override
            public void onClick(View v) {
                show_income1();
            }
        });

    }

    private void show_cost1(){
        cost1Cursor = db.query("MainClassTB",new String[]{"主分類"},null,null,null,null,null);
        List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
        cost1Cursor.moveToFirst();
        for(int i= 0;i< cost1Cursor.getCount();i++){
            Map<String,Object> item = new HashMap<String,Object>();
            item.put("主分類",cost1Cursor.getString(0));
            items.add(item);
            cost1Cursor.moveToNext();
        }
        SimpleAdapter SA = new SimpleAdapter(this,items,android.R.layout.simple_list_item_1,new String[]{"主分類"},new int[]{android.R.id.text1});
        left_list.setAdapter(SA);
    }

    private void show_cost2(){
        cost2Cursor = db.query("SecondClassTB",new String[]{"主分類","副分類"},"主分類=?",new String[]{now_click_left},null,null,null);
        List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
        cost2Cursor.moveToFirst();
        for(int j= 0;j< cost2Cursor.getCount();j++){
            Map<String,Object> item = new HashMap<String,Object>();
            item.put("主分類",cost2Cursor.getString(0));
            item.put("副分類",cost2Cursor.getString(1));
            items.add(item);
            cost2Cursor.moveToNext();
        }
        SimpleAdapter SA = new SimpleAdapter(BuyWhat_Screen.this,items,android.R.layout.simple_list_item_1,new String[]{"副分類"},new int[]{android.R.id.text1});
        right_list.setAdapter(SA);
    }

    private void show_income1(){
        income1Cursor = db.query("incomeTB1",new String[]{"主分類"},null,null,null,null,null);
        List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
        income1Cursor.moveToFirst();
        for(int i= 0;i< income1Cursor.getCount();i++){
            Map<String,Object> item = new HashMap<String,Object>();
            item.put("主分類",income1Cursor.getString(0));
            items.add(item);
            income1Cursor.moveToNext();
        }
        SimpleAdapter SA = new SimpleAdapter(this,items,android.R.layout.simple_list_item_1,new String[]{"主分類"},new int[]{android.R.id.text1});
        left_list.setAdapter(SA);
    }

    private void show_income2(){
        income2Cursor = db.query("incomeTB2",new String[]{"主分類","副分類"},"主分類=?",new String[]{now_click_left},null,null,null);
        List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
        income2Cursor.moveToFirst();
        for(int i= 0;i< income2Cursor.getCount();i++){
            Map<String,Object> item = new HashMap<String,Object>();
            item.put("主分類",income2Cursor.getString(0));
            item.put("副分類",income2Cursor.getString(1));
            items.add(item);
            income2Cursor.moveToNext();
        }
        SimpleAdapter SA = new SimpleAdapter(this,items,android.R.layout.simple_list_item_1,new String[]{"副分類"},new int[]{android.R.id.text1});
        right_list.setAdapter(SA);
    }

    private void MainClassInit(){

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

    private void SecondClassInit(){

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


    private void income1Init(){
        income1Values.put("主分類","一般收入");
        db.insert("incomeTB1",null,income1Values);
        income1Values.put("主分類","投資收入");
        db.insert("incomeTB1",null,income1Values);
        income1Values.put("主分類","意外收入");
        db.insert("incomeTB1",null,income1Values);
    }

    private void income2Init(){
        income2Values.put("主分類","一般收入");
        income2Values.put("副分類","公司薪資");
        db.insert("incomeTB2",null,income2Values);
        income2Values.put("主分類","一般收入");
        income2Values.put("副分類","打工");
        db.insert("incomeTB2",null,income2Values);
        income2Values.put("主分類","一般收入");
        income2Values.put("副分類","零用錢");
        db.insert("incomeTB2",null,income2Values);
        income2Values.put("主分類","投資收入");
        income2Values.put("副分類","定存");
        db.insert("incomeTB2",null,income2Values);
        income2Values.put("主分類","投資收入");
        income2Values.put("副分類","股票");
        db.insert("incomeTB2",null,income2Values);
        income2Values.put("主分類","投資收入");
        income2Values.put("副分類","基金");
        db.insert("incomeTB2",null,income2Values);
        income2Values.put("主分類","意外收入");
        income2Values.put("副分類","統一發票中獎");
        db.insert("incomeTB2",null,income2Values);
        income2Values.put("主分類","意外收入");
        income2Values.put("副分類","樂透中獎");
        db.insert("incomeTB2",null,income2Values);
        income2Values.put("主分類","意外收入");
        income2Values.put("副分類","撿到錢");
        db.insert("incomeTB2",null,income2Values);
    }
}
