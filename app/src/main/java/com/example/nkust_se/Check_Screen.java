package com.example.nkust_se;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import java.util.Calendar;

public class Check_Screen extends AppCompatActivity {

    SQLiteDB DH = null;
    SQLiteDatabase db;

    EditText et_cost,et_class,et_re;
    Spinner account_sp;
    Button back_btn,ok_btn;

    Cursor AccountCursor1;
    ContentValues CostValues;
    ContentValues AccountValues;

    Calendar rightnow;
    int year;
    int month;
    int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check__screen);

        // 隱藏titla Bar
        getSupportActionBar().hide();

        DH = new SQLiteDB(this);
        db = DH.getWritableDatabase();

        et_cost = (EditText)findViewById(R.id.et_cost);
        et_class = (EditText)findViewById(R.id.et_class);
        et_re = (EditText)findViewById(R.id.et_re);
        account_sp = (Spinner)findViewById(R.id.account_sp);
        back_btn = (Button)findViewById(R.id.back_btn);
        ok_btn = (Button)findViewById(R.id.ok_btn);
        CostValues = new ContentValues();
        AccountValues = new ContentValues();

        rightnow = Calendar.getInstance();

        year = rightnow.get(Calendar.YEAR);     //取出年月日
        month = rightnow.get(Calendar.MONTH)+1;
        day = rightnow.get(Calendar.DAY_OF_MONTH);

        back_btn.setOnClickListener(new View.OnClickListener() {  //返回建
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle bundle = getIntent().getExtras();
        int cost = bundle.getInt("花費");  //抓出從BuyWhat_Screen傳過來的數值
        String s = bundle.getString("品項");

        et_cost.setText(""+cost);
        et_class.setText(s);

        et_class.setOnClickListener(new View.OnClickListener() {  //若要重選類別 點擊，回上一頁
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        AccountCursor1 = db.query("AccountTB",new String[]{"_id","帳戶名","金額"},null,null,null,null,null);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item ,AccountCursor1, new String[] { "帳戶名" }, new int[] {android.R.id.text1});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);   //spinner 載入帳戶資料
        account_sp.setAdapter(adapter);

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CostValues.put("支出項目",et_class.getText().toString());
                CostValues.put("金額",et_cost.getText().toString());
                CostValues.put("帳戶",AccountCursor1.getString(1));
                CostValues.put("日期",year+"-"+month+"-"+day);
                CostValues.put("備註",et_re.getText().toString());
                db.insert("CostTB", null, CostValues);  //insert到花費紀錄資料表

                int money = Integer.parseInt(AccountCursor1.getString(2));  //你選擇的帳戶原本有的金額

                String str = et_class.getText().toString();
                if(str.indexOf("收入")>=0){  //判斷支出項目中的字串是否有"收入"兩字存在  要將原本帳戶的錢相加
                    int income = Integer.parseInt(et_cost.getText().toString());
                    money += income;
                    AccountValues.put("金額",money);
                    db.update("AccountTB",AccountValues,"_id" + "=" + AccountCursor1.getString(0),null);  //update到帳戶資料表
                }else{                                        //支出 相減
                    int cost = Integer.parseInt(et_cost.getText().toString());
                    money -= cost;
                    AccountValues.put("金額",money);
                    db.update("AccountTB",AccountValues,"_id" + "=" + AccountCursor1.getString(0),null);  //update到帳戶資料表
                }

                finish();
                Intent Intent = new Intent(Check_Screen.this,Main_Screen.class);
                startActivity(Intent);
            }
        });
    }
}
