package com.example.luoling.android_dome.praise;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.luoling.android_dome.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PraiseActivity extends AppCompatActivity {

    @BindView(R.id.loveLayout)
    LoveLayout loveLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_praise);
        ButterKnife.bind(this);
    }

    public void start(View v) {
        loveLayout.addLoveIcon();
    }
}
