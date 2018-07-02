package com.example.luoling.android_dome.CustomDrawerLayout.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class MyDrawerBgView extends View {

    private Paint paint;
    private Path path;
    private BitmapDrawable bgDrawable;
    private Bitmap bgbitmap;
    private Shader shader;

    public MyDrawerBgView(Context context) {
        this(context, null);
    }

    public MyDrawerBgView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyDrawerBgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        path = new Path();
    }

    public void setTouchY(float y, float percent) {
        path.reset();
        float width = getWidth() * percent;
        float height = getHeight();
        float offsetY = height / 8;
        float x = width / 2;
        path.moveTo(0, 0);
        path.lineTo(x, -offsetY);
        path.quadTo(width * 3 / 2, y, x, height + offsetY);
        path.lineTo(0, height + offsetY);
        path.close();
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (bgDrawable != null) {
            bgbitmap = bgDrawable.getBitmap();
            shader = new BitmapShader(bgbitmap, Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
            paint.setShader(shader);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    /**
     * 传递颜色
     *
     * @param color
     */
    public void setColor(int color) {
        paint.setColor(color);
    }

    public void setColor(Drawable color) {
        if (color instanceof ColorDrawable) {
            ColorDrawable colorDrawable = (ColorDrawable) color;
            paint.setColor(colorDrawable.getColor());
        } else if (color instanceof BitmapDrawable) {
            //实现背景图片 的  变换
            bgDrawable = (BitmapDrawable) color;
        }
    }

    public void onMoveUp() {
        path.reset();
        path.moveTo(0,0);
        path.lineTo(getWidth()*2/3,0);
        path.lineTo(getWidth()*2/3,getHeight());
        path.lineTo(0,getHeight());
        path.close();
        invalidate();
    }
}
