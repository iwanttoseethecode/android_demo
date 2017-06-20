package com.example.luoling.android_dome.doublecache;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.luoling.android_dome.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DrawRectActivity extends AppCompatActivity {

    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.layout1)
    LinearLayout layout1;
    @BindView(R.id.drawRect1View)
    DrawRect1View drawRect1View;
    @BindView(R.id.drawRect2View)
    DrawRect2View drawRect2View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_rect);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn1, R.id.btn2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                drawRect1View.setVisibility(View.VISIBLE);
                drawRect2View.setVisibility(View.GONE);
                break;
            case R.id.btn2:
                drawRect1View.setVisibility(View.GONE);
                drawRect2View.setVisibility(View.VISIBLE);
                break;
        }
    }
}
