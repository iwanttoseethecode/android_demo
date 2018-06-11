package com.example.luoling.android_dome.PathMeasure;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GrabageView extends View {

    private Paint paint;
    private Path headPath;
    private Path bodyPath;
    private int width;
    private int height;
    private int centerX;
    private int centerY;
    private int delta;
    private ValueAnimator valueAnimator;

    public GrabageView(Context context) {
        this(context,null);
        init();
    }

    public GrabageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        headPath = new Path();
        bodyPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        centerX = w/2;
        centerY = h/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        headPath.reset();
        bodyPath.reset();
        //画盖子
        headPath.moveTo(width * 4/20,height * 6/20);
        headPath.lineTo(width * 16/ 20, height * 6/20);
        headPath.moveTo(width * 9/20, height * 6 /20);
        headPath.lineTo(width * 9/20,height * 5/20);
        headPath.lineTo(width * 11/20,height * 5/20);
        headPath.lineTo(width * 11/20,height * 6/20);
        //画桶
        bodyPath.moveTo(width*5/20,height * 6 /20);
        bodyPath.lineTo(width*7/20,height * 15 /20);
        bodyPath.lineTo(width*13/20,height * 15 /20);
        bodyPath.lineTo(width*15/20,height * 6 /20);
        bodyPath.moveTo(width*8/20,height * 8 /20);
        bodyPath.lineTo(width*8/20,height * 15 /20);
        bodyPath.moveTo(width*12/20,height * 8 /20);
        bodyPath.lineTo(width*12/20,height * 15 /20);

        canvas.drawPath(bodyPath,paint);
        canvas.save();
        canvas.rotate(delta,width * 16 /20,height * 6 /20);
        canvas.drawPath(headPath,paint);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (valueAnimator != null){
                    valueAnimator.cancel();
                    delta = 0;
                }
                startAnim();
                return true;
        }

        return super.onTouchEvent(event);
    }

    private void startAnim(){
        valueAnimator = ValueAnimator.ofInt(0,30,0);
        valueAnimator.setDuration(1600);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                delta = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        valueAnimator.start();
    }
}
