package com.example.nkust_se;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Cost_Screen extends AppCompatActivity {

    Button back_btn;
    Button btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost__screen);

        // 隱藏titla Bar
        getSupportActionBar().hide();

        back_btn = (Button)findViewById(R.id.back_btn);
        btn_ok = (Button)findViewById(R.id.btn_ok);

        back_btn.setOnClickListener(new View.OnClickListener() {  //返回建
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoBuywhat = new Intent();
                gotoBuywhat.setClass(Cost_Screen.this,BuyWhat_Screen.class);
                startActivity(gotoBuywhat);
            }
        });
    }
}
