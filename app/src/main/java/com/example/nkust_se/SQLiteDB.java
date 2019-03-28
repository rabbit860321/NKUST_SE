package com.example.nkust_se;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDB extends SQLiteOpenHelper {

    private final static String DB = "DB2019.db";             //資料庫名稱
    private final static String AccountTB = "AccountTB";      //帳戶資料表名稱
    private final static int VS = 2;

    public SQLiteDB(Context context){
        super(context,DB,null,VS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        CreateAccountTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void CreateAccountTable(SQLiteDatabase db){
        String SQL = "CREATE TABLE IF NOT EXISTS "+AccountTB+"(_id INTEGER primary key autoincrement,帳戶名 TEXT,金額 INTEGER)";  //帳戶資料表
        db.execSQL(SQL);
    }
}
