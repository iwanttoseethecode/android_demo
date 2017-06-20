package com.example.luoling.android_dome.chapter5;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by luoling on 2016/12/1.
 */

public class RadialGradientView extends View {
    public RadialGradientView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rect = new Rect(100,100,500,500);
        RadialGradient rg = new RadialGradient(300,300,200,Color.RED,Color.BLUE,Shader.TileMode.MIRROR);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShader(rg);
        canvas.drawRect(rect,paint);
        canvas.translate(510,0);
        canvas.drawOval(new RectF(rect),paint);
    }
}
