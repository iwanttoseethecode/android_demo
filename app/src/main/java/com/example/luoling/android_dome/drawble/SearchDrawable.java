package com.example.luoling.android_dome.drawble;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.animation.LinearInterpolator;

public class SearchDrawable extends Drawable {

    public static final int STATE_ANIM_NONE = 0;
    public static final int STATE_ANIM_START = 1;
    public static final int STATE_ANIM_STOP = 2;
    protected float animationRatio = -1;
    private Paint mPaint;
    private RectF ovalRect;
    protected ValueAnimator mValueAnimator;

    public int getmState() {
        return mState;
    }

    public int mState = STATE_ANIM_NONE;
    private String mColor = "#4caf50";
    private int mRadius = 0;


    public SearchDrawable() {
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(5);
        Rect rect = getBounds();
        mRadius = Math.min(rect.width(),rect.height())/6;
        mCircleX = rect.width()/2;
        mCircleY = rect.height()/2;

        ovalRect = new RectF();
        ovalRect.left = mCircleX - mRadius;
        ovalRect.right = mCircleX + mRadius;
        ovalRect.top = mCircleY - mRadius;
        ovalRect.bottom = mCircleY + mRadius;
    }

    public void startAnimation(){
        mValueAnimator = ValueAnimator.ofFloat(0,1);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animationRatio = animation.getAnimatedFraction();
                invalidateSelf();//会通过callback回调到ImagView的invalidate()
            }
        });
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setDuration(4000);
        mValueAnimator.start();
        mState = STATE_ANIM_START;
    }

    public void resetAnimation(){
        mValueAnimator.cancel();
        animationRatio = -1;
        mValueAnimator = null;
        mState = STATE_ANIM_NONE;
        invalidateSelf();
    }

    public void stopAnimation(){
        mValueAnimator.cancel();
        animationRatio = 1;
        mState = STATE_ANIM_STOP;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawColor(Color.parseColor(mColor));
        switch(mState){
            case STATE_ANIM_NONE:
                drawNormalView(mPaint,canvas);
                break;
            case STATE_ANIM_START:
                drawRunAnimView(mPaint,canvas);
                break;
            case STATE_ANIM_STOP:
                drawRunAnimView(mPaint,canvas);
                break;
        }
    }

    private int mCircleX,mCircleY;

    //绘制的圆的半径是mRadius,手柄长度是mRadius
    private void drawNormalView(Paint paint,Canvas canvas){

        canvas.save();
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(ovalRect,0,360,false,paint);
        //半径长度在45度角时对应的x方向和y方向的长度
        float cosXLength = (float) (mRadius * Math.cos(0.25 * Math.PI));
        float sinYLength = (float) (mRadius * Math.sin(0.25 * Math.PI));
        canvas.drawLine(mCircleX + cosXLength,mCircleY + sinYLength, mCircleX + 2 * cosXLength,mCircleY + 2 * sinYLength,paint);
    }

    //绘制的圆的半径是mRadius,手柄长度是mRadius
    private void drawRunAnimView(Paint paint,Canvas canvas){
        //半径长度在45度角时对应的x方向和y方向的长度
        float cosXLength = (float) (mRadius * Math.cos(0.25 * Math.PI));
        float sinYLength = (float) (mRadius * Math.sin(0.25 * Math.PI));
        if (animationRatio <= 0.5){
            //绘制圆,sweepAngle为负角度就是逆时针方向绘制
            canvas.drawArc(ovalRect,45,360 * (2 * animationRatio -1),false,paint);
            canvas.drawLine(mCircleX + cosXLength,mCircleY + sinYLength, mCircleX + 2 * cosXLength,mCircleY + 2 * sinYLength,paint);
        }else{
            //计算剩余手柄的起始点
            float startX = (float) (mCircleX + cosXLength + mRadius * ((animationRatio - 0.5) * 2) * Math.cos(0.25 * Math.PI));
            float startY = (float) (mCircleY + sinYLength + mRadius * ((animationRatio - 0.5) * 2) * Math.sin(0.25 * Math.PI));
            canvas.drawLine(startX,startY,mCircleX + 2 * cosXLength,mCircleY + 2 * sinYLength,paint);
        }

        //绘制下面横线,横线的起点就是手柄的终点
        float lineStartX = mCircleX + 2 * cosXLength;
        float lineStartY = mCircleY + 2 * sinYLength;
        float lineEndX = lineStartX - (animationRatio * 4 * mRadius);
        float lineEndY = lineStartY;
        canvas.drawLine(lineStartX,lineStartY,lineEndX,lineEndY,paint);

    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}
