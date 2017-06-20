package com.example.luoling.android_dome.chapter6;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.luoling.android_dome.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Chapter6Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    @BindView(R.id.mySpinner)
    Spinner mySpinner;
    @BindView(R.id.fistView)
    FistView fistView;

    private SparseArray<View> ViewSparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter6);
        ButterKnife.bind(this);
        mySpinner.setOnItemSelectedListener(this);
        init();
    }

    private void init() {
        ViewSparseArray = new SparseArray<View>();
        ViewSparseArray.put(0,fistView);
    }

    private void setVisiblity(int n){
        int length = ViewSparseArray.size();
        for(int i=0;i<length ; i++){
            if (n == i){
                ViewSparseArray.get(i).setVisibility(View.VISIBLE);
                continue;
            }
            ViewSparseArray.get(i).setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String name = parent.getItemAtPosition(position).toString();
        if (name.equals("text自定义控件")){
            setVisiblity(0);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
