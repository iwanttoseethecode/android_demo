package com.example.luoling.android_dome.radar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.luoling.android_dome.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RadarActivity extends AppCompatActivity {

    @BindView(R.id.mRadarView)
    RadarView mRadarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar);
        ButterKnife.bind(this);
    }

    public void start(View view) {
        mRadarView.startScan();
    }

    public void stop(View view){
        mRadarView.stopScan();
    }

}
