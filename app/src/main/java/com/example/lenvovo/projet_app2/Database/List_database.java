package com.example.lenvovo.projet_app2.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lenvovo on 11/9/2017.
 */


public class List_database extends SQLiteOpenHelper {

    public final static String student_name="std_name";
    public final static int student_number=1;
    public final static String ID="id";
    public final static String list_name="list_name";
    public final static String DATABASE_NAME="List_database";
    public final static  int version=1;

    public List_database(Context context) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create if not exits"+list_name+"("+ID+" integer primary key autoincrement, "+
                student_name+"text"+student_number+"integer);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
