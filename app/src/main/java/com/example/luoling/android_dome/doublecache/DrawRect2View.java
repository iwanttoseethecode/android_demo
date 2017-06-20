package com.example.luoling.android_dome.doublecache;

import android.content.Context;
import android.graphics.Bitmap;
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
public class DrawRect2View extends View {

    private int firstX,firstY;
    private Paint paint;
    private Path path;
    private Bitmap myBitmap;
    private Canvas myCanvas;
    public DrawRect2View(Context context) {
        this(context,null);
    }

    public DrawRect2View(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawRect2View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        path = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(myBitmap==null){
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            myBitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
            myCanvas = new Canvas(myBitmap);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path,paint);
        canvas.drawBitmap(myBitmap,0,0,null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
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
                myCanvas.drawPath(path,paint);
                invalidate();
                break;
        }
        return true;
    }
}
