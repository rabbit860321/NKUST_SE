package com.nkust_csie.se_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDB extends SQLiteOpenHelper {

    private final static String DB = "DB2019.db"; //資料庫名稱
    private final static int VS = 2;              //版本
    private final static String tb_setting = "tb_setting"; //資料表名稱


    public SQLiteDB(Context context) {
        super(context, DB, null, VS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Create_tb_setting(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void Create_tb_setting(SQLiteDatabase db){
        String SQL = "CREATE TABLE IF NOT EXISTS "+tb_setting+"(_id INTEGER primary key autoincrement,分類屬性 INTEGER,描述 TEXT,帳戶 TEXT,金額 FLOAT,幾個月幾次 TEXT,日期 INTEGER,為常用帳戶 BOOLEAN)";
        db.execSQL(SQL);
    }


    //Insert data into database; this is for add a new account.
    public boolean insertData(String account, String money){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("帳戶",account);
        contentValues.put("金額",Float.parseFloat(money));

        long result = db.insert(tb_setting, null, contentValues);

        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean updateData(int id,String account, String money){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("帳戶",account);
        contentValues.put("金額",Float.parseFloat(money));

        long result = db.update("tb_setting",contentValues,"_id" + "=" + id,null);

        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

}
