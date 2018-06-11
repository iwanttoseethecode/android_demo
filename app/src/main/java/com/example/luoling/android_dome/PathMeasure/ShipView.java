package com.example.luoling.android_dome.PathMeasure;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.luoling.android_dome.R;

public class ShipView extends View {
    private int WAVE_LENGTH = 600;
    private Path mPath;
    private Paint mPaint;
    private Bitmap mBitmap;
    private float faction;
    private int mDeltaX;

    public ShipView(Context context) {
        this(context,null);
    }

    public ShipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPath = new Path();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ship,options);
        int bmpWidth = options.outWidth;
        int bmpHeight = options.outHeight;
        while(bmpWidth/options.inSampleSize > 160 || bmpHeight/options.inSampleSize > 130){
            options.inSampleSize *= 2;
        }
        options.inJustDecodeBounds = false;
        mBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ship,options);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        startAnim();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        int halfWaveLength = WAVE_LENGTH/2;
        mPath.moveTo(-WAVE_LENGTH+mDeltaX, (float) (getHeight()*0.6));
        for (int i = -WAVE_LENGTH+mDeltaX; i < getWidth() + WAVE_LENGTH+mDeltaX; i += WAVE_LENGTH){
            mPath.rQuadTo(halfWaveLength/2,60,halfWaveLength,0);
            mPath.rQuadTo(halfWaveLength/2,-60,halfWaveLength,0);
        }

        PathMeasure pathMeasure = new PathMeasure(mPath,false);
        float length = pathMeasure.getLength();
        Matrix matrix = new Matrix();
        float[] pos = new float[2];
        float[] tan = new float[2];
        boolean posTan = pathMeasure.getPosTan(length * faction,pos,tan);
        if (posTan){
            // 方案一 ：自己计算
            // 将tan值通过反正切函数得到对应的弧度，在转化成对应的角度度数
            /*float degrees = (float) (Math.atan2(tan[1],tan[0])*180f / Math.PI);
            mMatrix.postRotate(degrees, mBitMap.getWidth()/2, mBitMap.getHeight() / 2);
            mMatrix.postTranslate(pos[0]- mBitMap.getWidth() / 2,pos[1] - mBitMap.getHeight());
            canvas.drawBitmap(mBitMap,mMatrix,mPaint);*/

            pathMeasure.getMatrix(length*faction,matrix,PathMeasure.TANGENT_MATRIX_FLAG|PathMeasure.POSITION_MATRIX_FLAG);
            //matrix保存了pos这点，让图片的底边的中心点设置到该点上就需要平移
            matrix.preTranslate(-mBitmap.getWidth()/2,-mBitmap.getHeight());
            canvas.drawBitmap(mBitmap,matrix,mPaint);
        }
        mPath.lineTo(getWidth(),getHeight());
        mPath.lineTo(0,getHeight());
        mPath.close();
        canvas.drawPath(mPath,mPaint);
    }

    private void startAnim(){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,1f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                faction = (float) animation.getAnimatedValue();

                mDeltaX = (int) (WAVE_LENGTH * faction);
                invalidate();
            }
        });
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setDuration(6000);
        valueAnimator.start();
    }
}
