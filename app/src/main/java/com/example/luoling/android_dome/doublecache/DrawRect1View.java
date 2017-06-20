package com.example.luoling.android_dome.doublecache;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by luoling on 2016/10/11.
 */
public class DrawRect1View extends View {

    private int firstX,firstY;
    private Path path;
    private Paint paint;

    public DrawRect1View(Context context) {
        this(context,null);
    }

    public DrawRect1View(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawRect1View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(5);
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.reset();
                firstX = x;
                firstY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                if(firstX<x && firstY<y){
                    path.addRect(firstX,firstY,x,y,Path.Direction.CCW);
                }
                if(firstX>x && firstY<y){
                    path.addRect(x,firstY,firstX,y,Path.Direction.CCW);
                }
                if(firstX>x && firstY>y){
                    path.addRect(x,y,firstX,firstY,Path.Direction.CCW);
                }
                if(firstX<x && firstY>y){
                    path.addRect(firstX,y,x,firstY,Path.Direction.CCW);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                invalidate();
                break;
        }
        return true;
    }
}
