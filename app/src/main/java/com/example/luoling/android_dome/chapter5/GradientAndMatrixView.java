package com.example.luoling.android_dome.chapter5;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by luoling on 2016/12/27.
 */

public class GradientAndMatrixView extends View {

    private Paint paint;
    private float mRotate;
    private Matrix matrix = new Matrix();
    private Shader mShader;

    public GradientAndMatrixView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mShader = new SweepGradient(160,100,new int[]{Color.RED,Color.GREEN,Color.BLUE},null);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShader(mShader);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                postInvalidate();
            }
        },1000,50);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(300,300);
        canvas.drawColor(Color.WHITE);
        matrix.setRotate(mRotate,160,100);
        mShader.setLocalMatrix(matrix);
        mRotate += 4;
        if (mRotate>=360){
            mRotate = 0;
        }
        canvas.drawCircle(160,100,380,paint);
    }

}
