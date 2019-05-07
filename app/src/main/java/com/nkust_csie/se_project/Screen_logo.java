package com.nkust_csie.se_project;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Screen_logo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_logo);

        // 隱藏title Bar
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {  //5秒後跳轉到setting畫面
            @Override
            public void run() {
                final Intent mainIntent = new Intent(Screen_logo.this,Screen_Setting.class);
                startActivity(mainIntent);
                finish();
            }
        }, 5000);
    }
}
