package com.example.luoling.android_dome.doublecache;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.luoling.android_dome.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabletActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.btn1)
    RadioButton btn1;
    @BindView(R.id.btn2)
    RadioButton btn2;
    @BindView(R.id.btn3)
    RadioButton btn3;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.tabletView)
    TabletView tabletView;
    @BindView(R.id.tablet2View)
    Tablet2View tablet2View;
    @BindView(R.id.tablet3View)
    Tablet3View tablet3View;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablet);
        ButterKnife.bind(this);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.btn1:
                tabletView.setVisibility(View.VISIBLE);
                tablet2View.setVisibility(View.GONE);
                tablet3View.setVisibility(View.GONE);
                break;
            case R.id.btn2:
                tabletView.setVisibility(View.GONE);
                tablet2View.setVisibility(View.VISIBLE);
                tablet3View.setVisibility(View.GONE);
                break;
            case R.id.btn3:
                tabletView.setVisibility(View.GONE);
                tablet2View.setVisibility(View.GONE);
                tablet3View.setVisibility(View.VISIBLE);
                break;
        }
    }
}
