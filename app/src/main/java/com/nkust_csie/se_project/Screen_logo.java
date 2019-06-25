package com.nkust_csie.se_project;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Screen_logo extends AppCompatActivity {

    SQLiteDB DH = null;
    SQLiteDatabase db;

    Cursor cs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_logo);

        // 隱藏title Bar
        getSupportActionBar().hide();

        DH = new SQLiteDB(this);
        db = DH.getWritableDatabase();

        cs = db.query("tb_account",null,null,null,null,null,null);

        if(cs.getCount() == 0){
            new Handler().postDelayed(new Runnable() {  //5秒後跳轉到setting畫面
                @Override
                public void run() {
                    final Intent mainIntent = new Intent(Screen_logo.this,Screen_Setting.class);
                    startActivity(mainIntent);
                    finish();
                }
            }, 3000);
        }else {
            new Handler().postDelayed(new Runnable() {  //5秒後跳轉到setting畫面
                @Override
                public void run() {
                    final Intent mainIntent = new Intent(Screen_logo.this,Screen_Main1.class);
                    startActivity(mainIntent);
                    finish();
                }
            }, 3000);
        }
    }
}
