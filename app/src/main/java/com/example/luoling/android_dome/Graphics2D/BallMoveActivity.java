package com.example.luoling.android_dome.Graphics2D;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.luoling.android_dome.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BallMoveActivity extends AppCompatActivity {

    @BindView(R.id.myBollMoveViewew)
    BollMoveView myBollMoveViewew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball_move);
        ButterKnife.bind(this);
        /*经常需要一些周期性的操作,第一个参数是要操作的方法，第二个参数是要设定延迟(开始执行)的时间，第三个参
        数是周期的设定，每隔多长时间执行该操作。*/
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //在iu线程用 invalidate(),在子线程用postInvalidate();
                myBollMoveViewew.postInvalidate();
            }
        },200,30);

    }
}
