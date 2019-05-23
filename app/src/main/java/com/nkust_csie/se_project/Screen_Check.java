package com.nkust_csie.se_project;

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

public class Screen_Check extends AppCompatActivity {

    SQLiteDB DH = null;
    SQLiteDatabase db;

    EditText edit_cost_name,edit_cost_money;
    Spinner spinner_account;
    Button btn_del;
    Button btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9,btn_0;


    Cursor cs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen__check);

        // 隱藏title Bar
        getSupportActionBar().hide();

        DH = new SQLiteDB(this);
        db = DH.getWritableDatabase();

        edit_cost_name = (EditText)findViewById(R.id.edit_cost_name);
        edit_cost_money = (EditText)findViewById(R.id.edit_cost_money);
        spinner_account = (Spinner)findViewById(R.id.spinner_account);
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
        btn_del = (Button)findViewById(R.id.btn_del);

        Bundle bundle = getIntent().getExtras();
        String cost_class_name = bundle.getString("類別");  //抓出從select_class畫面丟過來的類別名稱

        //Log.e("TAG",cost_class_name);

        edit_cost_name.setText(cost_class_name);

        edit_cost_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent gotoselectclass = new Intent(Screen_Check.this,Screen_Select_Class.class);
                startActivity(gotoselectclass);
                finish();
            }
        });

        cs = db.query("tb_setting",null,null,null,null,null,null);

        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item ,cs, new String[] { "Account" }, new int[] {android.R.id.text1});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);   //帳戶spinner 載入資料
        spinner_account.setAdapter(adapter);

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

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_cost_money.setText("");
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
        String str = edit_cost_money.getText().toString();
        edit_cost_money.setText(str + s);
    }

}
