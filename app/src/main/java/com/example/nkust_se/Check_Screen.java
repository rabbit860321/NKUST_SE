package com.example.nkust_se;

import android.content.ContentValues;
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
                db.insert("CostTB", null, CostValues);

                int before_cost = Integer.parseInt(AccountCursor1.getString(2));  //原本有的錢
                int cost_money = Integer.parseInt(et_cost.getText().toString());              //這次花費的金額
                int after_cost = before_cost - cost_money;                                    //相減

                Log.e("ASD",AccountCursor1.getString(1)+""+AccountCursor1.getString(2));

                AccountValues.put("金額",after_cost);
                db.update("AccountTB",AccountValues,"_id" + "=" + AccountCursor1.getString(0),null);
            }
        });
    }
}
