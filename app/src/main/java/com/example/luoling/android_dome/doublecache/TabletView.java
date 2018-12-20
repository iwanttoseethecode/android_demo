package com.example.luoling.android_dome.doublecache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by luoling on 2016/10/10.
 * 自定义控件的算缓存技术，定义一个bitmap，在bitmap上绘制，然后再把bitmap绘制在view上
 *
 */
public class TabletView extends View {
    /**上一个点的坐标*/
    private int preX,preY;
    /**当前点的坐标*/
    private int currentX,currentY;
    /**Bitmap 缓冲区*/
    private Bitmap bitmapButter;
    private Canvas bitmapCanvas;
    private Paint paint;

    public TabletView(Context context) {
        this(context,null);
    }

    public TabletView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TabletView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(13);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(bitmapButter==null){
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            bitmapButter = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
            bitmapCanvas = new Canvas(bitmapButter);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmapButter,0,0,null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                currentX = x;
                currentY = y;
                bitmapCanvas.drawLine(preX,preY,currentX,currentY,paint);
                invalidate();
                preX = currentX;
                preY = currentY;
                break;
            case MotionEvent.ACTION_UP:
                invalidate();
                break;
        }
        return true;
    }
}
