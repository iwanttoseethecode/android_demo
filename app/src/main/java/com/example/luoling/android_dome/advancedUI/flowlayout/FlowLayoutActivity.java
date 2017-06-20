package com.example.luoling.android_dome.advancedUI.flowlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.luoling.android_dome.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FlowLayoutActivity extends AppCompatActivity {

    @BindView(R.id.myFlowlayout)
    FlowLayout myFlowlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);
        ButterKnife.bind(this);

        myFlowlayout.setOnItemClickListener(new FlowLayout.OnItemClickListener() {
            @Override
            public void ItemClick(View v, int index) {
                Toast.makeText(FlowLayoutActivity.this,"第"+index+"个按钮",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
