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

        DH = new SQLiteDB(this);
        db = DH.getWritableDatabase();

        AccountCursor = db.query("AccountTB",new String[]{"_id","帳戶名","金額"},null,null,null,null,null);

        if (AccountCursor.getCount() == 0){  //若資料表count=0 5秒後跳轉帳戶初始化
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    final Intent mainIntent = new Intent(MainActivity.this,Setting_Account.class);
                    startActivity(mainIntent);
                    finish();
                }
            }, 5000);
        }else {
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
