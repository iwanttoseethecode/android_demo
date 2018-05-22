package com.example.luoling.android_dome.chapter5;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;


public class HighLightLinearGradientTextView extends AppCompatTextView {

    private TextPaint mPaint;
    private LinearGradient mLinearGradient;
    private Matrix mMatrix;

    private float mTranslate;
    private float DELTAX = 20;

    public HighLightLinearGradientTextView(Context context) {
        super(context);
    }

    public HighLightLinearGradientTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int horiz, int vert, int oldHoriz, int oldVert) {
        super.onSizeChanged(horiz, vert, oldHoriz, oldVert);
        mPaint = getPaint();
        String text = getText().toString();
        float textWith = mPaint.measureText(text);
        int gradientSize = (int) (textWith / text.length() * 3);
        mLinearGradient = new LinearGradient(-gradientSize,0,0,0,new int[]{0x22ffffff,0xffffffff,0x22ffffff},null, Shader.TileMode.CLAMP);
        mPaint.setShader(mLinearGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mTranslate += DELTAX;
        float textWidth = getPaint().measureText(getText().toString());
        if (mTranslate > textWidth || mTranslate < 0){
            DELTAX = -DELTAX;
        }
        mMatrix = new Matrix();
        mMatrix.setTranslate(mTranslate,0);
        mLinearGradient.setLocalMatrix(mMatrix);
        postInvalidateDelayed(50);
    }
}
