package com.vodka.sotodo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class todoDBHelper extends SQLiteOpenHelper {
    private static final String TB_NAME1 = "tb_work";
    private static final String TB_NAME2 = "tb_play";
    private static final String TB_NAME3 = "tb_life";
    private static final String TB_NAME4 = "tb_others";

    private Context m_context;

    todoDBHelper(Context context, String db_name) {
        super(context, db_name, null, 1);
        m_context = context;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TB_NAME1 + "( _id integer primary key autoincrement,"
                + "title varchar," + "remark varchar,"+ "time varchar" + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TB_NAME2 + "( _id integer primary key autoincrement,"
                + "title varchar," + "remark varchar,"+ "time varchar" + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TB_NAME3 + "( _id integer primary key autoincrement,"
                + "title varchar," + "remark varchar,"+ "time varchar" + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TB_NAME4 + "( _id integer primary key autoincrement,"
                + "title varchar," + "remark varchar,"+ "time varchar" + ")");
        Toast.makeText(m_context, "Create ok. ", Toast.LENGTH_LONG).show();
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TB_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TB_NAME3);
        db.execSQL("DROP TABLE IF EXISTS " + TB_NAME4);
        onCreate(db);
    }
}
