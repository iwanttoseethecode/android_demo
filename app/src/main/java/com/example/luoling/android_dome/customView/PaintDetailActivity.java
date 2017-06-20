package com.example.luoling.android_dome.customView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.luoling.android_dome.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaintDetailActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.myView)
    MyView myView;
    @BindView(R.id.selectSpinner)
    Spinner selectSpinner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint_detail);
        ButterKnife.bind(this);
        init();
    }

    public void init(){
        selectSpinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String name = parent.getItemAtPosition(position).toString();
        if (name.equals("StrokeCap示例")) {
            myView.setShowId(0);
        } else if (name.equals("stokeJoin示例")) {
            myView.setShowId(1);
        } else if (name.equals("CornerPathEffect示例")) {
            myView.setShowId(2);
        } else if (name.equals("CornerPathEffect DEMO曲线")) {
            myView.setShowId(3);
        } else if (name.equals("DashPathEffect DEMO 效果")) {
            myView.setShowId(4);
        } else if (name.equals("DiscretePathEffect DEMO效果")) {
            myView.setShowId(5);
        } else if (name.equals("PathDashPathEffect效果")) {
            myView.setShowId(6);
        } else if (name.equals("PathDashPathEffect DEMO效果")){
            myView.setShowId(7);
        } else if (name.equals("ComposePathEffect与SumPathEffect")){
            myView.setShowId(8);
        } else if(name.equals("SubpixelText Demo")){
            myView.setShowId(9);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
