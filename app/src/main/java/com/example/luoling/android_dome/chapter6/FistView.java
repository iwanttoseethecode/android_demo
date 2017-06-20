package com.example.luoling.android_dome.chapter6;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.luoling.android_dome.R;

/**
 * Created by luoling on 2017/1/22.
 */

public class FistView extends View {

    private static final String TEXT = "无形装逼最为致命";
    private String text;
    private Paint paint;

    public FistView(Context context) {
        super(context);
    }

    public FistView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(100);
        paint.setColor(Color.RED);

        //获取属性值
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FistView);
        text = a.getString(R.styleable.FistView_text);
        a.recycle();
    }

    public FistView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Rect rect = getTextRect();
        int textWidth = rect.width();
        int textHeight = rect.height();
        int width = measureWidth(widthMeasureSpec,textWidth);
        int height = measureHeight(heightMeasureSpec,textHeight);
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect textRect = this.getTextRect();
        //将文字放在正中间
        int viewWidth = getMeasuredWidth();
        int viewHeight = getMeasuredHeight();
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        int x = (viewWidth - textRect.width())/2;
        int y = (int) (viewHeight/2 + (fontMetrics.descent-fontMetrics.ascent)/2-fontMetrics.descent);
        canvas.drawText(text,x,y,paint);
    }

    /**
     *
     * 获取文字所占的尺寸
     * @return
     */
    private Rect getTextRect(){
        //根据Paint 设置的绘制参数计算文字所占的宽度
        Rect rect = new Rect();
        //文字所占的区域大小保存在rect中
        paint.getTextBounds(text,0,text.length(),rect);
        return rect;
    }

    private int measureWidth(int widthMeasureSpec,int textWidth){
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int width = 0;
        if (mode == MeasureSpec.EXACTLY){
            width = size;
        }else if(mode == MeasureSpec.AT_MOST){
            width = textWidth;
        }
        return width;
    }

    private int measureHeight(int heightMeasureSpec,int textHeight){
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int width = 0;
        if (mode == MeasureSpec.EXACTLY){
            width = size;
        }else if(mode == MeasureSpec.AT_MOST){
            width = textHeight;
        }
        return width;
    }

}
