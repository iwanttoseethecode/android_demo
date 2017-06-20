package com.example.luoling.android_dome.chapter5;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by luoling on 2016/11/30.
 */

public class ShaderView extends View {
    public ShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(150);
        setLayerType(View.LAYER_TYPE_SOFTWARE,paint);
        paint.setShadowLayer(10,1,1, Color.RED);
        canvas.drawText("Android 开发",100,100,paint);
        paint.setShadowLayer(6,40,40,Color.BLUE);
        canvas.drawText("Android绘图技术",100,550,paint);
    }
}
