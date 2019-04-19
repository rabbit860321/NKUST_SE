package com.example.nkust_se;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    SQLiteDB DH = null;
    SQLiteDatabase db;

    Cursor AccountCursor;         //帳戶用指標

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 隱藏titla Bar
        getSupportActionBar().hide();

        DH = new SQLiteDB(this);
        db = DH.getWritableDatabase();

        AccountCursor = db.query("AccountTB",new String[]{"_id","帳戶名","金額"},null,null,null,null,null);
        //查詢帳戶資料表

        if (AccountCursor.getCount() == 0){  //若帳戶資料表無資料 5秒後跳轉帳戶初始化頁面
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    final Intent mainIntent = new Intent(MainActivity.this,Setting_Account.class);
                    startActivity(mainIntent);
                    finish();
                }
            }, 5000);
        }else {                              //若有帳戶資料表有資料  5秒後跳轉到主頁面
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    final Intent mainIntent = new Intent(MainActivity.this,Main_Screen.class);
                    startActivity(mainIntent);
                    finish();
                }
            }, 5000);
        }
    }
}
