package com.nkust_csie.se_project;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.LongSparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class Screen_Check extends AppCompatActivity {

    SQLiteDB DH = null;
    SQLiteDatabase db;

    EditText edit_cost_name,edit_cost_money,edit_re;
    Spinner spinner_account;
    Button btn_del;
    Button btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9,btn_0,btn_dot;
    Button btn_save_cost;
    CheckBox checkbox_fav;
    Calendar today;

    Cursor cs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen__check);

        // 隱藏title Bar
        getSupportActionBar().hide();

        DH = new SQLiteDB(this);
        db = DH.getWritableDatabase();

        today = Calendar.getInstance();
        edit_cost_name = (EditText)findViewById(R.id.edit_cost_name);
        edit_cost_money = (EditText)findViewById(R.id.edit_cost_money);
        edit_re = (EditText)findViewById(R.id.edit_re);
        spinner_account = (Spinner)findViewById(R.id.spinner_account);
        checkbox_fav = (CheckBox)findViewById(R.id.checkbox_fav);
        btn_save_cost = (Button)findViewById(R.id.btn_save_cost);
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
        btn_dot = (Button)findViewById(R.id.btn_dot);

        int M = today.get(Calendar.MONTH)+1;
        final String YMD = ""+today.get(Calendar.YEAR)+""+M+""+today.get(Calendar.DAY_OF_MONTH);   //今天的年月日

        Bundle bundle = getIntent().getExtras();
        final String cost_class_name = bundle.getString("類別");  //抓出從select_class畫面丟過來的類別名稱

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
        btn_dot.setOnClickListener(listener);

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_cost_money.setText("");
            }
        });

        btn_save_cost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account_name = cs.getString(3);
                float now_money = 0;

                //Log.e("TAG",cs.getString(4));  帳戶金額
                if(!edit_cost_money.getText().toString().isEmpty()){  //若輸入金額
                    now_money = Float.parseFloat(cs.getString(4)) - Float.parseFloat(edit_cost_money.getText().toString());  //當前帳戶金額減掉支出金額
                    now_money = (float) (Math.round(now_money*100)/100.0);  //解決精度問題
                }

                if(now_money < 0){
                    show_toast("你不夠錢啦");
                }else if(edit_cost_money.getText().toString().isEmpty()){
                    show_toast("你沒輸入金額啦!");
                }else{
                    DH.insertData(YMD,cost_class_name,edit_re.getText().toString(),account_name,edit_cost_money.getText().toString(),"支出",checkbox_fav.isChecked());  //insert支出紀錄
                    DH.updateData(Integer.parseInt(cs.getString(0)),cs.getString(3),""+now_money);             //update帳戶資料表
                }

                Log.e("LOG",""+YMD+cost_class_name+edit_re.getText().toString()+account_name+edit_cost_money.getText().toString()+"支出"+checkbox_fav.isChecked());
                final Intent gotomain = new Intent(Screen_Check.this,Screen_Main1.class);
                startActivity(gotomain);
                finish();
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
                case R.id.btn_dot:
                    display(".");
                    break;
            }

        }
    };

    private void display(String s){
        String str = edit_cost_money.getText().toString();
        edit_cost_money.setText(str + s);
    }

    private void show_toast(String text){
        Toast toast = Toast.makeText(Screen_Check.this,
                text, Toast.LENGTH_LONG);
        //顯示Toast
        toast.show();
    }
}
