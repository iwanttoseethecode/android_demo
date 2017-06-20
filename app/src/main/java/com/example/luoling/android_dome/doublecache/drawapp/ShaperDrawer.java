package com.example.luoling.android_dome.doublecache.drawapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by luoling on 2016/10/12.
 */
public abstract class ShaperDrawer {
    private View view;
    public ShaperDrawer(View view){
        super();
        this.view = view;
    }
    public View getView(){
        return view;
    }
    /*
    * 用于绘图
    * */
    public void draw(Canvas viewCanvas){
        //画历史结果
        Bitmap bitmap = BitmapBuffer.getInstance().getBitmap();
        viewCanvas.drawBitmap(bitmap,0,0,null);
    }
    /*
    * 用于响应触摸事件
    * */
    public abstract boolean onTouchEvent(MotionEvent event);
    /*
    * 绘图的逻辑
    * */
    public abstract void logic();
}
