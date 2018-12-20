package com.example.luoling.android_dome.tagetcricle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by luoling on 2016/8/18.
 */
public class BullsEyeView extends View {

    private Paint mPaint;
    private Point mCenter;
    private float mRadius;

    public BullsEyeView(Context context) {
        this(context,null);
    }

    public BullsEyeView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BullsEyeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mCenter = new Point();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width,height;
        //在没有约束条件下确定的内容大小
        int contentWidth = 100;
        int contentHeight = 100;

        width = getMeasurement(widthMeasureSpec,contentWidth);
        height = getMeasurement(heightMeasureSpec,contentHeight);

        //必须使用测量值调用此方法
        setMeasuredDimension(width,height);
    }

    private int getMeasurement(int measureSpec,int contentSize){
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (MeasureSpec.getMode(measureSpec)){
            case MeasureSpec.AT_MOST:
                return Math.min(specSize,contentSize);
            case MeasureSpec.UNSPECIFIED:
                return contentSize;
            case MeasureSpec.EXACTLY:
                return specSize;
            default:
                return 0;
        }
    }

    //测量完后，调用onSizeChanged()收集一些所需要的基本数据来绘制目标圆
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            //如果有变化则复位参数
            mCenter.x = w/2;
            mCenter.y = h/2;
            mRadius = Math.min(mCenter.x,mCenter.y);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.RED);
        canvas.drawCircle(mCenter.x,mCenter.y,mRadius,mPaint);

        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(mCenter.x,mCenter.y,mRadius * 0.8f,mPaint);

        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(mCenter.x,mCenter.y,mRadius * 0.6f,mPaint);

        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(mCenter.x,mCenter.y,mRadius * 0.4f,mPaint);

        mPaint.setColor(Color.RED);
        canvas.drawCircle(mCenter.x,mCenter.y,mRadius * 0.1f,mPaint);
    }
}
