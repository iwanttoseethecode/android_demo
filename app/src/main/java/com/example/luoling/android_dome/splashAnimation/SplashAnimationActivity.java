package com.example.luoling.android_dome.splashAnimation;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.luoling.android_dome.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashAnimationActivity extends AppCompatActivity {

    @BindView(R.id.contentView)
    ContentView contentView;
    @BindView(R.id.splashView)
    SplashView splashView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_animation);
        ButterKnife.bind(this);

        loadData();
    }

    Handler handler = new Handler();

    private void loadData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                splashView.splashDisappear();
            }
        }, 5000);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
