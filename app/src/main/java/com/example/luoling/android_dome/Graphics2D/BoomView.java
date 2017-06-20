package com.example.luoling.android_dome.Graphics2D;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.luoling.android_dome.R;

/**
 * Created by luoling on 2016/10/9.
 */
public class BoomView extends View {
    /*代表动画的第几帧*/
    private int i=0;
    private Bitmap boomBitmap;

    public BoomView(Context context) {
        this(context,null);
    }

    public BoomView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BoomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        boomBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.boompic);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取位置的宽度和高度
        int width = boomBitmap.getWidth();
        int height = boomBitmap.getHeight();
        //剪切区
        int framewidth = width/7;
        Rect rect = new Rect(0,0,framewidth,height);
        canvas.clipRect(rect);
        canvas.drawBitmap(boomBitmap,-i*framewidth,0,null);
        i++;
        if(i==7) i=0;//播放完之后将i重置为0，继续播放。
    }
}
