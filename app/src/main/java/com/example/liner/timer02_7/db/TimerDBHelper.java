package com.example.liner.timer02_7.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by liner on 16/7/29.
 */
public class TimerDBHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME="Timer";

    public static final String CREATE = "create table " + TABLE_NAME
            + "(id integer primary key autoincrement,"
            + "time text,"
            + "label text)";

    public TimerDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }
}
