package com.example.lenvovo.projet_app2.List_creation_Functions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lenvovo.projet_app2.R;
import com.example.lenvovo.projet_app2.models.Item;
import com.example.lenvovo.projet_app2.service.List_service;

import java.util.ArrayList;
import java.util.HashMap;

public class Create_name_list extends AppCompatActivity {
    private HashMap<String, Integer> map = new HashMap<String, Integer>();
    private ArrayList<ListView> List;
    private final int list_length = 0;
    private List_service list_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_name_list);
        Button add=(Button)findViewById(R.id.add_student);
        Button finish=(Button)findViewById(R.id.finish);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             String student_name=((EditText)findViewById(R.id.std_name)).getText().toString();
             int student_numb= Integer.parseInt(((EditText)findViewById(R.id.std_number)).getText().toString());
             String list_name=((EditText)findViewById(R.id.list_name)).getText().toString();
             map.put(student_name,student_numb);
                if(map.size()==0||map==null||list_name==null){
                    Toast.makeText(Create_name_list.this, "nothing to store", Toast.LENGTH_SHORT).show();
                }else{

                    saveListInfos();
                    //Create_name_list.this.onBackPressed();
                }
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Create_name_list.this,List.class);
                startActivity(intent);
            }
        });
    }

    private void saveListInfos() {
        Item item;
        item= new Item();
        EditText student_name=(EditText)findViewById(R.id.std_name);
        String std_name=student_name.getText().toString();

        EditText student_number=(EditText)findViewById(R.id.std_number);
        int std_number= Integer.parseInt(student_number.getText().toString())
                ;
        EditText list_name=(EditText)findViewById(R.id.list_name);
        String list_noun=list_name.getText().toString();

       item.setList_name(list_noun);
       item.setStd_name(std_name);
       item.setStd_number(std_number);
    }
}