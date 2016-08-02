package com.example.liner.timer02_7.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.liner.timer02_7.model.Timer02;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liner on 16/7/29.
 */
public class TimerDB {

    private static final String TAG = "TimerDB";

    //private Context context;
    public static final String DB_NAME = "timer";
    public static final int VERSION =2;

    //private TimerDBHelper timerDBHelper;
    private static TimerDB timerDB;
    private SQLiteDatabase db;

    private TimerDB(Context context){

        TimerDBHelper dbHelper = new TimerDBHelper(context,DB_NAME,null,VERSION);
        db = dbHelper.getWritableDatabase();

    }

    public synchronized static TimerDB getInstance(Context context){
        if (timerDB == null){
            timerDB = new TimerDB(context);
        }
        return timerDB;
    }

    public void saveTimer(Timer02 timer02){
        if (timer02 != null){
            ContentValues values = new ContentValues();
            values.put("time",timer02.getTime());
            values.put("label","无");
            db.insert(TimerDBHelper.TABLE_NAME,null,values);
        }
    }

    public List<Timer02> loadTheTimer(){
        List<Timer02> list = new ArrayList<Timer02>();
        Cursor cursor = db.query(TimerDBHelper.TABLE_NAME,null,null,null,null,null,"id DESC");
        if (cursor.moveToFirst()){
            do {
                Timer02 timer02 = new Timer02();
                timer02.setId(cursor.getInt(cursor.getColumnIndex("id")));
                timer02.setTime(cursor.getString(cursor.getColumnIndex("time")));
                timer02.setLabel(cursor.getString(cursor.getColumnIndex("label")));
                list.add(timer02);
            }while (cursor.moveToNext());
        }
        if (cursor != null){
            cursor.close();
        }
        return list;
    }

    public void updateLabel(int d,String str){
        ContentValues values = new ContentValues();
        values.put("label",str);
        db.update(TimerDBHelper.TABLE_NAME,values,"id = ?",
                new String[]{String.valueOf(d)});

    }

}
