package com.example.luoling.android_dome.newAnimation;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by luoling on 2016/9/1.
 */
public class MyEvalutor implements TypeEvaluator<PointF> {
    @Override
    public PointF evaluate(float v, PointF pointF, PointF t1) {//v是Interpolator运算得到的值
        PointF pointf = new PointF();
        //x方向移动函数，线性轨迹，匀速移动
        pointf.x = 400*v;
        //y方向移动函数，匀加速移动，总距离是800 y=0.5*10*v*v;
        pointf.y = 1600*0.5f*v*v;
        return pointf;
    }
}
