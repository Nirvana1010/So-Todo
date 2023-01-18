package com.vodka.sotodo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;

    private static final String DATABASE_NAME = "TimeManagement.db";

    MyDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        String sql = "create table timeCount(" +
                "id integer primary key autoincrement," +
                "username text, " +
                "date text, " +
                "event text, " +
                "time integer);";
        db.execSQL(sql);
    }

    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("drop table if exists timeCount");

        onCreate(db);
    }
}
