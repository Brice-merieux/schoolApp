package com.example.lenvovo.projet_app2.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lenvovo on 10/20/2017.
 */

public class data_base extends SQLiteOpenHelper {
    public final static String DATABASENAME = "schedule.db";
    public final static String TABLENAME = "class";
    public final static String ID = "id";
    public final static String CLASSNAME = "class_name";
    public final static String TEACHERNAME = "teacher_name";
    public final static String CLASSROOM = "classroom";
    public final static String WEEK = "week";
    public final static String START = "start";
    public final static String LENGTH = "length";
    public final static int VERSION = 1;

    public data_base(Context context) {
        super(context, DATABASENAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists " + TABLENAME + " (" +
                ID + " integer primary key autoincrement, " +
                CLASSNAME + " text, " +
                TEACHERNAME + " text, " +
                CLASSROOM + " text, " +
                WEEK + " integer, " +
                START + " integer, " +
                LENGTH + " integer);";
                db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
