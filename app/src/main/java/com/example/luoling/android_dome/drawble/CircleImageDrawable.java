package com.example.luoling.android_dome.drawble;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class CircleImageDrawable extends Drawable {

    private Paint paint;
    private Bitmap mBitmap;
    private int radius;

    public CircleImageDrawable(Bitmap bitmap){
        paint = new Paint();
        paint.setAntiAlias(true);
        mBitmap = bitmap;
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(bitmapShader);
        radius = Math.min(mBitmap.getWidth(),mBitmap.getHeight())/2;
    }

    @Override
    public int getIntrinsicWidth() {
        return radius*2;
    }

    @Override
    public int getIntrinsicHeight() {
        return radius*2;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawCircle(radius,radius,radius,paint);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}
