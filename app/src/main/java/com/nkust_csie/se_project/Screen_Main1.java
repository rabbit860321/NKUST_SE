package com.nkust_csie.se_project;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Screen_Main1 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SQLiteDB DH = null;
    SQLiteDatabase db;

    ListView list_today_cost;
    Cursor cs;
    Calendar today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen__main1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("錢包:878787元");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        DH = new SQLiteDB(this);
        db = DH.getWritableDatabase();
        today = Calendar.getInstance();
        list_today_cost = (ListView)findViewById(R.id.list_today_cost);

        int M = today.get(Calendar.MONTH)+1;
        final String YMD = ""+today.get(Calendar.YEAR)+""+M+""+today.get(Calendar.DAY_OF_MONTH);   //今天的年月日

        show_list_today_cost(YMD);

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
            finish();
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
            item.put("Category",cs.getString(2));
            item.put("Description",cs.getString(3));
            item.put("Account",cs.getString(4));
            item.put("Money",cs.getString(5));
            items.add(item);
            cs.moveToNext();
        }
        SimpleAdapter SA = new SimpleAdapter(this,items,R.layout.cost_list_layout,new String[]{"Category","Money","Account","Description"},new int[]{R.id.cl,R.id.co,R.id.ac,R.id.re});
        list_today_cost.setAdapter(SA);
    }
}
