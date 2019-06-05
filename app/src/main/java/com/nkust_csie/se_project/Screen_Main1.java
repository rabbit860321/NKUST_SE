package com.nkust_csie.se_project;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Screen_Main1 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SQLiteDB DH = null;
    SQLiteDatabase db;

    ListView list_today_cost,list_account;
    TextView txt_today_cost_total;
    Cursor cs;
    ContentValues cv;
    Calendar today;

    float today_cost_total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen__main1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        DH = new SQLiteDB(this);
        db = DH.getWritableDatabase();
        cv = new ContentValues();
        today = Calendar.getInstance();
        list_today_cost = (ListView)findViewById(R.id.list_today_cost);
        list_account = (ListView)findViewById(R.id.list_account);
        txt_today_cost_total = (TextView)findViewById(R.id.txt_today_cost_total);

        int M = today.get(Calendar.MONTH)+1;
        final String YMD = ""+today.get(Calendar.YEAR)+""+M+""+today.get(Calendar.DAY_OF_MONTH);   //今天的年月日

        show_list_today_cost(YMD);  //顯示今日花費
        show_list_account();        //顯示當前帳戶資料

        findViewById(R.id.btn_menu_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent gotosetting = new Intent(Screen_Main1.this,Screen_Setting.class);
                startActivity(gotosetting);
                finish();
            }
        });

        list_today_cost.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final TextView cost_id = (TextView) view.findViewById(R.id.cid);
                final TextView cost_money = (TextView)view.findViewById(R.id.co);
                final TextView cost_account = (TextView)view.findViewById(R.id.ac);


                AlertDialog.Builder obj_Dialog = new AlertDialog.Builder(Screen_Main1.this);  //彈出對話方塊
                obj_Dialog.setTitle("確定刪除?");
                obj_Dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete("tb_cost_history","_id"+"="+cost_id.getText().toString(),null);

                        cs = db.query("tb_setting",null,"Account=?",new String[]{cost_account.getText().toString()},null,null,null);
                        cs.moveToNext();
                        Log.e("TAG",""+cs.getString(4));  //cs.getString(4)  當前該帳戶金額
                        Log.e("TAG",""+cost_money.getText().toString()); //你點的那筆支出紀錄的金額


                        float now_money = Float.parseFloat(cs.getString(4)) + Float.parseFloat(cost_money.getText().toString());  //刪除支出資料 要加回去
                        now_money = (float) (Math.round(now_money*100)/100.0);  //解決精度問題
                        Log.e("TAG",""+now_money);  //加回去後
                        cv.clear();
                        cv.put("Money",now_money);
                        db.update("tb_setting",cv,"_id"+"="+cs.getString(0),null);

                        show_list_account();
                        show_list_today_cost(YMD);
                    }
                });
                obj_Dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                obj_Dialog.show();
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.screen__main1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.btn_new) {
            final Intent gotoselclass = new Intent(Screen_Main1.this,Screen_Select_Class.class);
            startActivity(gotoselclass);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        /*if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void show_list_today_cost(String YMD){
        cs = db.query("tb_cost_history",null,"Date=?",new String[]{YMD},null,null,null);
        List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
        cs.moveToFirst();
        for(int i= 0;i< cs.getCount();i++){
            Map<String,Object> item = new HashMap<String,Object>();
            item.put("_id",cs.getString(0));
            item.put("Category",cs.getString(2));
            item.put("Description",cs.getString(3));
            item.put("Account",cs.getString(4));
            item.put("Money",cs.getString(5));
            today_cost_total += Float.parseFloat(cs.getString(5));
            items.add(item);
            cs.moveToNext();
        }
        txt_today_cost_total.setText(""+today_cost_total);
        SimpleAdapter SA = new SimpleAdapter(this,items,R.layout.cost_list_layout,new String[]{"_id","Category","Money","Account","Description"},new int[]{R.id.cid,R.id.cl,R.id.co,R.id.ac,R.id.re});
        list_today_cost.setAdapter(SA);
    }

    private void show_list_account(){
        cs = db.query("tb_setting",null,null,null,null,null,null);
        List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
        cs.moveToFirst();
        for(int i= 0;i< cs.getCount();i++){
            Map<String,Object> item = new HashMap<String,Object>();
            item.put("_id",cs.getString(0));
            item.put("Account",cs.getString(3));
            item.put("Money",cs.getString(4));
            items.add(item);
            cs.moveToNext();
        }
        SimpleAdapter SA = new SimpleAdapter(this,items,R.layout.account_list_layout,new String[]{"_id","Account","Money"},new int[]{R.id.account_list_id,R.id.account_list_name,R.id.account_list_money});
        list_account.setAdapter(SA);
    }
}
