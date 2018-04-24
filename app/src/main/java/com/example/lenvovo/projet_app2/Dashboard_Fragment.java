package com.example.lenvovo.projet_app2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenvovo.projet_app2.List_creation_Functions.Create_name_list;
import com.example.lenvovo.projet_app2.R.id;
import com.example.lenvovo.projet_app2.R.layout;
import com.example.lenvovo.projet_app2.models.Class;
import com.example.lenvovo.projet_app2.service.ClassService;

import java.util.ArrayList;
import java.util.zip.Deflater;

import static android.app.Activity.RESULT_CANCELED;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Dashboard_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Dashboard_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Dashboard_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final int ADD_CLASS_CODE = 1;
    private static final int SHOW_CLASS_CODE = 2;
    private final RelativeLayout[] week = new RelativeLayout[7];
    private int per_height;
    private int height;
    private boolean hasMeasured;
    private int color_flag = 1;
    private ClassService classService;

    private Dashboard_Fragment.OnFragmentInteractionListener mListener;
    private Deflater EdgeEffectCompatIcs;

    public Dashboard_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Dashboard_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Dashboard_Fragment newInstance(String param1, String param2) {
        Dashboard_Fragment fragment = new Dashboard_Fragment();
        Bundle args = new Bundle();
        args.putString(Dashboard_Fragment.ARG_PARAM1, param1);
        args.putString(Dashboard_Fragment.ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getArguments() != null) {
            this.mParam1 = this.getArguments().getString(Dashboard_Fragment.ARG_PARAM1);
            this.mParam2 = this.getArguments().getString(Dashboard_Fragment.ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(layout.fragment_dashboard_, container, false);
        Menu add_class_menu=(Menu)view.findViewById(R.menu.add_class_menu);
        LinearLayout fragment_dashboard_ = (LinearLayout) view.findViewById(id.dashboard_layout);
        this.classService = new ClassService(this.getContext());

        this.week[0] = (RelativeLayout) view.findViewById(id.mon);
        this.week[1] = (RelativeLayout) view.findViewById(id.tue);
        this.week[2] = (RelativeLayout) view.findViewById(id.wed);
        this.week[3] = (RelativeLayout) view.findViewById(id.thu);
        this.week[4] = (RelativeLayout) view.findViewById(id.fri);
        this.week[5] = (RelativeLayout) view.findViewById(id.sat);
        this.week[6] = (RelativeLayout) view.findViewById(id.sun);
        ViewTreeObserver vto = fragment_dashboard_.getViewTreeObserver();
        vto.addOnPreDrawListener(new OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (!Dashboard_Fragment.this.hasMeasured) {
                    Dashboard_Fragment.this.height = Dashboard_Fragment.this.week[0].getMeasuredHeight();
                    Dashboard_Fragment.this.per_height = Dashboard_Fragment.this.height /14;
                    Dashboard_Fragment.this.hasMeasured = true;
                    //Toast.makeText(MainActivity.this, String.valueOf(height), Toast.LENGTH_LONG).show();
                    Dashboard_Fragment.this.drawClass();
                }
                return true;
            }
        });


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_class_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
 if (id == R.id.add) {
            Intent intent = new Intent();
            intent.setClass(getContext(), AddClass.class);
            startActivityForResult(intent, ADD_CLASS_CODE);
        }
        if (id==R.id.create_list_names) {
            Intent intent = new Intent(getContext(), Create_name_list.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_CLASS_CODE) {
            if (resultCode == RESULT_CANCELED) {
                drawClass();
            }
        } else if (requestCode == SHOW_CLASS_CODE) {
            if (resultCode == RESULT_CANCELED) {
                drawClass();
            }
        }
    }


    private void drawClass() {
        for (int i = 0; i < 7; i++) {
            week[i].removeAllViews();
        }
        ArrayList<Class> classList = classService.findAll();
        if (classList != null) {
            for (Class cur: classList) {
                RelativeLayout class_layout = createClassLayout(cur.getStart(), cur.getLength());
                TextView class_text = createClassInfo(cur.getClass_name()+"\n /@ "+cur.getClassroom());
                class_text.setGravity(Gravity.TOP | Gravity.LEFT);
                Button id_btn = createIdBtn(cur.id);
                class_layout.addView(id_btn);
                class_layout.addView(class_text);
                week[cur.getWeek()].addView(class_layout);
            }
        }
    }

    private TextView createClassInfo(String s) {
        TextView textView = new TextView(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        textView.setText(s);
        textView.setTextColor(Color.BLACK);
        textView.setLayoutParams(params);
        textView.setTextSize(12);
        return textView;
    }

    private RelativeLayout createClassLayout(int start, int length) {
        RelativeLayout layout = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, length * per_height -2);
        if (color_flag == 1) {
            layout.setBackgroundResource(R.color.pink);
        } else if (color_flag == 2){
            layout.setBackgroundResource(R.color.purple);
        } else if (color_flag == 3) {
            layout.setBackgroundResource(R.color.bleu);
        } else if (color_flag == 4){
            layout.setBackgroundResource(R.color.green);
        } else {
            layout.setBackgroundResource(R.color.orange);
        }
        color_flag++;
        color_flag %= 3;

        params.setMargins(2, start* per_height +2, 2, 2);
        layout.setLayoutParams(params);
        return layout;
    }

    private Button createIdBtn(final int id) {
        Button btn = new Button(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        btn.setText(String.valueOf(id));
        btn.setAlpha(0);
        btn.setLayoutParams(params);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("id", id);
                intent.setClass(getContext(), ShowClassActivity.class);
                startActivityForResult(intent, SHOW_CLASS_CODE);
                overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
            }
        });
        return btn;
    }

    private void overridePendingTransition(int abc_slide_in_bottom, int abc_slide_out_top) {
        throw new RuntimeException("Stub!");
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Dashboard_Fragment.OnFragmentInteractionListener) {
            mListener = (Dashboard_Fragment.OnFragmentInteractionListener) context;
        } else {
            Toast.makeText(context,"Dashboard",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
