package com.example.luoling.android_dome.chapter5;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by luoling on 2016/12/26.
 */

public class SweepGradientView extends View {

    private TYPE type = TYPE.TYPE1;

    public SweepGradientView(Context context, AttributeSet attrs) {
        super(context, attrs);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                type = (type == TYPE.TYPE2 ? TYPE.TYPE1 : TYPE.TYPE2);
                postInvalidate();
            }
        },5000,5000);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        SweepGradient sg = type == TYPE.TYPE1?getSweepGradient1():getSweepGradient2();

        paint.setShader(sg);
        canvas.drawOval(new RectF(300,300,900,900),paint);
    }

    public SweepGradient getSweepGradient1(){
        return new SweepGradient(600,600, Color.GREEN,Color.YELLOW);
    }

    public SweepGradient getSweepGradient2(){
        return new SweepGradient(600,600, new int[]{Color.RED,Color.GREEN,Color.BLUE},null);
    }

    enum TYPE{
        TYPE1,TYPE2;
    }
}
