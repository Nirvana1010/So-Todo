package com.vodka.sotodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

class todoDB {
    private SQLiteDatabase myDb;
    private todoDBHelper dbHelper;

    todoDB(Context context) {
        dbHelper = new todoDBHelper(context, "things.db");
    }

    Cursor getAll(String TB_NAME){
        try {
            myDb = dbHelper.getReadableDatabase();
            return myDb.rawQuery("select * from " + TB_NAME,null);
        } catch (Exception e) {
            e.printStackTrace();  // 打印错误堆栈信息
        }
        return null;
    }

    int getNum(String TB_NAME){
        try {
            myDb = dbHelper.getReadableDatabase();
            Cursor cursor = myDb.rawQuery("select * from " + TB_NAME,null);
            int num = cursor.getCount();
            cursor.close();
            return num;
        } catch (Exception e) {
            e.printStackTrace();  // 打印错误堆栈信息
        }
        return -1;
    }

    ToDoList getInfo(String TB_NAME, int id) {
        ToDoList tdl = new ToDoList();
        try {
            myDb = dbHelper.getReadableDatabase();
            Cursor cursor = myDb.rawQuery("select * from " + TB_NAME + " where _id=?",
                    new String []{ String.valueOf(id) });
            if (cursor!=null && cursor.getCount()!=0) {
                cursor.moveToNext();
                String title = cursor.getString(1);
                String remark = cursor.getString(2);
                String time = cursor.getString(3);

                // 添加
                tdl.setId(id);
                tdl.setTitle(title);
                tdl.setRemark(remark);
                tdl.setDate(time);

                cursor.close();
            }
            return tdl;
        } catch (Exception e) {
            e.printStackTrace();  // 打印错误堆栈信息
        }
        return tdl;
    }

    boolean insertInfo(String TB_NAME, String title, String remark, String time){
        try {
            myDb = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("title", title);
            values.put("remark", remark);
            values.put("time", time);
            long suc = myDb.insert(TB_NAME,null, values);
            return suc != -1;
        } catch (Exception e) {
            e.printStackTrace();  // 打印错误堆栈信息
        }
        return false;
    }

    boolean deleteInfo(String TB_NAME, int id){
        try {
            myDb = dbHelper.getWritableDatabase();
            String where = "_id=?";
            String []whereValue = { String.valueOf(id) };
            int suc = myDb.delete(TB_NAME, where, whereValue);
            return suc != -1;
        } catch (Exception e) {
            e.printStackTrace();  // 打印错误堆栈信息
        }
        return false;
    }

    boolean updateInfo(String TB_NAME, int id, String title, String remark, String time) {
        try {
            // 可写的SQLiteDatabase对象
            myDb = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("title", title);
            values.put("remark", remark);
            values.put("time", time);
            int suc = myDb.update(TB_NAME, values, "_id=?", new String[]{ String.valueOf(id) });
            return suc != -1;
        } catch (Exception e) {
            e.printStackTrace();  // 打印错误堆栈信息
        }
        return false;
    }

    void clearInfo(String TB_NAME) {
        try {
            myDb = dbHelper.getWritableDatabase();
            myDb.execSQL("DELETE FROM " + TB_NAME);
            myDb.execSQL("DELETE FROM sqlite_sequence WHERE name = '" + TB_NAME + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
