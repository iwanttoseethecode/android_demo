package com.example.luoling.android_dome.PathMeasure;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class LoadingView extends View {

    private Paint mPaint;
    private Path mPath;
    private PathMeasure pathMeasure;
    private float animatorValue;
    private Path dstPath;
    private float mLength;

    public LoadingView(Context context) {
        this(context,null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.argb(210,210,210,210));
        mPath = new Path();
        mPath.addCircle(getWidth()/2,getHeight()/2,100,Path.Direction.CW);
        pathMeasure = new PathMeasure(mPath,true);
        mLength = pathMeasure.getLength();
        dstPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        init();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f,1f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatorValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        dstPath.reset();
//        dstPath.moveTo(0,0);
        float stop = mLength * animatorValue;
        float start = (float) (stop - ((0.5 - Math.abs(animatorValue - 0.5)) * mLength));

        pathMeasure.getSegment(start,stop,dstPath,true);
        canvas.drawPath(dstPath,mPaint);
    }
}
