package com.example.lenvovo.projet_app2.List_creation_Functions;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.example.lenvovo.projet_app2.R;
import com.example.lenvovo.projet_app2.R.id;
import com.example.lenvovo.projet_app2.R.layout;
import com.example.lenvovo.projet_app2.models.Item;

import java.util.ArrayList;

public class List extends AppCompatActivity {
    private final ArrayList<String> List=new ArrayList<>();
    private String list_name;
    private Item br;
    private  String std_name;
    private int std_numb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.activity_list);
        this.list_name = this.br.getList_name();
        this.std_name = this.br.getStd_name();
        this.std_numb = this.br.getStd_number();
    }
}
