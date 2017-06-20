package com.example.luoling.android_dome.doublecache.drawapp;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by luoling on 2016/10/12.
 */
public class DrawView extends View {

    private ShaperDrawer shaperDrawer;//图形绘制器

    public DrawView(Context context) {
        this(context,null);
        shaperDrawer = new LineDrawer(this);
    }

    public DrawView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        shaperDrawer = new LineDrawer(this);
    }

    public DrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        shaperDrawer = new LineDrawer(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        SystemParams.areaWidth = this.getMeasuredWidth();
        SystemParams.areaHeight = this.getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (SystemParams.isRedo){
            canvas.drawBitmap(BitmapBuffer.getInstance().getBitmap(),0,0,null);
            SystemParams.isRedo = false;
        }else{
            shaperDrawer.draw(canvas);
        }
        //逻辑
        shaperDrawer.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return shaperDrawer.onTouchEvent(event);
    }

}
