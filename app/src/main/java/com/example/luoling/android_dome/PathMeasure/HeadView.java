package com.example.luoling.android_dome.PathMeasure;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.luoling.android_dome.R;

/**
 * Created by Administrator on 2018/10/29.
 */

public class HeadView extends View {

    private Paint mPaint;
    private int mWidth;
    private int mHeight;

    private BitmapDrawable besselViewBackGroundDrawable;
    private String numberText = "";
    private int numberTextColor;
    private int numberTextSize;
    private String unitText = "";
    private int unitTextColor;
    private int unitTextSize;
    private String describeText = "";
    private int describeTextColor;
    private int describeTextSize;

    public HeadView(Context context) {
        this(context,null);
    }

    public HeadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HeadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    public void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeadView);
        besselViewBackGroundDrawable = (BitmapDrawable) typedArray.getDrawable(R.styleable.HeadView_besselViewBackGround);
        numberText = typedArray.getString(R.styleable.HeadView_numberText);
        numberTextColor = typedArray.getColor(R.styleable.HeadView_numberTextColor, Color.WHITE);
        numberTextSize = (int) typedArray.getDimension(R.styleable.HeadView_numberTextSize,10);
        unitText = typedArray.getString(R.styleable.HeadView_unitText);
        unitTextColor = typedArray.getColor(R.styleable.HeadView_numberTextColor,Color.WHITE);
        unitTextSize = (int) typedArray.getDimension(R.styleable.HeadView_unitTextSize,10);
        describeText = typedArray.getString(R.styleable.HeadView_describeText);
        describeTextColor = typedArray.getColor(R.styleable.HeadView_describeTextColor,Color.WHITE);
        describeTextSize = (int) typedArray.getDimension(R.styleable.HeadView_describeTextSize,10);

        typedArray.recycle();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            setBackgroundColor(getResources().getColor(R.color.white,null));
        }else{
            setBackgroundColor(getResources().getColor(R.color.white));
        }

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int warpContentSize = (int) dp2px(this.getContext(),200);

        int width = getMeasurement(widthMeasureSpec,warpContentSize);
        int height = getMeasurement(heightMeasureSpec,warpContentSize);

        setMeasuredDimension(width,height);
    }

    private int getMeasurement(int measureSpec,int contentSize){
        int measureSize = MeasureSpec.getSize(measureSpec);
        int measureMode = MeasureSpec.getMode(measureSpec);

        int result = 0;

        switch(measureMode){
            case MeasureSpec.EXACTLY:
                result = measureSize;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(measureSize,contentSize);
                break;
            case MeasureSpec.UNSPECIFIED:
                result = contentSize;
                break;
        }
        return result;
    }

    private float dp2px(Context context,int dpValue){
        float size = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpValue,context.getResources().getDisplayMetrics());
        return size;
    }

    private float sp2px(Context context,int spValue){
        float size = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,spValue,context.getResources().getDisplayMetrics());
        return size;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.reset();

        int layerId = canvas.saveLayer(0,0,getWidth(),getHeight(),null,Canvas.ALL_SAVE_FLAG);

        besselViewBackGround(canvas);

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        drawTopText(canvas);

        drawDescribeText(canvas);

        canvas.restoreToCount(layerId);
    }

    private void besselViewBackGround(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        if (besselViewBackGroundDrawable != null && besselViewBackGroundDrawable instanceof BitmapDrawable){
            BitmapShader bitmapShader = new BitmapShader(besselViewBackGroundDrawable.getBitmap(), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mPaint.setShader(bitmapShader);
        }

        Path path = new Path();

        path.moveTo(0,0);
        path.lineTo(mWidth,0);
        path.lineTo(mWidth,mHeight);
        path.moveTo(0,0);
        path.lineTo(0,mHeight);
        path.quadTo(mWidth/2,mHeight * 7 / 8,mWidth,mHeight);
        path.close();

        canvas.drawPath(path,mPaint);

    }

    private void drawTopText(Canvas canvas){
        mPaint.setColor(numberTextColor);
        mPaint.setTextSize(numberTextSize);

        Rect rect1 = getTextRect(numberText);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();

        float BaseLine = mHeight * 1 /3 + ((fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom);

        Rect rect2 = getTextRect(unitText);

        //单位和数字之间的间隔
        int space = unitTextSize /2;

        int numberTextX = (mWidth - (rect1.width()+ space + rect2.width())) / 2;

        //画数字
        canvas.drawText(numberText,numberTextX,BaseLine,mPaint);

        //画单位
        mPaint.setTextSize(unitTextSize);
        mPaint.setColor(unitTextColor);

        int unitTextX = numberTextX + rect1.width() + space;
        canvas.drawText(unitText,unitTextX,BaseLine,mPaint);
    }

    private void drawDescribeText(Canvas canvas){
        mPaint.setColor(describeTextColor);
        mPaint.setTextSize(describeTextSize);

        Rect rect = getTextRect(describeText);

        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();

        float baseLine = mHeight * 2 / 3 - ((fontMetrics.bottom - fontMetrics.top) - fontMetrics.bottom);

        canvas.drawText(describeText,(mWidth - rect.width())/2,baseLine,mPaint);
    }

    private Rect getTextRect(String text){
        Rect rect = new Rect();
        mPaint.getTextBounds(text,0,text.length(),rect);
        return rect;
    }

}
