package com.nkust_csie.se_project;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Screen_history extends AppCompatActivity {

    SQLiteDB DH = null;
    SQLiteDatabase db;

    ListView list_history;
    Cursor cs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_history);

        getSupportActionBar().hide();

        DH = new SQLiteDB(this);
        db = DH.getWritableDatabase();

        list_history = (ListView)findViewById(R.id.list_history);

        show_cost_history();
    }

    private void show_cost_history(){
        cs = db.query("tb_cost_history",null,null,null,null,null,null);
        cs.moveToFirst();
        List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
        for(int i= 0;i< cs.getCount();i++){
            Map<String,Object> item = new HashMap<String,Object>();
            item.put("_id",cs.getString(0));  //id
            item.put("Date",cs.getString(1));  //日期
            item.put("Category",cs.getString(2));  //類別
            item.put("Description",cs.getString(3));  //備註
            item.put("Account",cs.getString(4));  //帳戶
            item.put("Money",cs.getString(5));  //金額
            items.add(item);
            cs.moveToNext();
        }
        SimpleAdapter SA = new SimpleAdapter(this,items,R.layout.cost_list_layout,new String[]{"_id","Date","Category","Money","Account","Description"},new int[]{R.id.cid,R.id.da,R.id.cl,R.id.co,R.id.ac,R.id.re});
        list_history.setAdapter(SA);
    }
}
