package com.vodka.sotodo;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

class ThingsUtils {
    static List<ToDoList> getThings(Context context, String TB_NAME) {
        final List<ToDoList> iToDoList = new ArrayList<>();
        // 检索所有文件

        todoDB myDB = new todoDB(context);
        Cursor cursor = myDB.getAll(TB_NAME);
        if (cursor!=null) {
            while (cursor.moveToNext()) {  // 遍历
                ToDoList tdl = new ToDoList();
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String remark = cursor.getString(2);
                String time = cursor.getString(3);
                // 添加到列表项
                tdl.setId(id);
                tdl.setTitle(title);
                tdl.setRemark(remark);
                tdl.setDate(time);

                iToDoList.add(tdl);
            }
            cursor.close();
        }
        return iToDoList;
    }

    static ToDoList getThing(Context context, String TB_NAME, int id) {
        todoDB myDB = new todoDB(context);
        return  myDB.getInfo(TB_NAME, id);
    }

    static boolean addThings(Context context, String TB_NAME, String title, String remark, String time) {
        todoDB myDB = new todoDB(context);
        return myDB.insertInfo(TB_NAME, title, remark, time);
    }

    static boolean delThings(Context context, String TB_NAME, int id) {
        todoDB myDB = new todoDB(context);
        return myDB.deleteInfo(TB_NAME, id);
    }

    static boolean updThings(Context context, String TB_NAME, int id, String title, String remark, String time) {
        todoDB myDB = new todoDB(context);
        return myDB.updateInfo(TB_NAME, id, title, remark, time);
    }

    static int clearThings(Context context, String TB_NAME) {
        todoDB myDB = new todoDB(context);
        int num = myDB.getNum(TB_NAME);
        myDB.clearInfo(TB_NAME);
        return num;
    }
}
