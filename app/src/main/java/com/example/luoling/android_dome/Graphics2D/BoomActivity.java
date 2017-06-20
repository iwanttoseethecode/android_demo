package com.example.luoling.android_dome.Graphics2D;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.luoling.android_dome.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BoomActivity extends AppCompatActivity {

    @BindView(R.id.newBoomView)
    BoomView newBoomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boom);
        ButterKnife.bind(this);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                newBoomView.postInvalidate();
            }
        },200,100);
    }


}
