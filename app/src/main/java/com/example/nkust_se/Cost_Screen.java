package com.example.nkust_se;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Cost_Screen extends AppCompatActivity {

    Button back_btn;
    Button btn_ok;
    Button btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9,btn_0;
    EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost__screen);

        // 隱藏titla Bar
        getSupportActionBar().hide();

        back_btn = (Button)findViewById(R.id.back_btn);
        btn_ok = (Button)findViewById(R.id.btn_ok);
        btn_1 = (Button)findViewById(R.id.btn_1);
        btn_2 = (Button)findViewById(R.id.btn_2);
        btn_3 = (Button)findViewById(R.id.btn_3);
        btn_4 = (Button)findViewById(R.id.btn_4);
        btn_5 = (Button)findViewById(R.id.btn_5);
        btn_6 = (Button)findViewById(R.id.btn_6);
        btn_7 = (Button)findViewById(R.id.btn_7);
        btn_8 = (Button)findViewById(R.id.btn_8);
        btn_9 = (Button)findViewById(R.id.btn_9);
        btn_0 = (Button)findViewById(R.id.btn_0);
        edit = (EditText)findViewById(R.id.edit);

        btn_1.setOnClickListener(listener);
        btn_2.setOnClickListener(listener);
        btn_3.setOnClickListener(listener);
        btn_4.setOnClickListener(listener);
        btn_5.setOnClickListener(listener);
        btn_6.setOnClickListener(listener);
        btn_7.setOnClickListener(listener);
        btn_8.setOnClickListener(listener);
        btn_9.setOnClickListener(listener);
        btn_0.setOnClickListener(listener);

        back_btn.setOnClickListener(new View.OnClickListener() {  //返回建
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent();
                intent.setClass(Cost_Screen.this,BuyWhat_Screen.class);

                Bundle cost = new Bundle();  //Bundle用於資料傳遞 以key value方式儲存資料
                cost.putInt("花費",Integer.parseInt(edit.getText().toString()));  //要將輸入的數字傳到下個頁面
                intent.putExtras(cost);

                startActivity(intent);
            }
        });
    }

    private Button.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_1:
                    display("1");
                    break;
                case R.id.btn_2:
                    display("2");
                    break;
                case R.id.btn_3:
                    display("3");
                    break;
                case R.id.btn_4:
                    display("4");
                    break;
                case R.id.btn_5:
                    display("5");
                    break;
                case R.id.btn_6:
                    display("6");
                    break;
                case R.id.btn_7:
                    display("7");
                    break;
                case R.id.btn_8:
                    display("8");
                    break;
                case R.id.btn_9:
                    display("9");
                    break;
                case R.id.btn_0:
                    display("0");
                    break;
            }

        }
    };

    private void display(String s){
        String str = edit.getText().toString();
        edit.setText(str + s);
    }
}
