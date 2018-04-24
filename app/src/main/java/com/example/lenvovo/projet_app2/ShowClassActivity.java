package com.example.lenvovo.projet_app2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.lenvovo.projet_app2.service.ClassService;
import com.example.lenvovo.projet_app2.models.Class;
/**
 * Created by lenvovo on 10/21/2017.
 */

class ShowClassActivity extends Activity {
    private static final String[] weeks = {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};
    private int id;
    private ClassService classService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_class);
        classService = new ClassService(getBaseContext());
        Button del_btn = (Button) findViewById(R.id.del_btn);
        Intent intent = getIntent();
        id = intent.getIntExtra("id" , -1);
        TextView show_class_name = (TextView) findViewById(R.id.show_class_name);
        TextView show_teacher_name = (TextView) findViewById(R.id.show_teacher_name);
        TextView show_place = (TextView) findViewById(R.id.show_place);
        TextView show_time = (TextView) findViewById(R.id.show_time);
        Class c = classService.findById(id);
        show_class_name.setText("course name: " + c.getClass_name());
        show_teacher_name.setText("teacher name: " + c.getTeacher_name());
        show_place.setText("classroom: " + c.getClassroom());
        String time = "Time:\n\n";
        time += weeks[c.getWeek()]+" "+"第";
        int cur = c.getStart()+1;
        for (int i = 0; i < c.getLength(); i++, cur++) {
            time += " " + cur;
        }
        time += "session";
        show_time.setText(time);

        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classService.deleteById(id);
                ShowClassActivity.this.onBackPressed();
                overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
            }
        });
    }

}
