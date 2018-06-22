package com.example.luoling.android_dome.ButterKnife;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luoling.android_dome.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/*
* ButterKnife 是一款好用的注解框架，可以将你从耗费时间的控件初始化种解脱出来
*
* 在modul 的 build.gradle中添加compile 'com.jakewharton:butterknife:8.4.0'
*
* 添加 ButterKnife Zelezny插件，setting—>plugin 搜索 android butterknife zelezny，然后添加就行了
*
* 其他绑定方式见 http://blog.csdn.net/i374711088/article/details/49102003
* */


public class ButterKnifeActivity extends FragmentActivity implements View.OnLongClickListener{

    @BindView(R.id.backBtn)
    Button backBtn;
    @BindView(R.id.titleTxtView)
    TextView titleTxtView;
    @BindView(R.id.dwTxtView)
    TextView dwTxtView;
    @BindView(R.id.new_modfiyEdit)
    ImageView newModfiyEdit;
    @BindView(R.id.lxrTxtView)
    TextView lxrTxtView;
    @BindView(R.id.telTxtView)
    TextView telTxtView;
    @BindView(R.id.addressTxtView)
    TextView addressTxtView;
    @BindView(R.id.dwbmTxtView)
    TextView dwbmTxtView;
    @BindView(R.id.dwTypeTxtView)
    TextView dwTypeTxtView;
    @BindView(R.id.areaTxtView)
    TextView areaTxtView;
    @BindView(R.id.swdjhTxtView)
    TextView swdjhTxtView;
    @BindView(R.id.emailTxtView)
    TextView emailTxtView;
    @BindView(R.id.ybTxtView)
    TextView ybTxtView;
    @BindView(R.id.yhTxtView)
    TextView yhTxtView;
    @BindView(R.id.zhTxtView)
    TextView zhTxtView;
    @BindView(R.id.moreLayout)
    LinearLayout moreLayout;
    @BindView(R.id.showImgView)
    ImageView showImgView;
    @BindView(R.id.showLayout)
    LinearLayout showLayout;
    @BindView(R.id.myFrameLayout)
    FrameLayout myFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butter_knife);
        ButterKnife.bind(this);
        init();
    }

    public void init(){

        String iwtd = "1111111";
        String intd = "1111111";
        String ihtd = "1111111";

        titleTxtView.setText("ButterKnife");
        dwTxtView.setText("iwtd  "+iwtd.length());
        dwbmTxtView.setText("android studio 的确比eclipse好太多了");
        lxrTxtView.setText("intd  "+intd.length());
        telTxtView.setText("ihtd  "+ihtd.length());

        dwTxtView.setOnLongClickListener(this);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ButterKnifeFragment butterKnifeFragment = ButterKnifeFragment.newInstance();
        fragmentTransaction.replace(R.id.myFrameLayout,butterKnifeFragment);
        fragmentTransaction.commit();
    }

    @OnClick({R.id.backBtn, R.id.moreLayout, R.id.showLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                finish();
                break;
            case R.id.moreLayout:
                Toast.makeText(ButterKnifeActivity.this, "呵呵", Toast.LENGTH_SHORT).show();
                break;
            case R.id.showLayout:
                Toast.makeText(ButterKnifeActivity.this, "哈哈", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        dwTxtView.setText("哈哈我变了");
        return false;
    }
}
