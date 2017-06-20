package com.example.luoling.android_dome.advancedUI.waterfallLayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.luoling.android_dome.R;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WaterfallActivity extends AppCompatActivity {

    @BindView(R.id.myBtn)
    Button myBtn;
    @BindView(R.id.myWaterfallLayout)
    WaterfallLayout myWaterfallLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waterfall);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.myBtn)
    public void onClick() {
        addImageView();

    }

    private void addImageView(){
        ImageView imgView = new ImageView(this);
        WaterfallLayout.WaterfallLayoutParams wp = new WaterfallLayout.WaterfallLayoutParams(WaterfallLayout.WaterfallLayoutParams.MATCH_PARENT, WaterfallLayout.WaterfallLayoutParams.MATCH_PARENT);
        Random random = new Random();
        int num = random.nextInt(5);

        if (num == 0){
            imgView.setImageResource(R.mipmap.p1);
        }else if (num == 1){
            imgView.setImageResource(R.mipmap.p2);
        }else if (num ==2){
            imgView.setImageResource(R.mipmap.p3);
        }else if (num ==3){
            imgView.setImageResource(R.mipmap.p4);
        }else if (num ==4){
            imgView.setImageResource(R.mipmap.p5);
        }

        imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        myWaterfallLayout.addView(imgView,wp);
    }
}
