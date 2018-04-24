package com.example.lenvovo.projet_app2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import com.example.lenvovo.projet_app2.first.MainActivity;
import com.example.lenvovo.projet_app2.models.Class;
import com.example.lenvovo.projet_app2.service.ClassService;
/**
 * Created by lenvovo on 10/21/2017.
 */

class AddClass extends Activity{
    private LinearLayout container;
    private static final String[] weeks = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
    private static final String[] start = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11","12","13","14"};
    private static final String[] length = {"1", "2", "3"};
    private ArrayList<LinearLayout> list;
    private ClassService classService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_class);
        classService = new ClassService(getBaseContext());
        Button add_detail_btn = (Button) findViewById(R.id.add_detail_btn);
        container = (LinearLayout)findViewById(R.id.container);
        list = new ArrayList<>();
        add_detail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout temp = createLayout();
                container.addView(temp);
            }
        });

        Button submit_btn = (Button) findViewById(R.id.submit_btn);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //读取所有课程细节信息,存入数据库
                if (list.size() == 0 || list == null) {
                    Toast.makeText(AddClass.this, "NOTHING TO REGISTER", Toast.LENGTH_LONG).show();
                } else {
                    //得到课程信息,储存到数据库
                    saveClassInfo();
                    AddClass.this.onBackPressed();
                }
            }
        });

        Button del_btn = (Button) findViewById(R.id.del_last_btn);

        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.size() == 0 || list == null) {
                    Toast.makeText(AddClass.this, "NOTHING TO DELETE!", Toast.LENGTH_LONG).show();
                } else {
                    LinearLayout temp = list.get(list.size() - 1);
                    list.remove(list.size() - 1);
                    container.removeView(temp);
                    AddClass.this.onBackPressed();
                }
            }
        });
    }

    private LinearLayout createLayout() {
        LinearLayout res = new LinearLayout(getBaseContext());
        LinearLayout up = new LinearLayout(getBaseContext());
        LinearLayout down = new LinearLayout(getBaseContext());

        up.setOrientation(LinearLayout.HORIZONTAL);
        down.setOrientation(LinearLayout.HORIZONTAL);
        res.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 20, 0, 0);
        res.setLayoutParams(params);
        res.setGravity(Gravity.TOP|Gravity.CENTER);

        ArrayAdapter<String> adapter_week = new ArrayAdapter<>(this, R.layout.spinner_item, weeks);
        adapter_week.setDropDownViewResource(R.layout.dropdown);
        ArrayAdapter<String> adapter_length = new ArrayAdapter<>(this, R.layout.spinner_item, length);
        adapter_length.setDropDownViewResource(R.layout.dropdown);
        ArrayAdapter<String> adapter_start = new ArrayAdapter<>(this, R.layout.spinner_item, start);
        adapter_start.setDropDownViewResource(R.layout.dropdown);

        Spinner spinner_week = createSpinner();
        spinner_week.setAdapter(adapter_week);
        Spinner spinner_start = createSpinner();
        spinner_start.setAdapter(adapter_start);
        Spinner spinner_length = createSpinner();
        spinner_length.setAdapter(adapter_length);

        EditText editText = new EditText(getBaseContext());
        editText.setBackgroundResource(R.color.gris);
        editText.setAlpha(0.75f);
        editText.setTextColor(Color.rgb(0,0,0));
        editText.setTextSize(15f);

        LinearLayout.LayoutParams layoutParamsWrap = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams layoutParamsMatch = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsMatch.setMargins(0,5,0,0);

        TextView textView1 = createText("从第");
        TextView textView2 = createText("节 上");
        TextView textView3 = createText("节下");
        TextView textView4 = createText("教室");

        up.addView(spinner_week, layoutParamsWrap);
        up.addView(textView1, layoutParamsWrap);
        up.addView(spinner_start, layoutParamsWrap);
        up.addView(textView2, layoutParamsWrap);
        up.addView(spinner_length, layoutParamsWrap);
        up.addView(textView3, layoutParamsWrap);

        down.addView(textView4);
        down.addView(editText, layoutParamsMatch);

        res.addView(up, layoutParamsWrap);
        res.addView(down, layoutParamsMatch);

        list.add(res);

        return res;
    }

    private Spinner createSpinner() {
        Spinner spinner = new Spinner(getBaseContext());
        spinner.setBackgroundResource(R.color.gris);
        spinner.setAlpha(0.75f);
        return spinner;
    }

    private TextView createText(String string) {
        TextView textView = new TextView(getBaseContext());
        textView.setText(string);
        textView.setTextColor(Color.rgb(255,255,255));
        textView.setShadowLayer(2,5,5,Color.BLACK);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    private void saveClassInfo() {
        Class c;
        c = new Class();
        EditText editText = (EditText) findViewById(R.id.class_name);
        String class_name = editText.getText().toString();
        editText = (EditText) findViewById(R.id.teacher_name);
        String teacher_name = editText.getText().toString();

        c.setClass_name(class_name);
        c.setTeacher_name(teacher_name);

        for (LinearLayout curLayout : list) {
            LinearLayout up = (LinearLayout)curLayout.getChildAt(0);
            Spinner spinner = (Spinner)up.getChildAt(0);//取得星期
            int week = spinner.getSelectedItemPosition();
            spinner = (Spinner)up.getChildAt(2);//取得开始
            int start = spinner.getSelectedItemPosition();
            spinner = (Spinner)up.getChildAt(4);
            int length = spinner.getSelectedItemPosition();

            LinearLayout down = (LinearLayout)curLayout.getChildAt(1);
            EditText et = (EditText)down.getChildAt(1);
            String classroom = et.getText().toString();
            //Toast.makeText(AddClass.this, String.valueOf(week), Toast.LENGTH_LONG).show();
            c.setWeek(week);
            c.setStart(start);
            c.setLength(length + 1);
            c.setClassroom(classroom);
            //储存进数据库
            classService.save(c);
        }
    }
}
