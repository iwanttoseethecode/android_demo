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
 * Created by luoling on 2016/10/10.
 */
public class Tablet2View extends View {

    private Path path;
    private int PreX,PreY;
    private Paint paint;

    public Tablet2View(Context context) {
        this(context,null);
    }

    public Tablet2View(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Tablet2View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(20);
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
                PreX = x;
                PreY = y;
                path.moveTo(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                path.quadTo(PreX,PreY,x,y);
                invalidate();
                PreX = x;
                PreY = y;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
