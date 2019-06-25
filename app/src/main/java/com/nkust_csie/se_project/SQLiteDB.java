package com.nkust_csie.se_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDB extends SQLiteOpenHelper {

    private final static String DB = "DB2019.db"; //資料庫名稱
    private final static int VS = 2;              //版本

    private final static String tb_account = "tb_account";
    private final static String tb_cost_history = "tb_cost_history";
    private final static String tb_cost_class1 = "tb_cost_class1";
    private final static String tb_cost_class2 = "tb_cost_class2";
    private final static String tb_cost_fav = "tb_cost_fav";


    public SQLiteDB(Context context) {
        super(context, DB, null, VS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Create_tb_account(db);
        Create_tb_cost_class1(db);
        Create_tb_cost_class2(db);
        Create_tb_cost_history(db);
        Create_tb_cost_fav(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void Create_tb_account(SQLiteDatabase db){
        String SQL = "CREATE TABLE IF NOT EXISTS "+tb_account+"(_id INTEGER primary key autoincrement,帳戶名稱 TEXT,帳戶金額 INTEGER)";
        db.execSQL(SQL);
    }
    public void Create_tb_cost_history(SQLiteDatabase db){
        String SQL = "CREATE TABLE IF NOT EXISTS "+tb_cost_history+"(_id INTEGER primary key autoincrement,日期 INTEGER,支出類別 TEXT,備註 TEXT,支出帳戶 TEXT,支出金額 INTEGER,常用支出 BOOLEAN)";
        db.execSQL(SQL);
    }
    public void Create_tb_cost_class1(SQLiteDatabase db){
        String SQL = "CREATE TABLE IF NOT EXISTS "+tb_cost_class1+"(_id INTEGER primary key autoincrement,支出主分類 TEXT)";  //主分類花費項目資料表
        db.execSQL(SQL);
    }
    public void Create_tb_cost_class2(SQLiteDatabase db){
        String SQL = "CREATE TABLE IF NOT EXISTS "+tb_cost_class2+"(_id INTEGER primary key autoincrement,支出主分類 TEXT,支出副分類 TEXT)";  //副分類花費項目資料表
        db.execSQL(SQL);
    }
    public void Create_tb_cost_fav(SQLiteDatabase db){
        String SQL = "CREATE TABLE IF NOT EXISTS "+tb_cost_fav+"(_id INTEGER primary key autoincrement,支出類別 TEXT,支出帳戶 TEXT,支出金額 INTEGER)";
        db.execSQL(SQL);
    }


    public void cost_class1_init(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("支出主分類", "餐飲");
        db.insert("tb_cost_class1", null, cv);
        cv.put("支出主分類", "服飾美容");
        db.insert("tb_cost_class1", null, cv);
        cv.put("支出主分類", "居家生活");
        db.insert("tb_cost_class1", null, cv);
        cv.put("支出主分類", "交通");
        db.insert("tb_cost_class1", null, cv);
        cv.put("支出主分類", "學習");
        db.insert("tb_cost_class1", null, cv);
        cv.put("支出主分類", "休閒");
        db.insert("tb_cost_class1", null, cv);
        cv.put("支出主分類", "3C");
        db.insert("tb_cost_class1", null, cv);
        cv.put("支出主分類", "汽機車");
        db.insert("tb_cost_class1", null, cv);
        cv.put("支出主分類", "醫療");
        db.insert("tb_cost_class1", null, cv);
        cv.put("支出主分類", "其他");
        db.insert("tb_cost_class1", null, cv);
    }

    public void cost_class2_init(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("支出主分類","餐飲");
        cv.put("支出副分類","早餐");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","餐飲");
        cv.put("支出副分類","午餐");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","餐飲");
        cv.put("支出副分類","晚餐");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","餐飲");
        cv.put("支出副分類","消夜");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","服飾美容");
        cv.put("支出副分類","衣服");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","服飾美容");
        cv.put("支出副分類","褲子");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","服飾美容");
        cv.put("支出副分類","鞋子");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","服飾美容");
        cv.put("支出副分類","剪髮");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","服飾美容");
        cv.put("支出副分類","保養品");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","居家生活");
        cv.put("支出副分類","家俱");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","居家生活");
        cv.put("支出副分類","房租");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","居家生活");
        cv.put("支出副分類","電費");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","交通");
        cv.put("支出副分類","公車");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","交通");
        cv.put("支出副分類","捷運");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","交通");
        cv.put("支出副分類","計程車");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","學習");
        cv.put("支出副分類","文具");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","學習");
        cv.put("支出副分類","補習");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","休閒");
        cv.put("支出副分類","電影");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","休閒");
        cv.put("支出副分類","玩具");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","休閒");
        cv.put("支出副分類","展覽");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","休閒");
        cv.put("支出副分類","運動");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","休閒");
        cv.put("支出副分類","旅行");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","3C");
        cv.put("支出副分類","電話費");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","3C");
        cv.put("支出副分類","電腦商品");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","3C");
        cv.put("支出副分類","手機配件");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","汽機車");
        cv.put("支出副分類","油錢");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","汽機車");
        cv.put("支出副分類","停車費");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","汽機車");
        cv.put("支出副分類","維修保養");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","汽機車");
        cv.put("支出副分類","罰單");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","醫療");
        cv.put("支出副分類","就醫");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","醫療");
        cv.put("支出副分類","藥物");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","醫療");
        cv.put("支出副分類","勞健保費");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","其他");
        cv.put("支出副分類","捐款");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","其他");
        cv.put("支出副分類","寵物");
        db.insert("tb_cost_class2",null,cv);
        cv.put("支出主分類","其他");
        cv.put("支出副分類","雜支");
        db.insert("tb_cost_class2",null,cv);
    }
}
