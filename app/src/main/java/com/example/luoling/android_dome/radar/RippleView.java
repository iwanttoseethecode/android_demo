package com.example.luoling.android_dome.radar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;

public class RippleView extends android.support.v7.widget.AppCompatButton {

    private int mX,mY;
    private int DEFAULT_RADIUS = 50;
    private int mCurRadius = 0;
    private int maxRadius = 0;
    private RadialGradient mRadialGradient;
    private Paint mPaint;
    private ObjectAnimator mAnimator;


    public RippleView(Context context) {
        super(context);
        init();
    }

    public RippleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_HARDWARE,null);
        mPaint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        maxRadius = Math.max(w,h);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mX != event.getX() || mY != event.getY()){
            mX = (int) event.getX();
            mY = (int) event.getY();
            setRadius(DEFAULT_RADIUS);
        }

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                if (mAnimator != null && mAnimator.isRunning()){
                    mAnimator.cancel();
                }
                if (mAnimator == null){
                    mAnimator = ObjectAnimator.ofInt(this,"radius",DEFAULT_RADIUS,maxRadius);
                }
                mAnimator.setInterpolator(new AccelerateInterpolator());
                mAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        setRadius(0);
                    }
                });
                mAnimator.start();
                break;
        }

        return super.onTouchEvent(event);
    }

    private void setRadius(int radius) {
        mCurRadius = radius;
        if (mCurRadius > 0){
            mRadialGradient = new RadialGradient(mX,mY,mCurRadius,0x00ffffff,0xff58faac, Shader.TileMode.CLAMP);
            mPaint.setShader(mRadialGradient);
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mX,mY,mCurRadius,mPaint);
    }
}
