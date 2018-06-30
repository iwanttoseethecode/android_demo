package com.example.luoling.android_dome.RecyclerViewDemo.SwipeCard;

import android.content.Context;
import android.util.TypedValue;

public class CardConfig {
    //屏幕上最多同时显示几个Item
    public static int MAX_SHOW_COUNT;

    //每一级放缩0.05放，每一级移动10dp
    public static float SCALE_GAP;
    public static int TRANS_Y_GAP;

    public static void initConfig(Context context){
        MAX_SHOW_COUNT = 4;
        SCALE_GAP = 0.05f;
        TRANS_Y_GAP = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10,context.getResources().getDisplayMetrics());
    }
}
