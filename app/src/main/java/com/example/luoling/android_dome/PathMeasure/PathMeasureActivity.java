package com.example.luoling.android_dome.PathMeasure;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.luoling.android_dome.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PathMeasureActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.selectSpinner)
    Spinner selectSpinner;
    @BindView(R.id.grabageView)
    GrabageView grabageView;
    @BindView(R.id.pathMeasureView)
    PathMeasureBasicView pathMeasureView;
    @BindView(R.id.smileLoadingView)
    SmileLoadingView smileLoadingView;
    @BindView(R.id.loadingView)
    LoadingView loadingView;
    @BindView(R.id.shipView)
    ShipView shipView;

    private SparseArray<View> sparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_measure);
        ButterKnife.bind(this);
        selectSpinner.setOnItemSelectedListener(this);
        init();
    }

    private void init() {
        sparseArray = new SparseArray<>();
        sparseArray.put(0, grabageView);
        sparseArray.put(1, pathMeasureView);
        sparseArray.put(2, smileLoadingView);
        sparseArray.put(3, loadingView);
        sparseArray.put(4, shipView);
    }

    private void setVisible(int num) {
        for (int i = 0; i < sparseArray.size(); i++) {
            if (i == num) {
                sparseArray.get(i).setVisibility(View.VISIBLE);
                continue;
            }
            sparseArray.get(i).setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String name = (String) parent.getItemAtPosition(position);
        if (name.equals("垃圾桶")) {
            setVisible(0);
        } else if (name.equals("PathMeasure基础")) {
            setVisible(1);
        } else if (name.equals("加载动画")) {
            setVisible(2);
        } else if (name.equals("加载圈")) {
            setVisible(3);
        } else if (name.equals("小船飘飘")) {
            setVisible(4);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
