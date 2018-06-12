package com.example.luoling.android_dome.svg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.luoling.android_dome.R;

public class SVGChinaMapActivity extends AppCompatActivity {

    private ChinaMapView chinaMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_svgchina_map);
        initView();
    }

    private void initView() {
        chinaMapView = (ChinaMapView) findViewById(R.id.chinaMapView);
    }

    @Override
    protected void onDestroy() {
        chinaMapView.destory();
        super.onDestroy();
    }
}
