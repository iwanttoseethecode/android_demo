package com.example.luoling.android_dome.Graphics2D;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by luoling on 2016/9/16.
 * 将canvas坐标图进行转换以实现效果
 */
public class CoordinateView extends View {

    public CoordinateView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        //保存现场，保存现在canvas的坐标状态
        canvas.save();
        for(int i = 0;i<10;i++){
            canvas.drawRect(0,0,200,200,paint);
            canvas.translate(20,20);
        }
        //回复上次保存的坐标状态
        canvas.restore();

        //平移坐标，让接下来的图形绘制在上一次图形的下面
        canvas.translate(0,500);
        canvas.save();
        for(int i=0;i<10;i++){
            canvas.drawRect(0,0,400,400,paint);
            canvas.scale(0.9f,0.9f,200,200);
        }

        canvas.restore();


        canvas.translate(0,500);
        canvas.save();

        canvas.drawCircle(200,200,200,paint);
        for(int i=0;i<12;i++){
            canvas.drawLine(350,200,400,200,paint);
            canvas.rotate(30,200,200);
        }
        canvas.restore();
    }
}
