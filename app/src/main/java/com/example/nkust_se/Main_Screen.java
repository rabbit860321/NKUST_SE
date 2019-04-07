package com.example.nkust_se;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.time.Year;
import java.util.Calendar;
import java.util.Date;

public class Main_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__screen);

        TextView Date_View = (TextView)findViewById(R.id.Date_View);
        Calendar rightnow = Calendar.getInstance();
        Button left_btn = (Button)findViewById(R.id.left_btn);
        Button right_btn = (Button)findViewById(R.id.right_btn);

        int year = rightnow.get(Calendar.YEAR);     //取出年月日
        int month = rightnow.get(Calendar.MONTH)+1;
        int day = rightnow.get(Calendar.DAY_OF_MONTH);

        Date_View.setText(year+"年"+month+"月"+day+"日");
        left_btn.setText("<");
        right_btn.setText(">");


   }
}
