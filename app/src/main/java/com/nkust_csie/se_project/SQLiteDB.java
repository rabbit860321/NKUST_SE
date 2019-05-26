package com.nkust_csie.se_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDB extends SQLiteOpenHelper {

    private final static String DB = "DB2019.db"; //資料庫名稱
    private final static int VS = 2;              //版本
    private final static String tb_setting = "tb_setting"; //資料表名稱
    private final static String tb_cost_class1 = "tb_cost_class1";
    private final static String tb_cost_class2 = "tb_cost_class2";
    private final static String tb_cost_history = "tb_cost_history";


    public SQLiteDB(Context context) {
        super(context, DB, null, VS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Create_tb_setting(db);
        Create_tb_cost_class1(db);
        Create_tb_cost_class2(db);
        Create_tb_cost_history(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void Create_tb_setting(SQLiteDatabase db){
        String SQL = "CREATE TABLE IF NOT EXISTS "+tb_setting+"(_id INTEGER primary key autoincrement,Category INTEGER,Description TEXT,Account TEXT,Money FLOAT,Frequency TEXT,Date INTEGER,IsFavoriteAccount BOOLEAN)";
        db.execSQL(SQL);
    }
    public void Create_tb_cost_class1(SQLiteDatabase db){
        String SQL = "CREATE TABLE IF NOT EXISTS "+tb_cost_class1+"(_id INTEGER primary key autoincrement,mainclass TEXT)";  //主分類花費項目資料表
        db.execSQL(SQL);
    }
    public void Create_tb_cost_class2(SQLiteDatabase db){
        String SQL = "CREATE TABLE IF NOT EXISTS "+tb_cost_class2+"(_id INTEGER primary key autoincrement,mainclass TEXT,subclass TEXT)";  //副分類花費項目資料表
        db.execSQL(SQL);
    }
    public void Create_tb_cost_history(SQLiteDatabase db){
        String SQL = "CREATE TABLE IF NOT EXISTS "+tb_cost_history+"(_id INTEGER primary key autoincrement,Date INTEGER,Category TEXT,Description TEXT,Account TEXT,Money FLOAT,PayorEarn TEXT,FavoriteChecked BOOLEAN)";
        db.execSQL(SQL);
    }


    //Insert data into database; this is for add a new account.
    public Boolean insertData(String account, String money){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Account",account);
        contentValues.put("Money",Float.parseFloat(money));

        //db.insert(tb_setting, null, contentValues);
        long result = db.insert(tb_setting, null, contentValues);

        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean insertData(String date, String category,String description,String account,String money,String payorEarn,Boolean favoriteChecked){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Date",Integer.parseInt(date));
        contentValues.put("Category",category);
        contentValues.put("Description",description);
        contentValues.put("Account",account);
        contentValues.put("Money",Float.parseFloat(money));
        contentValues.put("PayorEarn",payorEarn);
        contentValues.put("FavoriteChecked",favoriteChecked);

        long result = db.insert(tb_cost_history, null, contentValues);

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

        contentValues.put("Account",account);
        contentValues.put("Money",Float.parseFloat(money));

        long result = db.update("tb_setting",contentValues,"_id" + "=" + id,null);

        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public void cost_class1_init(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("mainclass", "餐飲");
        db.insert("tb_cost_class1", null, cv);
        cv.put("mainclass", "服飾美容");
        db.insert("tb_cost_class1", null, cv);
        cv.put("mainclass", "居家生活");
        db.insert("tb_cost_class1", null, cv);
        cv.put("mainclass", "交通");
        db.insert("tb_cost_class1", null, cv);
        cv.put("mainclass", "學習");
        db.insert("tb_cost_class1", null, cv);
        cv.put("mainclass", "休閒");
        db.insert("tb_cost_class1", null, cv);
        cv.put("mainclass", "3C");
        db.insert("tb_cost_class1", null, cv);
        cv.put("mainclass", "汽機車");
        db.insert("tb_cost_class1", null, cv);
        cv.put("mainclass", "醫療");
        db.insert("tb_cost_class1", null, cv);
        cv.put("mainclass", "其他");
        db.insert("tb_cost_class1", null, cv);
    }

    public void cost_class2_init(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("mainclass","餐飲");
        cv.put("subclass","早餐");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","餐飲");
        cv.put("subclass","午餐");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","餐飲");
        cv.put("subclass","晚餐");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","餐飲");
        cv.put("subclass","消夜");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","服飾美容");
        cv.put("subclass","衣服");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","服飾美容");
        cv.put("subclass","褲子");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","服飾美容");
        cv.put("subclass","鞋子");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","服飾美容");
        cv.put("subclass","剪髮");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","服飾美容");
        cv.put("subclass","保養品");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","居家生活");
        cv.put("subclass","家俱");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","居家生活");
        cv.put("subclass","房租");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","居家生活");
        cv.put("subclass","電費");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","交通");
        cv.put("subclass","公車");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","交通");
        cv.put("subclass","捷運");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","交通");
        cv.put("subclass","計程車");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","學習");
        cv.put("subclass","文具");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","學習");
        cv.put("subclass","補習");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","休閒");
        cv.put("subclass","電影");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","休閒");
        cv.put("subclass","玩具");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","休閒");
        cv.put("subclass","展覽");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","休閒");
        cv.put("subclass","運動");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","休閒");
        cv.put("subclass","旅行");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","3C");
        cv.put("subclass","電話費");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","3C");
        cv.put("subclass","電腦商品");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","3C");
        cv.put("subclass","手機配件");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","汽機車");
        cv.put("subclass","油錢");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","汽機車");
        cv.put("subclass","停車費");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","汽機車");
        cv.put("subclass","維修保養");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","汽機車");
        cv.put("subclass","罰單");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","醫療");
        cv.put("subclass","就醫");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","醫療");
        cv.put("subclass","藥物");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","醫療");
        cv.put("subclass","勞健保費");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","其他");
        cv.put("subclass","捐款");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","其他");
        cv.put("subclass","寵物");
        db.insert("tb_cost_class2",null,cv);
        cv.put("mainclass","其他");
        cv.put("subclass","雜支");
        db.insert("tb_cost_class2",null,cv);
    }
}
