package com.example.luoling.android_dome.circleprogressbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.luoling.android_dome.R;

import static java.lang.Thread.sleep;

public class CircleProgressBarActivity extends AppCompatActivity {

    private CircleProgressBar myCircleProgressBar;
    private int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_progress_bar);
        myCircleProgressBar = (CircleProgressBar) findViewById(R.id.myCircle);

        myCircleProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 progress = 0;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        progress = 0;
                        while(progress <= 100){
                            progress += 2;
                            myCircleProgressBar.setProgress(progress);
                            try {
                                sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });
    }
}
