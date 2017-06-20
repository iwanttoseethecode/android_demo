package com.example.luoling.android_dome.Graphics2D;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by luoling on 2016/9/16.
 */
public class BollMoveView extends View {

    /*小球圆心水平位置*/
    private int x;
    /*小球圆心垂直位置*/
    private static final int y =100;
    /*小球半径*/
    private static final int RADIUS = 30;

    private Paint paint;
    /*小球水平移动方向,true表示正向移动，false表示反响移动*/
    private boolean direction;

    public BollMoveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);//参数表示去锯齿
        paint.setColor(Color.RED);
//        paint.setAntiAlias(true);也是去掉锯齿的
        x = RADIUS;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //根据x,y的坐标值画一个小球
        canvas.drawCircle(x,y,RADIUS,paint);
        //改变x之后，调用invalidate()方法，控件将重新绘制，小球因x变化而产生移动效果。
        int width = getMeasuredWidth();
        if(x<RADIUS){//正向移动
            direction = true;
        }
        if(width-x<RADIUS){//反向移动
            direction = false;
        }
        x = direction ? x+5:x-5;
    }
}
