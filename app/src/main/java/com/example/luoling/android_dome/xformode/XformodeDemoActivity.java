package com.example.luoling.android_dome.xformode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.luoling.android_dome.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class XformodeDemoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.selectSpinner)
    Spinner selectSpinner;
    @BindView(R.id.heartMapView)
    HeartMapView heartMapView;
    @BindView(R.id.circleWaveView)
    CircleWaveView circleWaveView;
    @BindView(R.id.eraserView)
    EraserView eraserView;
    @BindView(R.id.guaguaCard)
    GuaGuaCardView guaguaCard;

    private SparseArray<View> viewSparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xformode_demo);
        ButterKnife.bind(this);

        selectSpinner.setOnItemSelectedListener(this);
        init();
    }

    private void init() {
        viewSparseArray = new SparseArray<View>();
        viewSparseArray.put(0, heartMapView);
        viewSparseArray.put(1, circleWaveView);
        viewSparseArray.put(2, eraserView);
        viewSparseArray.put(3,guaguaCard);
    }

    public void setVisible(int num) {
        int size = viewSparseArray.size();
        for (int i = 0; i < size; i++) {
            if (i == num) {
                viewSparseArray.get(i).setVisibility(View.VISIBLE);
                continue;
            }
            viewSparseArray.get(i).setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectName = parent.getItemAtPosition(position).toString();
        if (TextUtils.equals(selectName, "心电图")) {
            setVisible(0);
        } else if (TextUtils.equals(selectName, "波浪圆球图")) {
            setVisible(1);
        } else if (TextUtils.equals(selectName, "橡皮檫控件")) {
            setVisible(2);
        } else  if (TextUtils.equals(selectName,"刮刮卡")){
            setVisible(3);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
