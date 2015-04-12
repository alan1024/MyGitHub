package com.alan.myhomework;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import com.alan.helper.MySQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    private ArcView arcView;
    private MySQLiteOpenHelper db;
    private Button button_time,button_ok;
    private EditText editText;
    private List<Map<String, Object>> list;
    private String[] num={"00","01","100","101","102","110","111","120","121","130","131","140","141"};
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        arcView= (ArcView) findViewById(R.id.arcView);
        list=new ArrayList<Map<String, Object>>();
        db=new MySQLiteOpenHelper(this);
        button_ok= (Button) findViewById(R.id.button_ok);
        editText= (EditText) findViewById(R.id.editText_main);
        button_time= (Button) findViewById(R.id.button_time);
        button_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                MyDatePickerDialog datePickerDialog = new MyDatePickerDialog(
                        MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        editText.setText( i + "-" + (i1+1) );
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
                datePickerDialog.show();
            }
        });

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time = editText.getText().toString();
                float money=0;
                list = db.selectList("select sum(money) count from tb_bill where date =? and typeId=?", new String[]{time,num[0]});
                Object count = list.get(0).get("count");
                if(count!=null){
                    arcView.setWage(Float.parseFloat(list.get(0).get("count").toString()));

                }else{
                    arcView.setWage(0);
                }

                list = db.selectList("select sum(money) count from tb_bill where date =? and typeId=?", new String[]{time,num[1]});
                count = list.get(0).get("count");
                if(count!=null){
                    arcView.setExtra(Float.parseFloat(list.get(0).get("count").toString()));
                }else{
                    arcView.setExtra(0);
                }

                money=0;
                list=db.selectList("select sum(money) count from tb_bill where date =? and typeId=?", new String[]{time,num[2]});
                count = list.get(0).get("count");
                if(count!=null){
                    money=Float.parseFloat(list.get(0).get("count").toString());
                }
                list=db.selectList("select sum(money) count from tb_bill where date =? and typeId=?", new String[]{time, num[3]});
                count = list.get(0).get("count");
                if(count!=null){
                    money=money+Float.parseFloat(count.toString());
                }
                list=db.selectList("select sum(money) count from tb_bill where date =? and typeId=?", new String[]{time, num[4]});
                count = list.get(0).get("count");
                if(count!=null){
                    money=money+Float.parseFloat(list.get(0).get("count").toString());
                }
                arcView.setEatFee(money);




                money=0;
                list=db.selectList("select sum(money) count from tb_bill where date =? and typeId=?", new String[]{time,num[5]});
                count = list.get(0).get("count");
                if(count!=null){
                    money=money+Float.parseFloat(list.get(0).get("count").toString());
                }
                list=db.selectList("select sum(money) count from tb_bill where date =? and typeId=?", new String[]{time,num[6]});
                count = list.get(0).get("count");
                if(count!=null){
                    money=money+Float.parseFloat(list.get(0).get("count").toString());
                }
                arcView.setClothesFee(money);




                money=0;
                list=db.selectList("select sum(money) count from tb_bill where date =? and typeId=?", new String[]{time,num[7]});
                count = list.get(0).get("count");
                if(count!=null){
                    money=money+Float.parseFloat(list.get(0).get("count").toString());
                }
                list=db.selectList("select sum(money) count from tb_bill where date =? and typeId=?", new String[]{time,num[8]});
                count = list.get(0).get("count");
                if(count!=null){
                    money=money+Float.parseFloat(list.get(0).get("count").toString());
                }
                arcView.setLiveFee(money);




                money=0;
                list=db.selectList("select sum(money) count from tb_bill where date =? and typeId=?", new String[]{time,num[9]});
                count = list.get(0).get("count");
                if(count!=null){
                    money=money+Float.parseFloat(list.get(0).get("count").toString());
                }
                list=db.selectList("select sum(money) count from tb_bill where date =? and typeId=?", new String[]{time,num[10]});
                count = list.get(0).get("count");
                if(count!=null){
                    money=money+Float.parseFloat(list.get(0).get("count").toString());
                }
                arcView.setTransFee(money);




                money=0;
                list=db.selectList("select sum(money) count from tb_bill where date =? and typeId=?", new String[]{time,num[11]});
                count = list.get(0).get("count");
                if(count!=null){
                    money=money+Float.parseFloat(list.get(0).get("count").toString());
                }
                list=db.selectList("select sum(money) count from tb_bill where date =? and typeId=?", new String[]{time,num[12]});
                count = list.get(0).get("count");
                if(count!=null){
                    money=money+Float.parseFloat(list.get(0).get("count").toString());
                }
                arcView.setUseFee(money);




                arcView.invalidate();
            }

        });


    }
}
