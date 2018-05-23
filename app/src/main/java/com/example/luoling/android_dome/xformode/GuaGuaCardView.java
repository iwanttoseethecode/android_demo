package com.example.luoling.android_dome.xformode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.luoling.android_dome.R;

import java.util.Random;

public class GuaGuaCardView extends View {
    public GuaGuaCardView(Context context) {
        super(context);
        init();
    }

    public GuaGuaCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private Paint mBitPaint;
    private Bitmap BmpDst,BmpSrc;
    private Path mPath;
    private float mPreX,mPreY;
    private static final String[] PRIZE = {
            "无形之刃最为致命",
            "带你装逼带你飞",
            "带你走近垃圾堆",
            "大王叫我来巡山",
            "逗你玩呢"
    };

    private void init(){
        setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        mBitPaint = new Paint();
        mBitPaint.setColor(Color.RED);
        mBitPaint.setStyle(Paint.Style.STROKE);
        mBitPaint.setStrokeWidth(45);
        mBitPaint.setStrokeCap(Paint.Cap.ROUND);
        BmpSrc = BitmapFactory.decodeResource(getResources(), R.mipmap.guaguaka,null);
        BmpDst = Bitmap.createBitmap(BmpSrc.getWidth(),BmpSrc.getHeight(), Bitmap.Config.ARGB_8888);
        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Bitmap bmpBg = Bitmap.createBitmap(BmpDst.getWidth(),BmpDst.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpBg);

        String text = PRIZE[getRandom()];
        Paint paint = new Paint();
        paint.setTextSize(34);
        Rect rect = new Rect();
        paint.getTextBounds(text,0,text.length(),rect);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        int x = (getWidth() - rect.width())/2;
        int center = getHeight()/2;
        int y = (int) ((fontMetrics.bottom-fontMetrics.top)/2 - fontMetrics.bottom + center);
        c.drawText(text,x,y,paint);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN){
            this.setBackground(new BitmapDrawable(getResources(),bmpBg));
        }else {
            this.setBackground(new BitmapDrawable(bmpBg));
        }
    }

    private int getRandom(){
        Random r = new Random();
        return r.nextInt(PRIZE.length);
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
    protected void onDraw(Canvas canvas) {
        int layerId = canvas.saveLayer(0,0,getWidth(),getHeight(),null,Canvas.ALL_SAVE_FLAG);

        Canvas c = new Canvas(BmpDst);
        c.drawPath(mPath,mBitPaint);

        canvas.drawBitmap(BmpDst,0,0,mBitPaint);
        mBitPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawBitmap(BmpSrc,0,0,mBitPaint);
        mBitPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPreX = event.getX();
                mPreY = event.getY();
                mPath.moveTo(mPreX,mPreY);
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = (mPreX + event.getX())/2;
                float endY = (mPreY + event.getY())/2;
                mPath.quadTo(mPreX,mPreY,endX,endY);
                mPreX = event.getX();
                mPreY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        postInvalidate();
        return true;
    }
}
