package com.example.luoling.android_dome.tagetcricle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.luoling.android_dome.R;

public class TagetCricleActivity extends AppCompatActivity {

    private BullsEyeView mBullsEyeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taget_cricle);
        mBullsEyeView = (BullsEyeView)findViewById(R.id.bullbiew);
    }
}
