package com.example.luoling.android_dome.doublecache.drawapp;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by luoling on 2016/10/12.
 */
public class CircleDrawer extends ShaperDrawer {

    private float firstX,firstY;
    private float currentX,currentY;

    public CircleDrawer(View view) {
        super(view);
    }

    @Override
    public void draw(Canvas viewCanvas) {
        super.draw(viewCanvas);
        drawShape(viewCanvas,firstX,firstY,currentX,currentY);
    }

    /*画当前的形状*/
    public void drawShape(Canvas canvas,float firstX,float firstY,float currentX,float currentY){
        Paint paint = AttributesTool.getInstance().getPaint();
        //判断手指的方向


        int R= (int) Math.sqrt(Math.hypot(currentX-firstX,currentY-firstY));

        canvas.drawCircle(Math.abs(currentX-firstX)/2,Math.abs(currentY-firstY)/2,R,paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                firstX = x;
                firstY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                currentX = x;
                currentY = y;
                getView().invalidate();
                break;
            case MotionEvent.ACTION_UP:
                //将最终的矩形绘制在缓冲区
                Canvas canvas = BitmapBuffer.getInstance().getCanvas();
                drawShape(canvas,firstX,firstY,currentX,currentY);
                getView().invalidate();
                //保存到撤销栈中
                BitmapBuffer.getInstance().pushBitmap();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void logic() {

    }
}
