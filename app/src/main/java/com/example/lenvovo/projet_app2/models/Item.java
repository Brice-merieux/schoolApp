package com.example.lenvovo.projet_app2.models;

/**
 * Created by lenvovo on 11/9/2017.
 */

public class Item {
    public int id;
    private String std_name;
    private int std_number;
    private String list_name;

    public Item(){}
    public Item(String list_name,String std_name,int std_number){
        this.std_name=std_name;
        this.std_number=std_number;
        this.list_name=list_name;
    }
    public void setStd_name(String std_name){
        this.std_name=std_name;
    }
    public String  getStd_name(){
        return std_name;
    }

    public void setStd_number(int std_number){
        this.std_number=std_number;
    }
    public int  getStd_number(){
        return std_number;
    }
    public void setList_name(String list_name){
       this.list_name=list_name;
    }
    public String getList_name(){
        return list_name;
    }
}
