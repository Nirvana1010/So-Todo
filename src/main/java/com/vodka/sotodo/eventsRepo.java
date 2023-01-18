package com.vodka.sotodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class eventsRepo {
    private MyDBOpenHelper myDBOpenHelper;

    eventsRepo(Context context) {
        myDBOpenHelper = new MyDBOpenHelper(context);
    }

    int insert(Events event) {
        long num;
        SQLiteDatabase db = myDBOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", event.username);
        values.put("date", event.date);
        values.put("event", event.event);
        values.put("time", event.time);
        num = db.insert("timeCount", null, values);
        db.close();
        return (int)num;
    }

    public void delete(int eventId) {
        SQLiteDatabase db = myDBOpenHelper.getWritableDatabase();
        db.delete("timeCount", "id=?", new String[] {String.valueOf(eventId)});
        db.close();
    }

    public void update(Events event) {
        SQLiteDatabase db = myDBOpenHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("username", event.username);
        values.put("date", event.date);
        values.put("event", event.event);
        values.put("time", event.time);

        db.update("timeCount", values, "id=?", new String[] {String.valueOf(event.id)});
        db.close();
    }

    ArrayList<Events> getEventsList() {
        SQLiteDatabase db = myDBOpenHelper.getReadableDatabase();
        ArrayList<Events> events = new ArrayList<Events>();
        Cursor cursor = db.rawQuery("select id, username, date, event, time from timeCount", null);

        if(cursor.moveToFirst()) {
            do {
                Events event = new Events();
                event.id = cursor.getInt(cursor.getColumnIndex("id"));
                event.username = cursor.getString(cursor.getColumnIndex("username"));
                event.date = cursor.getString(cursor.getColumnIndex("date"));
                event.event = cursor.getString(cursor.getColumnIndex("event"));
                event.time = cursor.getInt(cursor.getColumnIndex("time"));
                events.add(event);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return events;
    }

    ArrayList<Events> getDailyEvents(String username, String date) {
        SQLiteDatabase db = myDBOpenHelper.getReadableDatabase();
        ArrayList<Events> events = new ArrayList<Events>();
        Cursor cursor = db.rawQuery("select * from timeCount where username=? and date=?",
                new String[]{username, date});
        if(cursor.moveToFirst()) {
            do {
                Events event = new Events();
                event.id = cursor.getInt(cursor.getColumnIndex("id"));
                event.username = cursor.getString(cursor.getColumnIndex("username"));
                event.date = cursor.getString(cursor.getColumnIndex("date"));
                event.event = cursor.getString(cursor.getColumnIndex("event"));
                event.time = cursor.getInt(cursor.getColumnIndex("time"));
                events.add(event);
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return events;
    }

    ArrayList<Events> getMonthlyEvents(String username, String date) {
        SQLiteDatabase db = myDBOpenHelper.getReadableDatabase();
        ArrayList<Events> events = new ArrayList<Events>();
        Cursor cursor = db.rawQuery("select * from timeCount where username=? and date like ?",
                new String[]{username, date});
        if(cursor.moveToFirst()) {
            do {
                Events event = new Events();
                event.id = cursor.getInt(cursor.getColumnIndex("id"));
                event.username = cursor.getString(cursor.getColumnIndex("username"));
                event.date = cursor.getString(cursor.getColumnIndex("date"));
                event.event = cursor.getString(cursor.getColumnIndex("event"));
                event.time = cursor.getInt(cursor.getColumnIndex("time"));
                events.add(event);
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return events;
    }
}
