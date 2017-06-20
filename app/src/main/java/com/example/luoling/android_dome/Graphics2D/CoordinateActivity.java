package com.example.luoling.android_dome.Graphics2D;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.luoling.android_dome.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoordinateActivity extends AppCompatActivity {

    @BindView(R.id.MyCoordianteView)
    CoordinateView MyCoordianteView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinate);
        ButterKnife.bind(this);
    }
}
