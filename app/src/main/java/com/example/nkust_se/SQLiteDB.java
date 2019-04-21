package com.example.nkust_se;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDB extends SQLiteOpenHelper {

    private final static String DB = "DB2019.db";             //資料庫名稱
    private final static String AccountTB = "AccountTB";      //帳戶資料表名稱
    private final static String CostTB = "CostTB";            //花費記錄資料表名稱
    private final static String MainClassTB = "MainClassTB";  //主分類花費項目資料表
    private final static String SecondClassTB = "SecondClassTB";  //副分類花費項目資料表
    private final static int VS = 2;

    public SQLiteDB(Context context){
        super(context,DB,null,VS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        CreateAccountTable(db);
        CreateCostTable(db);
        CreateMainClassTable(db);
        CreateSecondClassTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void CreateAccountTable(SQLiteDatabase db){
        String SQL = "CREATE TABLE IF NOT EXISTS "+AccountTB+"(_id INTEGER primary key autoincrement,帳戶名 TEXT,金額 INTEGER)";  //帳戶資料表
        db.execSQL(SQL);
    }

    public void CreateCostTable(SQLiteDatabase db){
        String SQL = "CREATE TABLE IF NOT EXISTS "+CostTB+"(_id INTEGER primary key autoincrement,支出項目 TEXT,金額 INTEGER,帳戶 TEXT,日期 TEXT,備註 TEXT)";  //花費記錄資料表
        db.execSQL(SQL);
    }

    public void CreateMainClassTable(SQLiteDatabase db){
        String SQL = "CREATE TABLE IF NOT EXISTS "+MainClassTB+"(_id INTEGER primary key autoincrement,主分類 TEXT)";  //主分類花費項目資料表
        db.execSQL(SQL);
    }

    public void CreateSecondClassTable(SQLiteDatabase db){
        String SQL = "CREATE TABLE IF NOT EXISTS "+SecondClassTB+"(_id INTEGER primary key autoincrement,主分類 TEXT,副分類 TEXT)";  //副分類花費項目資料表
        db.execSQL(SQL);
    }
}
