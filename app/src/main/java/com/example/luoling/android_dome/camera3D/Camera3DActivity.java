package com.example.luoling.android_dome.camera3D;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.luoling.android_dome.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Camera3DActivity extends AppCompatActivity {

    @BindView(R.id.item1)
    ItemView item1;
    @BindView(R.id.item2)
    ItemView item2;
    @BindView(R.id.item3)
    ItemView item3;
    @BindView(R.id.item4)
    ItemView item4;
    @BindView(R.id.item5)
    ItemView item5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera3_d);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        item1.setRollMode(Roll3DView.RollMode.Roll2D);
        item1.setTitleText("2d平移");

        item2.setRollMode(Roll3DView.RollMode.Whole3D);
        item2.setTitleText("3d翻转");

        item3.setRollMode(Roll3DView.RollMode.SepartConbine);
        item3.setPartNumber(3);
        item3.setTitleText("开合效果");

        item4.setRollMode(Roll3DView.RollMode.Jalousie);
        item4.setPartNumber(8);
        item4.setTitleText("百叶窗");

        item5.setRollMode(Roll3DView.RollMode.RollInTurn);
        item5.setPartNumber(8);
        item5.setTitleText("轮转效果");

    }
}
