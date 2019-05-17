package com.nkust_csie.se_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDB extends SQLiteOpenHelper {

    private final static String DB = "DB2019.db"; //資料庫名稱
    private final static int VS = 2;              //版本
    private final static String tb_setting = "tb_setting"; //資料表名稱
    private final static String tb_account = "tb_account";


    public SQLiteDB(Context context) {
        super(context, DB, null, VS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Create_tb_setting(db);
        Create_tb_account(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void Create_tb_setting(SQLiteDatabase db){
        String SQL = "CREATE TABLE IF NOT EXISTS "+tb_setting+"(_id INTEGER primary key autoincrement,分類屬性 TEXT,描述 TEXT,帳戶 TEXT,金額 REAL,幾個月幾次 TEXT,日期 INTEGER,為常用帳戶 BLOB)";
        db.execSQL(SQL);
    }

    public void Create_tb_account(SQLiteDatabase db){
        String SQL = "CREATE TABLE IF NOT EXISTS "+tb_account+"(_id INTEGER primary key autoincrement,帳戶名稱 TEXT,金額 REAL)";
        db.execSQL(SQL);
    }
}
