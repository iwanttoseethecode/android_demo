package com.example.luoling.android_dome.xformode;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.luoling.android_dome.R;

public class CircleWaveView extends View {
    public CircleWaveView(Context context) {
        super(context);
        init();
    }

    public CircleWaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private Paint paint;
    private int mItemWaveLength = 0;
    private int dx = 0;
    private Bitmap BmpSrc,BmpDst;

    private void init(){
        paint = new Paint();
        paint.setAntiAlias(true);
        BmpDst = BitmapFactory.decodeResource(getResources(), R.mipmap.wave_bg);
        BmpSrc = BitmapFactory.decodeResource(getResources(),R.mipmap.circle_shape);
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
            measureWidth = BmpSrc.getWidth();
        }

        if (heightMode == MeasureSpec.EXACTLY){
            measureHeight = height;
        }else if(heightMode == MeasureSpec.AT_MOST){
            measureHeight = BmpSrc.getHeight();
        }

        setMeasuredDimension(measureWidth,measureHeight);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mItemWaveLength = BmpDst.getWidth();
        this.post(new Runnable() {
            @Override
            public void run() {
                startAnim();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(BmpSrc,0,0,paint);
        int layerId = canvas.saveLayer(0,0,getWidth(),getHeight(),null,Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(BmpDst,new Rect(dx,0,dx+ BmpSrc.getWidth(),BmpSrc.getHeight()),new Rect(0,0,BmpSrc.getWidth(),BmpSrc.getHeight()),paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(BmpSrc,0,0,paint);
        paint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }

    private void startAnim(){
        ValueAnimator animator = ValueAnimator.ofInt(0,mItemWaveLength);
        animator.setDuration(4000);
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
}
