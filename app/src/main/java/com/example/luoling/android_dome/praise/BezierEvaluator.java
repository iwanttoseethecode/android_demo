package com.example.luoling.android_dome.praise;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

public class BezierEvaluator implements TypeEvaluator<PointF> {

    private PointF pointF1;
    private PointF pointF2;

    public BezierEvaluator(PointF pointF1,PointF pointF2){
        this.pointF1 = pointF1;
        this.pointF2 = pointF2;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        //t百分比：0~1
        // b(t)=p0*(1-t)*(1-t)*(1-t)+3*p1*t*(1-t)*(1-t)+3*p2*t*t*(1-t)+p3*t*t*t
        PointF point = new PointF();
        point.x = startValue.x*(1-fraction)*(1-fraction)*(1-fraction)
                +3*pointF1.x*fraction*(1-fraction)*(1-fraction)
                +3*pointF2.x*fraction*fraction*(1-fraction)
                +endValue.x*fraction*fraction*fraction;
        point.y = startValue.y*(1-fraction)*(1-fraction)*(1-fraction)
                +3*pointF1.y*fraction*(1-fraction)*(1-fraction)
                +3*pointF2.y*fraction*fraction*(1-fraction)
                +endValue.y*fraction*fraction*fraction;
        return point;
    }
}
