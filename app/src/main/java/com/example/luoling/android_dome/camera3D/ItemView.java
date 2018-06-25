package com.example.luoling.android_dome.camera3D;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.luoling.android_dome.R;

public class ItemView extends LinearLayout implements View.OnClickListener{
    public ItemView(Context context) {
        super(context);
        init(context);
    }

    public ItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;

        View.inflate(context, R.layout.camera_item,this);

        toLeft = findViewById(R.id.left_btn);
        toRight = findViewById(R.id.right_btn);
        toUp = findViewById(R.id.roll_up_btn);
        toDown = findViewById(R.id.roll_down_btn);
        roll3DView = findViewById(R.id.three_d_view);
        titleView = findViewById(R.id.title_tv);

        toLeft.setOnClickListener(this);
        toRight.setOnClickListener(this);
        toDown.setOnClickListener(this);
        toUp.setOnClickListener(this);

        bgDrawble1 = (BitmapDrawable) getResources().getDrawable(R.mipmap.img1);
        bgDrawble2 = (BitmapDrawable) getResources().getDrawable(R.mipmap.img2);
        bgDrawble3 = (BitmapDrawable) getResources().getDrawable(R.mipmap.img3);
        bgDrawble4 = (BitmapDrawable) getResources().getDrawable(R.mipmap.img4);
        bgDrawble5 = (BitmapDrawable) getResources().getDrawable(R.mipmap.img5);

        Bitmap bitmap1 = bgDrawble1.getBitmap();
        Bitmap bitmap2 = bgDrawble2.getBitmap();
        Bitmap bitmap3 = bgDrawble3.getBitmap();
        Bitmap bitmap4 = bgDrawble4.getBitmap();
        Bitmap bitmap5 = bgDrawble5.getBitmap();

        roll3DView.addImageBitmap(bitmap1);
        roll3DView.addImageBitmap(bitmap2);
        roll3DView.addImageBitmap(bitmap3);
        roll3DView.addImageBitmap(bitmap4);
        roll3DView.addImageBitmap(bitmap5);

        roll3DView.setRollMode(Roll3DView.RollMode.Whole3D);
    }

    private Context context;
    private Roll3DView roll3DView;
    private Button toLeft;
    private Button toRight;
    private Button toUp;
    private Button toDown;
    private TextView titleView;

    private BitmapDrawable bgDrawble1,bgDrawble2,bgDrawble3,bgDrawble4,bgDrawble5;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_btn:
                roll3DView.setDirection(0);
                roll3DView.toPre();
                break;
            case R.id.right_btn:
                roll3DView.setDirection(0);
                roll3DView.toNext();
                break;
            case R.id.roll_up_btn:
                roll3DView.setDirection(1);
                roll3DView.toPre();
                break;
            case R.id.roll_down_btn:
                roll3DView.setDirection(1);
                roll3DView.toNext();
                break;
        }
    }

    public void setRollMode(Roll3DView.RollMode rollMode){
        roll3DView.setRollMode(rollMode);
    }

    public void setTitleText(String titleText){
        titleView.setText(titleText);
    }

    public void setPartNumber(int num){
        roll3DView.setPartNumber(num);
    }

}
