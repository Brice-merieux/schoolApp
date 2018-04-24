package com.example.lenvovo.projet_app2.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lenvovo.projet_app2.Database.List_database;
import com.example.lenvovo.projet_app2.models.Item;

import java.util.ArrayList;

/**
 * Created by lenvovo on 11/12/2017.
 */

public class List_service {
    private SQLiteDatabase Rdb;
    private SQLiteDatabase Wdb;

    public List_service(Context context) {
        List_database helper = new List_database(context);
        Rdb=helper.getReadableDatabase();
        Wdb=helper.getWritableDatabase();
    }
public void save(Item c){
    ContentValues values =new ContentValues();
    values.put(List_database.student_name,c.getStd_name());
    values.put(String.valueOf(List_database.student_number),c.getStd_number());
    values.put(List_database.list_name,c.getList_name());
    Wdb.insert(List_database.list_name,"id",values);
}
public ArrayList<Item>findAll(){
    ArrayList<Item> list=new ArrayList<>();
Cursor cursor=Rdb.query(List_database.list_name,null,null,null,null,null,null);
int indexID=cursor.getColumnIndex(List_database.ID);
int list_name_index=cursor.getColumnIndex(List_database.list_name);
int student_name_index=cursor.getColumnIndex(List_database.student_name);
int student_number_index=cursor.getColumnIndex(String.valueOf(List_database.student_number));

int id;
String list_name;
String student_name;
int student_number;

for(cursor.moveToFirst();cursor.isAfterLast();cursor.moveToNext()){
   id=cursor.getInt(indexID);
   list_name=cursor.getString(list_name_index);
   student_name=cursor.getString(student_name_index);
   student_number=cursor.getInt(student_name_index);
Item b=new Item(list_name,student_name,student_number);
b.id=id;
list.add(b);
}

   return list;
}
public void DeleteById(int id){
    Wdb.delete(List_database.list_name,"id",new String[]{String.valueOf(id)});
}
public Item findById(int id){
    Item b;

    Cursor cursor=Rdb.query(List_database.list_name,null,null,null,null,null,null);
    int indexID=cursor.getColumnIndex(List_database.ID);
    int list_name_index=cursor.getColumnIndex(List_database.list_name);
    int student_name_index=cursor.getColumnIndex(List_database.student_name);
    int student_number_index=cursor.getColumnIndex(String.valueOf(List_database.student_number));

    String list_name;
    String student_name;
    int student_number;
cursor.moveToFirst();
    list_name=cursor.getString(list_name_index);
    student_name=cursor.getString(student_name_index);
    student_number=cursor.getInt(student_name_index);
    b=new Item(list_name,student_name,student_number);
    b.id=id;
    cursor.close();
    return b ;
}
}