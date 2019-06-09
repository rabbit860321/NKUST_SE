package com.nkust_csie.se_project;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Screen_Select_Class extends AppCompatActivity {

    SQLiteDB DH = null;
    SQLiteDatabase db;

    ListView list_cost_class1,list_cost_class2;
    Button btn_add_a_cost2,btn_back;
    Cursor cs;
    ContentValues cv;

    String now_click_left;
    String now_click_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen__select__class);

        // 隱藏title Bar
        getSupportActionBar().hide();

        DH = new SQLiteDB(this);
        db = DH.getWritableDatabase();

        list_cost_class1 = (ListView)findViewById(R.id.list_cost_class1);
        list_cost_class2 = (ListView)findViewById(R.id.list_cost_class2);
        btn_add_a_cost2 = (Button)findViewById(R.id.btn_add_a_cost2);
        btn_back = (Button)findViewById(R.id.btn_back);

        cv = new ContentValues();

        cs = db.query("tb_cost_class1",null,null,null,null,null,null);
        if(cs.getCount() == 0){     //若cost主類別TB無資料 初始化
            DH.cost_class1_init();
        }
        cs = db.query("tb_cost_class2",null,null,null,null,null,null);
        if(cs.getCount() == 0){     //若cost主類別TB無資料 初始化
            DH.cost_class2_init();
        }

        show_costClass1();

        list_cost_class1.setOnItemClickListener(new AdapterView.OnItemClickListener() {           //當左邊列表被點擊時，判斷是支出還是收入
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final TextView Classname = (TextView) view.findViewById(android.R.id.text1);
                now_click_left = Classname.getText().toString();      //抓使用者點了哪個主類別
                show_costClass2();
            }
        });

        list_cost_class2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final TextView sec_name = (TextView) view.findViewById(android.R.id.text1);
                now_click_right = sec_name.getText().toString();

                String margeclass = now_click_left + "-" + now_click_right; //字串:主分類-副分類  ex:餐飲-早餐

                Intent intent = new Intent();
                intent.setClass(Screen_Select_Class.this,Screen_Check.class);

                Bundle b = new Bundle();  //Bundle用於資料傳遞 以key value方式儲存資料
                b.putString("類別",margeclass);  //將margeclass字串傳入ckeck畫面
                intent.putExtras(b);

                finish();
                startActivity(intent);
            }
        });
        list_cost_class2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final TextView cost_sec = (TextView)view.findViewById(android.R.id.text1);
                AlertDialog.Builder obj_Dialog = new AlertDialog.Builder(Screen_Select_Class.this);  //彈出對話方塊
                obj_Dialog.setTitle("確定刪除?");
                obj_Dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete("tb_cost_class2","subclass"+"='"+cost_sec.getText().toString()+"'",null);
                        show_costClass2();
                    }
                });
                obj_Dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                obj_Dialog.show();
                return true;
            }
        });
        btn_add_a_cost2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder obj_Dialog = new AlertDialog.Builder(Screen_Select_Class.this);  //彈出對話方塊
                obj_Dialog.setTitle("新增副類別");

                TableLayout obj_TableLayout = new TableLayout(Screen_Select_Class.this);
                TableRow obj_TableRow1 = new TableRow(Screen_Select_Class.this);

                final EditText obj_et1 = new EditText(Screen_Select_Class.this);
                obj_et1.setWidth(600);
                obj_et1.setInputType(InputType.TYPE_CLASS_TEXT);

                obj_TableRow1.addView(obj_et1);

                obj_TableLayout.addView(obj_TableRow1);

                obj_Dialog.setView(obj_TableLayout);

                obj_Dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cv.clear();
                        cv.put("mainclass",now_click_left);
                        cv.put("subclass",obj_et1.getText().toString());
                        db.insert("tb_cost_class2", null, cv);

                        show_costClass2();
                    }
                });
                obj_Dialog.show();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent gotomain = new Intent(Screen_Select_Class.this,Screen_Main1.class);
                startActivity(gotomain);
                finish();
            }
        });

    }

    private void show_costClass1() {
        cs = db.query("tb_cost_class1",new String[]{"mainclass"},null,null,null,null,null);
        List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
        cs.moveToFirst();
        for(int i= 0;i< cs.getCount();i++){
            Map<String,Object> item = new HashMap<String,Object>();
            item.put("mainclass",cs.getString(0));
            items.add(item);
            cs.moveToNext();
        }
        SimpleAdapter SA = new SimpleAdapter(this,items,android.R.layout.simple_list_item_1,new String[]{"mainclass"},new int[]{android.R.id.text1});
        list_cost_class1.setAdapter(SA);
    }

    private void show_costClass2(){
        cs = db.query("tb_cost_class2",new String[]{"mainclass","subclass"},"mainclass=?",new String[]{now_click_left},null,null,null);
        List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
        cs.moveToFirst();
        for(int j= 0;j< cs.getCount();j++){
            Map<String,Object> item = new HashMap<String,Object>();
            item.put("mainclass",cs.getString(0));
            item.put("subclass",cs.getString(1));
            items.add(item);
            cs.moveToNext();
        }
        SimpleAdapter SA = new SimpleAdapter(this,items,android.R.layout.simple_list_item_1,new String[]{"subclass"},new int[]{android.R.id.text1});
        list_cost_class2.setAdapter(SA);
    }
}
