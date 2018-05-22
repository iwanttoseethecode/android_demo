package com.example.luoling.android_dome.xformode;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.luoling.android_dome.R;

public class HeartMapView extends View {

    private Paint mPaint;
    private int mItemWaveLength = 0;
    private int dx = 0;

    private Bitmap BmpSrc,BmpDst;

    public HeartMapView(Context context) {
        super(context);
        init();
    }

    public HeartMapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        BmpDst = BitmapFactory.decodeResource(getResources(), R.mipmap.heartmap,null);
        BmpSrc = Bitmap.createBitmap(BmpDst.getWidth(),BmpDst.getHeight(),Bitmap.Config.ARGB_8888);
        mItemWaveLength = BmpDst.getWidth();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int measureWidth = 0;
        int measureHeight = 0;

        if (widthMode == MeasureSpec.EXACTLY){
            measureWidth = width;
        }else if(widthMode == MeasureSpec.AT_MOST){
            measureWidth = BmpDst.getWidth();
        }

        if (heightMode == MeasureSpec.EXACTLY){
            measureHeight = height;
        }else if(heightMode == MeasureSpec.AT_MOST){
            measureHeight = BmpDst.getHeight();
        }

        setMeasuredDimension(measureWidth,measureHeight);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.post(new Runnable() {
            @Override
            public void run() {
                startAnim();
            }
        });
    }

    public void startAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0, mItemWaveLength);
        animator.setDuration(6000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //利用 PorterDuff.Mode.CLEAR 将BmpSrc的右半部分设为透明
        Canvas mCanvas = new Canvas(BmpSrc);
        mCanvas.drawColor(Color.RED, PorterDuff.Mode.CLEAR);
        mCanvas.drawRect(BmpSrc.getWidth() - dx,0,BmpSrc.getWidth(),BmpSrc.getHeight(),mPaint);

        int layerId = canvas.saveLayer(0,0,getWidth(),getHeight(),null,Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(BmpDst,0,0,mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(BmpSrc,0,0,mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);

    }
}
