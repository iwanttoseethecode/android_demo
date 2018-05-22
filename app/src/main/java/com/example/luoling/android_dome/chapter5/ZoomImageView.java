package com.example.luoling.android_dome.chapter5;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.luoling.android_dome.R;

public class ZoomImageView extends View {

    //放大倍数
    private static final int FACTOR = 2;
    //放大镜的半径
    private static final int RADIUS  = 100;
    // 原图
    private Bitmap mBitmap;
    // 放大后的图
    private Bitmap mBitmapScale;
    // 制作的圆形的图片（放大的局部），盖在Canvas上面
    private ShapeDrawable mShapeDrawable;

    private Matrix matrix;

    public ZoomImageView(Context context) {
        super(context);
        init();
    }

    public ZoomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.baby);
        mBitmapScale = Bitmap.createScaledBitmap(mBitmap,mBitmap.getWidth() * FACTOR,mBitmap.getHeight() * FACTOR,true);
        BitmapShader bitmapShader = new BitmapShader(mBitmapScale, Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);

        mShapeDrawable = new ShapeDrawable(new OvalShape());
        mShapeDrawable.getPaint().setShader(bitmapShader);
        mShapeDrawable.setBounds(0,0,RADIUS * 2,RADIUS * 2);
        matrix = new Matrix();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mBitmap.getWidth(),mBitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //1.画原图
        canvas.drawBitmap(mBitmap,0,0,null);
        //2.画放大镜的图
        mShapeDrawable.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();
        //将放大的图片往相反的方向挪动
        matrix.setTranslate(RADIUS - x * FACTOR, RADIUS - y * FACTOR);
        mShapeDrawable.getPaint().getShader().setLocalMatrix(matrix);
        // 切出手势区域点位置的圆
        mShapeDrawable.setBounds(x-RADIUS,y-RADIUS,x+RADIUS,y+RADIUS);
        invalidate();
        return true;
    }
}
