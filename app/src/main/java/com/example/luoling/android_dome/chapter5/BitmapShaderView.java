package com.example.luoling.android_dome.chapter5;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.example.luoling.android_dome.R;

/**
 * Created by luoling on 2016/12/27.
 */

public class BitmapShaderView extends View {
    public BitmapShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        BitmapShader bs = new BitmapShader(bmp, Shader.TileMode.REPEAT,Shader.TileMode.MIRROR);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShader(bs);
        canvas.drawRect(new Rect(0,0,getMeasuredWidth(),800),paint);
    }
}
