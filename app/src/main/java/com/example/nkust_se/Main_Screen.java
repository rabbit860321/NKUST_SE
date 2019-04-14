package com.example.nkust_se;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

public class Main_Screen extends AppCompatActivity {

    TextView Date_View;
    Calendar rightnow;
    Button expense_btn;
    Button income_btn;
    int year;
    int month;
    int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__screen);

        // 隱藏titla Bar
        getSupportActionBar().hide();

        Date_View = (TextView)findViewById(R.id.Date_View);
        expense_btn = (Button)findViewById(R.id.expense_btn);
        income_btn = (Button)findViewById(R.id.income_btn);
        rightnow = Calendar.getInstance();

        year = rightnow.get(Calendar.YEAR);     //取出年月日
        month = rightnow.get(Calendar.MONTH)+1;
        day = rightnow.get(Calendar.DAY_OF_MONTH);

        Date_View.setText(year+"年"+month+"月"+day+"日");
   }
}
