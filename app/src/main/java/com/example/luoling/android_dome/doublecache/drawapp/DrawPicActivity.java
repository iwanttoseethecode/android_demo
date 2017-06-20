package com.example.luoling.android_dome.doublecache.drawapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.luoling.android_dome.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrawPicActivity extends AppCompatActivity {

    @BindView(R.id.myDrawView)
    DrawView myDrawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_pic2);
        ButterKnife.bind(this);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

}
