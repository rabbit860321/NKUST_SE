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
import android.view.LayoutInflater;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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

    Cursor tr_cs1,tr_cs2; //轉帳畫面用

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
        show_today_cost(YMD);

        findViewById(R.id.btn_menu_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent gotosetting = new Intent(Screen_Main1.this,Screen_Setting.class);
                startActivity(gotosetting);
                finish();
            }
        });
        findViewById(R.id.btn_menu_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent gotohistory = new Intent(Screen_Main1.this,Screen_history.class);
                startActivity(gotohistory);
                finish();
            }
        });
        findViewById(R.id.btn_menu_stat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent gotostat = new Intent(Screen_Main1.this,Screen_stat.class);
                startActivity(gotostat);
                finish();
            }
        });
        findViewById(R.id.floatbtn_fav).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder obj_Dialog = new AlertDialog.Builder(Screen_Main1.this);  //彈出對話方塊
                final GridView gv = new GridView(Screen_Main1.this);
                gv.setNumColumns(-1);  //auto
                gv.setHorizontalSpacing(5);
                gv.setVerticalSpacing(5);

                show_gridview(gv);

                gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {  //長案刪除
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        final TextView fav_id = (TextView) view.findViewById(R.id.gv_id);
                        db.delete("tb_cost_fav","_id"+"="+fav_id.getText().toString(),null);

                        show_gridview(gv);
                        return true;
                    }
                });

                gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final TextView gv_name = (TextView) view.findViewById(R.id.gv_name);
                        final TextView gv_money = (TextView) view.findViewById(R.id.gv_money);
                        final TextView gv_account = (TextView) view.findViewById(R.id.gv_account);


                        cs = db.query("tb_setting",null,"Account=?",new String[]{gv_account.getText().toString()},null,null,null);
                        cs.moveToFirst();
                        float now_money = Float.parseFloat(cs.getString(4)) - Float.parseFloat(gv_money.getText().toString());  //當前帳戶金額減掉支出金額
                        now_money = (float) (Math.round(now_money*100)/100.0);

                        if(now_money < 0){
                            show_toast("你錢不夠啦!");
                        }else{

                            ContentValues cv = new ContentValues();
                            cv.put("Date",YMD);
                            cv.put("Category",gv_name.getText().toString());
                            cv.put("Account",gv_account.getText().toString());
                            cv.put("Money",Float.parseFloat(gv_money.getText().toString()));
                            db.insert("tb_cost_history",null,cv);

                            cv.clear();
                            cv.put("Money",now_money);

                            db.update("tb_setting",cv,"Account"+"='"+gv_account.getText().toString()+"'",null);
                        }

                        show_list_account();
                        show_list_today_cost(YMD);
                    }
                });



                obj_Dialog.setView(gv);
                obj_Dialog.show();
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
                        show_today_cost(YMD);
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
            AlertDialog.Builder obj_Dialog = new AlertDialog.Builder(Screen_Main1.this);  //彈出對話方塊
            final ListView obj_lv1 = new ListView(Screen_Main1.this);
            final ListAdapter adv = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{"支出", "收入", "轉帳"});
            obj_lv1.setAdapter(adv);

            obj_lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position){
                        case 0:  //支出
                            final Intent gotoselclass = new Intent(Screen_Main1.this,Screen_Select_Class.class);
                            startActivity(gotoselclass);
                            break;
                        case 1:  //收入
                            LayoutInflater inflater = LayoutInflater.from(Screen_Main1.this);
                            final View EntryView = inflater.inflate(R.layout.dialog_income,null);

                            final EditText edit_income_money = EntryView.findViewById(R.id.edit_income_money);
                            final Spinner sp_income_account = EntryView.findViewById(R.id.sp_income_account);

                            AlertDialog.Builder dialog = new AlertDialog.Builder(Screen_Main1.this);
                            dialog.setView(EntryView);  //dialog設置自定義畫面


                            cs = db.query("tb_setting",null,null,null,null,null,null);
                            cs.moveToFirst();
                            final SimpleCursorAdapter adapter = new SimpleCursorAdapter(Screen_Main1.this, android.R.layout.simple_spinner_item ,cs, new String[] { "Account" }, new int[] {android.R.id.text1});
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            sp_income_account.setAdapter(adapter);

                            dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //需先查詢該帳戶目前有多少錢 並加輸入金額加上
                                    //cs.getString(3) 使用者選擇的帳戶
                                    //cs.getString(4) 使用者選擇的帳戶的原金額


                                    if(edit_income_money.getText().toString().isEmpty()){
                                        show_toast("你沒輸入金額啦!");
                                    }else{
                                        float money = Float.parseFloat(cs.getString(4)) + Float.parseFloat(edit_income_money.getText().toString());
                                        money = (float) (Math.round(money*100)/100.0);  //解決精度問題
                                        ContentValues cv = new ContentValues();
                                        cv.put("Money",money);
                                        db.update("tb_setting",cv,"Account"+"='"+cs.getString(3)+"'",null);
                                        show_list_account();
                                    }
                                }
                            });
                            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                            dialog.show();
                            break;
                        case 2: //轉帳
                            //須將左邊帳戶原金額扣掉使用者輸入的金額 並把右邊帳戶加上使用者輸入的金額
                            LayoutInflater LI = LayoutInflater.from(Screen_Main1.this);
                            final View EV = LI.inflate(R.layout.dialog_transfer,null);

                            final Spinner sp_tr_1 = EV.findViewById(R.id.sp_tr_1);
                            final Spinner sp_tr_2 = EV.findViewById(R.id.sp_tr_2);
                            final EditText edit_tr_money = EV.findViewById(R.id.edit_tr_money);

                            AlertDialog.Builder tr_dialog = new AlertDialog.Builder(Screen_Main1.this);
                            tr_dialog.setView(EV);

                            tr_cs1 = db.query("tb_setting",null,null,null,null,null,null);
                            tr_cs2 = db.query("tb_setting",null,null,null,null,null,null);

                            final SimpleCursorAdapter ad1 = new SimpleCursorAdapter(Screen_Main1.this, android.R.layout.simple_spinner_item ,tr_cs1, new String[] { "Account" }, new int[] {android.R.id.text1});
                            ad1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            final SimpleCursorAdapter ad2 = new SimpleCursorAdapter(Screen_Main1.this, android.R.layout.simple_spinner_item ,tr_cs2, new String[] { "Account" }, new int[] {android.R.id.text1});
                            ad2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sp_tr_1.setAdapter(ad1);
                            sp_tr_2.setAdapter(ad2);

                            tr_dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //tr_cs1.getString(3)左邊帳戶名稱
                                    //tr_cs1.getString(4)左邊帳戶金額
                                    //tr_cs2.getString(3)右邊帳戶名稱
                                    //tr_cs2.getString(4)右邊帳戶金額

                                    /*Log.e("TAG",tr_cs1.getString(3)+tr_cs2.getString(3));
                                    Log.e("TAG",tr_cs1.getString(4)+tr_cs2.getString(4));*/

                                    if(edit_tr_money.getText().toString().isEmpty()){
                                        show_toast("你沒輸入金額啦!");
                                    }else if(tr_cs1.getString(3).equals(tr_cs2.getString(3))){
                                        show_toast("請選擇兩個不一樣的帳戶");
                                    }else if(Float.parseFloat(tr_cs1.getString(4)) < Float.parseFloat(edit_tr_money.getText().toString())){
                                        show_toast("該帳戶餘額不足");
                                    }else{
                                        float money = Float.parseFloat(tr_cs1.getString(4)) - Float.parseFloat(edit_tr_money.getText().toString());
                                        money = (float) (Math.round(money*100)/100.0);  //解決精度問題
                                        ContentValues cv = new ContentValues();
                                        cv.put("Money",money);
                                        db.update("tb_setting",cv,"Account"+"='"+tr_cs1.getString(3)+"'",null);

                                        cv.clear();

                                        money = Float.parseFloat(tr_cs2.getString(4)) + Float.parseFloat(edit_tr_money.getText().toString());
                                        money = (float) (Math.round(money*100)/100.0);  //解決精度問題
                                        cv.put("Money",money);
                                        db.update("tb_setting",cv,"Account"+"='"+tr_cs2.getString(3)+"'",null);

                                        show_list_account();
                                    }


                                }
                            });

                            tr_dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                            tr_dialog.show();
                            break;
                    }
                }
            });

            obj_Dialog.setView(obj_lv1);
            obj_Dialog.show();

            /*final Intent gotoselclass = new Intent(Screen_Main1.this,Screen_Select_Class.class);
            startActivity(gotoselclass);*/
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
            items.add(item);
            cs.moveToNext();
        }
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

    private void show_today_cost(String YMD){
        today_cost_total = 0;
        cs = db.query("tb_cost_history",null,"Date=?",new String[]{YMD},null,null,null);
        cs.moveToFirst();
        for(int i= 0;i< cs.getCount();i++){
            today_cost_total += Float.parseFloat(cs.getString(5));
            cs.moveToNext();
        }
        txt_today_cost_total.setText(""+today_cost_total+"元");
    }
    private void show_toast(String text){
        Toast toast = Toast.makeText(Screen_Main1.this,
                text, Toast.LENGTH_LONG);
        //顯示Toast
        toast.show();
    }
    private void show_gridview(GridView gv){
        cs = db.query("tb_cost_fav",null,null,null,null,null,null);
        cs.moveToFirst();
        List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
        for(int i= 0;i< cs.getCount();i++){
            Map<String,Object> item = new HashMap<String,Object>();
            item.put("_id",cs.getString(0));
            item.put("Category",cs.getString(1));
            item.put("Account",cs.getString(2));
            item.put("Money",cs.getString(3));
            items.add(item);
            cs.moveToNext();
        }
        SimpleAdapter SA = new SimpleAdapter(Screen_Main1.this, items, R.layout.fav_gridview_layout, new String[]{"_id","Category","Account","Money"}, new int[]{R.id.gv_id, R.id.gv_name,R.id.gv_account,R.id.gv_money});
        gv.setAdapter(SA);
    }
}
