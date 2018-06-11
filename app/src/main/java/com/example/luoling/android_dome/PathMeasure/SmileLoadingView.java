package com.example.luoling.android_dome.PathMeasure;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class SmileLoadingView extends View {

    /**
     * 左眼距离左边的距离（控件宽度＊EYE_PERCENT_W）
     */
    private float EYE_PERCENT_W = 0.35f;

    /**
     *眼睛距离top的距离（控件的高度＊EYE_PERCENT_H）
     */
    private float EYE_PERCENT_H = 0.38f;

    /**
     * 嘴巴左边跟右边距离top的距离（控件的高度＊MOUCH_PERCENT_H）
     */
    private float MOUTH_PERCENT_H = 0.55f;

    /**
     * 嘴巴中间距离top的距离（控件的高度＊MOUTH_PERCENT_H2）
     */
    private float MOUTH_PERCENT_H2 = 0.7f;

    /**
     * 嘴巴左边距离边缘的位置（控件宽度＊MOUCH_PERCENT_W）
     */
    private float MOUTH_PERCENT_W = 0.23f;

    /**
     * 眼睛跟嘴巴摆动的区间范围
     */
    private float QUIVER_AREA = 0.10f;

    private Paint backgroundPaint;
    private Paint reachedPaint;
    private Paint unreachedPaint;
    private Path reachedPath;
    private Path unreachedPath;
    private Path mouthPath = new Path();
    private float progress = 0f;
    private float lineWidth = dp2px(2);

    private float mRadius;

    private float mMouthH = MOUTH_PERCENT_H;

    private float mMouthH2 = MOUTH_PERCENT_H2;

    private float mEyesH = EYE_PERCENT_H;

    private ObjectAnimator quiverAnimator;
    private ValueAnimator valueAnimator;
    private AnimatorSet animatorSet;

    public SmileLoadingView(Context context) {
        this(context,null);
    }

    public SmileLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private float dp2px(int i) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,i,getResources().getDisplayMetrics());
    }

    private void initView() {
        reachedPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        reachedPaint.setStyle(Paint.Style.STROKE);
        reachedPaint.setStrokeWidth(lineWidth);
        reachedPaint.setColor(Color.WHITE);
        reachedPaint.setStrokeCap(Paint.Cap.ROUND);
        reachedPaint.setStrokeJoin(Paint.Join.ROUND);

        unreachedPaint = new Paint(reachedPaint);
        unreachedPaint.setColor(Color.GRAY);

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setColor(Color.argb(230,230,230,230));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mRadius = getWidth() / 14;
        if (unreachedPath == null){
            unreachedPath = new Path();
        }
        unreachedPath.addRoundRect(new RectF(lineWidth,lineWidth,w-lineWidth,h-lineWidth),w/6,w/6,Path.Direction.CW);
        if (reachedPath == null){
            reachedPath = new Path();
        }
        reachedPath.addRoundRect(new RectF(lineWidth,lineWidth,w-lineWidth,h-lineWidth),w/6,w/6,Path.Direction.CW);
        initAnim();
    }

    private void initAnim(){
        quiverAnimator = ObjectAnimator.ofFloat(this,"progress",0f,1f);
        quiverAnimator.setDuration(3000);
        quiverAnimator.setRepeatCount(ValueAnimator.INFINITE);
        quiverAnimator.setRepeatMode(ValueAnimator.REVERSE);

        valueAnimator = ValueAnimator.ofFloat(0f,1f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float offset = QUIVER_AREA * animation.getAnimatedFraction();
                mMouthH = MOUTH_PERCENT_H + offset;
                mMouthH2 = MOUTH_PERCENT_H2 + offset;
                mEyesH = EYE_PERCENT_H + offset;
                invalidate();
            }
        });
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(quiverAnimator,valueAnimator);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(unreachedPath,backgroundPaint);
        canvas.save();
        drawFace(canvas);
        drawReachedRect(canvas);
        canvas.restore();
    }

    private void drawReachedRect(Canvas canvas) {
        unreachedPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(unreachedPath,unreachedPaint);
        PathMeasure pathMeasure = new PathMeasure(reachedPath,false);
        float length = pathMeasure.getLength();
        float currLength = length * progress;
        Path path = new Path();
        path.moveTo(0,0);
        pathMeasure.getSegment(0,currLength,path,true);
        canvas.drawPath(path,reachedPaint);
    }

    private void drawFace(Canvas canvas) {
        unreachedPaint.setStyle(Paint.Style.FILL);
        //画左边眼睛
        canvas.drawCircle(getWidth() * EYE_PERCENT_W,getHeight() * mEyesH - mRadius,mRadius,unreachedPaint);
        //画右边眼睛
        canvas.drawCircle(getWidth() * (1-EYE_PERCENT_W),getHeight()*mEyesH - mRadius,mRadius,unreachedPaint);

        mouthPath.reset();
        mouthPath.moveTo(getWidth() * MOUTH_PERCENT_W,getHeight() * mMouthH);
        mouthPath.quadTo(getWidth()/2,getHeight()*mMouthH2,getWidth() * (1 - MOUTH_PERCENT_W),getHeight() * mMouthH);
        unreachedPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(mouthPath,unreachedPaint);
    }

    private void setProgress(float value){
        progress = value;
    }

    private void startAnim(){
        animatorSet.start();
    }

    private void stopAnim(){
        animatorSet.end();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (animatorSet != null && animatorSet.isRunning()){
                    stopAnim();
                    return true;
                }else if (animatorSet == null){
                    initAnim();
                }
                startAnim();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }
}
