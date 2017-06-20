package com.example.luoling.android_dome.Graphics2D;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.luoling.android_dome.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClipActivity extends AppCompatActivity {

    @BindView(R.id.mClipView)
    ClipView mClipView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                mClipView.setSerialNnumber(1);
                mClipView.invalidate();
                break;
            case R.id.btn2:
                mClipView.setSerialNnumber(2);
                mClipView.invalidate();
                break;
            case R.id.btn3:
                mClipView.setSerialNnumber(3);
                mClipView.invalidate();
                break;
            case R.id.btn4:
                mClipView.setSerialNnumber(4);
                mClipView.invalidate();
                break;
            case R.id.btn5:
                mClipView.setSerialNnumber(5);
                mClipView.invalidate();
                break;
            case R.id.btn6:
                mClipView.setSerialNnumber(6);
                mClipView.invalidate();
                break;
        }
    }
}
