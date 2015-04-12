package com.alan.myhomework;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.alan.helper.MyAdapter;
import com.alan.helper.MySQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by aaa on 15-4-10.
 * User:alan
 * Date:
 * Email:
 */
public class StartActivity extends Activity {
    private ListView listView;
    private TextView textView;
    private List<Map<String, Object>> totalList;
    private List<Map<String, Object>> list;
    private MySQLiteOpenHelper db;
    private MyAdapter adapter;
    private int position;
    private String[] obj={"收入：工资","收入：外快","支出：吃-日常","支出：吃-请客","支出：吃-烟酒","支出：穿-自用","支出：穿-礼物","支出：住-房租","支出：住-水电费","支出：行-公交","支出：行-出租","支出：用-学习","支出：用-生活"};
    private String[] num={"00","01","100","101","102","110","111","120","121","130","131","140","141"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_start);
        listView= (ListView) findViewById(R.id.listView);
        textView= (TextView) findViewById(R.id.textView_start);
        db=new MySQLiteOpenHelper(this);
        totalList=new ArrayList<Map<String, Object>>();
        totalList=db.selectList("select * from tb_bill b  left join tb_type t  where b.typeId= t.typeId",null);
        Log.i("--------",totalList.toString());
        adapter=new MyAdapter(this,totalList);
        listView.setAdapter(adapter);
        listView.setEmptyView(textView);
        registerForContextMenu(listView);

    }

    public void reload(){
        totalList.clear();
        list=db.selectList("select * from tb_bill b  left join tb_type t  where b.typeId= t.typeId",null);
        totalList.addAll(list);
        Log.i("--------",totalList.toString());
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_bt:
                Intent intent=new Intent();
                intent.setClass(this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.action_add:

                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("添加账单");
                View view = getLayoutInflater().inflate(R.layout.layout_dialog,null);
                Spinner spinner= (Spinner) view.findViewById(R.id.spinner);
                final EditText editText= (EditText) view.findViewById(R.id.editText);
                final EditText editText_time= (EditText) view.findViewById(R.id.editText_time);
                Button button= (Button) view.findViewById(R.id.button3);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Calendar calendar = Calendar.getInstance();
                        MyDatePickerDialog datePickerDialog = new MyDatePickerDialog(
                                StartActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                    editText_time.setText( i + "-" + (i1+1) );
                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
                        datePickerDialog.show();

                    }
                });
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, obj);
                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        position=i;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                spinner.setAdapter(adapter);

                builder.setView(view);
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String money=editText.getText().toString();
                        String date=editText_time.getText().toString();
                        if(money!=null&&!money.equals("")&&date!=null&&!date.equals("")){
                            String insertString="insert into tb_bill(typeId,money,date) values(?,?,?)";
                            boolean b=db.execData(insertString, new Object[] { num[position],money,date });
                            if(b){
                                Toast.makeText(StartActivity.this, "添加成功！",Toast.LENGTH_LONG).show();
                                reload();
                            }else{
                                Toast.makeText(StartActivity.this,"添加失败，请重新添加",Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(StartActivity.this,"请输入值！",Toast.LENGTH_LONG).show();
                        }

                    }
                });
                builder.show();
                break;
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.contextmenu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final String id=totalList.get(info.position).get("_id").toString();
        switch (item.getItemId()){
            case R.id.action_del:
                AlertDialog.Builder builder1=new AlertDialog.Builder(this);
                builder1.setIcon(R.drawable.ic_launcher);
                builder1.setTitle("删除");
                builder1.setMessage("确认删除吗！");
                builder1.setNegativeButton("取消", null);
                builder1.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String del="delete from tb_bill where _id=?";
                        boolean b=db.execData(del, new Object[]{id});
                        if(b){
                            Toast.makeText(StartActivity.this, "删除OK", Toast.LENGTH_SHORT).show();
                            reload();
                        }
                    }
                });
                builder1.show();
                break;
        }
        return super.onContextItemSelected(item);
    }



}