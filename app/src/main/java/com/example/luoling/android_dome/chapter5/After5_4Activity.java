package com.example.luoling.android_dome.chapter5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.luoling.android_dome.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class After5_4Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    SparseArray<View> viewSparseArray;

    @BindView(R.id.selectSpinner)
    Spinner selectSpinner;
    @BindView(R.id.porterDuffXferView)
    PorterDuffXferView porterDuffXferView;
    @BindView(R.id.circlePhotoSpannableView)
    CirclePhotoSpannableView circlePhotoSpannableView;
    @BindView(R.id.anomalousPhotoView)
    AnomalousPhotoView anomalousPhotoView;
    @BindView(R.id.guagualeView)
    GuaGuaLeView guagualeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after5_4);
        ButterKnife.bind(this);
        selectSpinner.setOnItemSelectedListener(this);

        init();
    }

    public void init() {
        viewSparseArray = new SparseArray<View>();

        viewSparseArray.put(1, porterDuffXferView);
        viewSparseArray.put(2, circlePhotoSpannableView);
        viewSparseArray.put(3, anomalousPhotoView);
        viewSparseArray.put(4,guagualeView);
    }

    public void setVisiblity(int n) {
        for (int i = 1; i <= viewSparseArray.size(); i++) {
            if (i == n) {
                viewSparseArray.get(i).setVisibility(View.VISIBLE);
                continue;
            }
            viewSparseArray.get(i).setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String name = parent.getItemAtPosition(position).toString();
        if (name.equals("图层测试")) {
            setVisiblity(1);
        } else if (name.equals("圆形头像")) {
            setVisiblity(2);
        } else if (name.equals("机器人边界头像")) {
            setVisiblity(3);
        } else if (name.equals("刮刮乐")){
            setVisiblity(4);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
