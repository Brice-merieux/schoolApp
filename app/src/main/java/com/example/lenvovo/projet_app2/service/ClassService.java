package com.example.lenvovo.projet_app2.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.lenvovo.projet_app2.models.Class;
import com.example.lenvovo.projet_app2.Database.data_base;

import java.util.ArrayList;

/**
 * Created by lenvovo on 10/21/2017.
 */

public class ClassService {
    private SQLiteDatabase Rdb;
    private SQLiteDatabase Wdb;
    public ClassService(Context context) {
        data_base helper = new data_base(context);
        Rdb = helper.getReadableDatabase();
        Wdb = helper.getWritableDatabase();
    }
    //保存一节课信息
    public void save(Class c) {
        ContentValues values = new ContentValues();
        values.put(data_base.CLASSNAME,c.getClass_name());
        values.put(data_base.TEACHERNAME, c.getTeacher_name());
        values.put(data_base.CLASSROOM, c.getClassroom());
        values.put(data_base.WEEK, c.getWeek());
        values.put(data_base.START, c.getStart());
        values.put(data_base.LENGTH, c.getLength());
        Wdb.insert(data_base.TABLENAME, "id", values);
    }
    //查询数据库中所有课程信息
    public ArrayList<Class> findAll() {
        ArrayList<Class> list = new ArrayList<>();
        Cursor cursor = Rdb.query(data_base.TABLENAME, null, null, null, null, null, null);
        int idIndex = cursor.getColumnIndex(data_base.ID);
        int classNameIndex = cursor.getColumnIndex(data_base.CLASSNAME);
        int teacherNameIndex = cursor.getColumnIndex(data_base.TEACHERNAME);
        int classroomIndex = cursor.getColumnIndex(data_base.CLASSROOM);
        int weekIndex = cursor.getColumnIndex(data_base.WEEK);
        int startIndex = cursor.getColumnIndex(data_base.START);
        int lengthIndex = cursor.getColumnIndex(data_base.LENGTH);

        int id;
        String className;
        String teacherName;
        String classroom;
        int week;
        int start;
        int length;

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            id = cursor.getInt(idIndex);
            className = cursor.getString(classNameIndex);
            teacherName = cursor.getString(teacherNameIndex);
            classroom = cursor.getString(classroomIndex);
            week = cursor.getInt(weekIndex);
            start = cursor.getInt(startIndex);
            length = cursor.getInt(lengthIndex);

            Class c = new Class(className, teacherName, classroom, week, start, length);
            c.id = id;
            list.add(c);
        }

        cursor.close();
        return list;
    }
    //根据id删除课程信息
    public void deleteById(int id) {
        Wdb.delete(data_base.TABLENAME, "id=?", new String[] {String.valueOf(id)});
    }
    //根据id查询课程信息
    public Class findById(int id) {
        Class c;
        Cursor cursor = Rdb.query(data_base.TABLENAME, null, "id=?", new String[]{String.valueOf(id)}, null, null, null);
        int classNameIndex = cursor.getColumnIndex(data_base.CLASSNAME);
        int teacherNameIndex = cursor.getColumnIndex(data_base.TEACHERNAME);
        int classroomIndex = cursor.getColumnIndex(data_base.CLASSROOM);
        int weekIndex = cursor.getColumnIndex(data_base.WEEK);
        int startIndex = cursor.getColumnIndex(data_base.START);
        int lengthIndex = cursor.getColumnIndex(data_base.LENGTH);

        String className;
        String teacherName;
        String classroom;
        int week;
        int start;
        int length;
        cursor.moveToFirst();
        className = cursor.getString(classNameIndex);
        teacherName = cursor.getString(teacherNameIndex);

        classroom = cursor.getString(classroomIndex);
        week = cursor.getInt(weekIndex);
        start = cursor.getInt(startIndex);
        length = cursor.getInt(lengthIndex);

        c = new Class(className, teacherName, classroom, week, start, length);
        c.id = id;
        cursor.close();
        return c;
    }
}
