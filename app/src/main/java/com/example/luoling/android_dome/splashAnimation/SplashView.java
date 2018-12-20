package com.example.luoling.android_dome.splashAnimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.example.luoling.android_dome.R;

public class SplashView extends View{
    public SplashView(Context context) {
        super(context);
        init(context);
    }

    public SplashView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SplashView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public SplashView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    //大圆的半径
    private float mRotationRadius = 90;

    //每一个小圆的半径
    private float mCircleRadius = 18;

    //小圆的颜色列表，在initialize方法里面初始化
    private int[] mCircleColors;

    /**
     * 大圆和小圆的旋转时间
     */
    private long mRotationDuration = 1200;

    /**
     * 整体的背景颜色
     */
    private int mSplashBgColor = Color.WHITE;

    /**
     * 空心圆的初始半径
     */
    private float mHoleRadius = 0f;

    /**
     * 当前大圆的旋转角度
     */
    private float mCurrentRotationAngle = 0f;

    /**
     * 当前大圆的半径
     */
    private float mCurrentRotationRadius = mRotationRadius;

    /**
     * 绘制圆的画笔
     */
    private Paint mPaint = new Paint();

    /**
     * 绘制背景的画笔
     */
    private Paint mPaintBackground = new Paint();

    /**
     * 屏幕正中心点坐标
     */
    private float mCenterX;
    private float mCenterY;

    /**
     * 屏幕对角线一半
     */
    private double mDiagonalDist;

    private void init(Context context){
        mCircleColors = context.getResources().getIntArray(R.array.splash_circle_colors);
        mPaint.setAntiAlias(true);
        mPaintBackground.setAntiAlias(true);
        mPaintBackground.setStyle(Paint.Style.STROKE);
        mPaintBackground.setColor(mSplashBgColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCenterX = w/2f;
        mCenterY = h/2f;
        mDiagonalDist = Math.sqrt(w*w + h*h) /2f;
    }

    public void splashDisappear(){
        if (mState != null && mState instanceof RotateState){
            RotateState rotateState = (RotateState) mState;
            rotateState.cancel();
            post(new Runnable() {
                @Override
                public void run() {
                    mState = new MerginState();
                }
            });
        }
    }

    private SplashState mState = null;
    private abstract class SplashState{
        public abstract void drawState(Canvas canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mState == null){
            mState = new RotateState();
        }
        mState.drawState(canvas);
    }

    private void drawBackground(Canvas canvas){
        if (mHoleRadius>0f){
            float strokeWidth = (float) (mDiagonalDist - mHoleRadius);
            mPaintBackground.setStrokeWidth(strokeWidth);
            float radius = mHoleRadius + strokeWidth/2;
            canvas.drawCircle(mCenterX,mCenterY,radius,mPaintBackground);
        }else {
            canvas.drawColor(mSplashBgColor);
        }
    }

    private void drawCircle(Canvas canvas){
        float rotationAngle = (float) (2 * Math.PI / mCircleColors.length);
        for (int i = 0; i < mCircleColors.length; i++){
            double angle = i * rotationAngle + mCurrentRotationAngle;
            float cx = (float) (mCurrentRotationRadius*Math.cos(angle) + mCenterX);
            float cy = (float) (mCurrentRotationRadius*Math.sin(angle) + mCenterY);
            mPaint.setColor(mCircleColors[i]);
            canvas.drawCircle(cx,cy,mCircleRadius,mPaint);
        }
    }

    private class RotateState extends SplashState{

        private ValueAnimator valueAnimator;

        public RotateState(){
            valueAnimator = ValueAnimator.ofFloat(0f, (float)(2*Math.PI));
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurrentRotationAngle = (float) valueAnimator.getAnimatedValue();
                    invalidate();
                }
            });
            valueAnimator.setDuration(2000);
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.start();
        }

        public void cancel(){
            valueAnimator.cancel();
        }

        @Override
        public void drawState(Canvas canvas) {
            //画背景
            drawBackground(canvas);
            //绘制小圆
            drawCircle(canvas);
        }
    }

    private class MerginState extends SplashState{
        private ValueAnimator valueAnimator;
        public MerginState(){
            valueAnimator = ValueAnimator.ofFloat(0,mRotationRadius);
            valueAnimator.setDuration(1500);
            valueAnimator.setInterpolator(new OvershootInterpolator(10f));
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurrentRotationRadius = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mState = new ExpandState();
                }
            });
            valueAnimator.reverse();
            valueAnimator.start();
        }
        @Override
        public void drawState(Canvas canvas) {
            drawBackground(canvas);
            drawCircle(canvas);
        }
    }

    private class ExpandState extends SplashState{

        ValueAnimator valueAnimator;

        private ExpandState(){
            valueAnimator = ValueAnimator.ofFloat(mCircleRadius, (float) mDiagonalDist);
            valueAnimator.setDuration(2000);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mHoleRadius = (float) valueAnimator.getAnimatedValue();
                    invalidate();
                }
            });
            valueAnimator.start();
        }

        @Override
        public void drawState(Canvas canvas) {
            drawBackground(canvas);
        }
    }
}
