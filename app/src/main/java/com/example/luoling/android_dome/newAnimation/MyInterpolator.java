package com.example.luoling.android_dome.newAnimation;

import android.animation.TimeInterpolator;

/**
 * Created by luoling on 2016/9/1.
 */
public class MyInterpolator implements TimeInterpolator{

    /*
    * 通过构造函数传递周期数
    * */
    private float mCycles;
    public MyInterpolator(float cycles){
        mCycles = cycles;
    }

    @Override
    public float getInterpolation(float v) { //v是运动起始到末尾的百分比
        return (float) (Math.cos(2*mCycles*Math.PI*v));
    }
}
