package com.nkust_csie.se_project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import org.xmlpull.v1.XmlPullParser;

public class Screen_Setting extends AppCompatActivity {

    Button btn_account;
    Button btn_fixedIncome;
    Button btn_fixedBill;
    Button btn_bill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_setting);

        // 隱藏title Bar
        getSupportActionBar().hide();

        btn_account = (Button)findViewById(R.id.btn_account);
        btn_fixedIncome = (Button)findViewById(R.id.btn_fixedIncome);
        btn_fixedBill = (Button)findViewById(R.id.btn_fixedBill);
        btn_bill = (Button)findViewById(R.id.btn_bill);

        btn_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog(R.layout.dialog_account);
            }
        });

        btn_fixedIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog(R.layout.dialog_fixedincome);
            }
        });

        btn_fixedBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog(R.layout.dialog_fixedbill);
            }
        });

        btn_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog(R.layout.dialog_bill);
            }
        });
    }

    private void showdialog(int x){
        LayoutInflater inflater = LayoutInflater.from(Screen_Setting.this);
        final View EntryView = inflater.inflate(x,null);

        AlertDialog.Builder dialog = new AlertDialog.Builder(Screen_Setting.this);
        dialog.setView(EntryView);

        dialog.setPositiveButton("確認", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }
}
