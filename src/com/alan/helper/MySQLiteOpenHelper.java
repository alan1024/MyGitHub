package com.alan.helper;

/**
 * Created by aaa on 15-4-10.
 * User:alan
 * Date:
 * Email:
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    private final static String DBNAME = "db_bill.db";
    private final static int VERSION = 1;
    private SQLiteDatabase db ;

    public MySQLiteOpenHelper(Context context) {
        super(context, DBNAME, null, VERSION);
        db = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS tb_bill(_id INTEGER PRIMARY KEY , typeId not null , money not null , date not null)");
        db.execSQL("CREATE TABLE IF NOT EXISTS tb_type(_id INTEGER PRIMARY KEY , typeId  , type)");
        db.execSQL("insert into tb_type(typeId,type) values(?,?)",new String []{"00","收入：工资"} );
        db.execSQL("insert into tb_type(typeId,type) values(?,?)",new String []{"01","收入：外快"} );
        db.execSQL("insert into tb_type(typeId,type) values(?,?)",new String []{"100","支出：吃-日常"} );
        db.execSQL("insert into tb_type(typeId,type) values(?,?)",new String []{"101","支出：吃-请客"} );
        db.execSQL("insert into tb_type(typeId,type) values(?,?)",new String []{"102","支出：吃-烟酒"} );
        db.execSQL("insert into tb_type(typeId,type) values(?,?)",new String []{"110","支出：穿-自用"} );
        db.execSQL("insert into tb_type(typeId,type) values(?,?)",new String []{"111","支出：穿-礼物"} );
        db.execSQL("insert into tb_type(typeId,type) values(?,?)",new String []{"120","支出：住-房租"} );
        db.execSQL("insert into tb_type(typeId,type) values(?,?)",new String []{"121","支出：住-水电费"} );
        db.execSQL("insert into tb_type(typeId,type) values(?,?)",new String []{"130","支出：行-公交"} );
        db.execSQL("insert into tb_type(typeId,type) values(?,?)",new String []{"131","支出：行-出租"} );
        db.execSQL("insert into tb_type(typeId,type) values(?,?)",new String []{"140","支出：用-学习"} );
        db.execSQL("insert into tb_type(typeId,type) values(?,?)",new String []{"141","支出：用-生活"} );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS tb_bill");
            onCreate(db);
        }
    }

    public Cursor selectCursor(String sql, String[] selectionArgs) {
        return db.rawQuery(sql, selectionArgs);
    }


    public List<Map<String, Object>> selectList(String sql,String[] selectionArgs) {
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        return cursorToList(cursor);
    }

    public int selectCount(String sql, String[] selectionArgs) {
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        int count = cursor.getCount();
        if (cursor != null) {
            cursor.close();
        }
        return count;
    }

    public boolean execData(String sql, Object[] bindArgs) {
        try {
            db.execSQL(sql, bindArgs);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Map<String, Object>> cursorToList(Cursor cursor) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        String[] arrColumnName = cursor.getColumnNames();
        while (cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<String, Object>();
            for (int i = 0; i < arrColumnName.length; i++) {
                map.put(arrColumnName[i], cursor.getString(i));
            }
            list.add(map);
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

}

