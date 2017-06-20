package com.example.luoling.android_dome.Graphics2D;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by luoling on 2016/10/9.
 */
public class WatchView extends View {

    private Paint paint;
    //表盘的半径
    private int r=0;
    //控件的长和宽
    private int width=0,height=0;
    private Calendar calendar;
    private Path path;

    public WatchView(Context context) {
        this(context,null);
    }

    public WatchView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WatchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        path = new Path();
        calendar = Calendar.getInstance();
    }

    public void run(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                postInvalidate();
            }
        },0,1000);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取组件宽高度
        width = getMeasuredWidth()-8;
        height = getMeasuredHeight()-8;
        //获取表盘的半径
        r = Math.min(width,height)/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.STROKE);
        //绘制表盘
        drawPlate(canvas);
        //绘制指针
        drawPoints(canvas);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width/2,height/2,8,paint);
    }

    private void drawPlate(Canvas canvas){
        canvas.save();
        //画园
        canvas.drawCircle(width/2,height/2,r,paint);
        //画刻度（一共60根，没第5根是长刻度）
        for(int i=0;i<60;i++){
            if(i%5==0){
                //长刻度占园盘的十分之一
                paint.setStrokeWidth(4);
                paint.setColor(Color.RED);
                canvas.drawLine(width/2,height/2-r,width/2,height/2-9*r/10,paint);
                //通过二阶贝塞尔曲线画数字
                path.moveTo(width/2-60,height/2-4*r/5);
                path.quadTo(width/2-60,height/2-4*r/5,width/2+60,height/2-4*r/5);
                paint.setStrokeWidth(2);
                paint.setTextSize(30);
                if(i==0){
                    canvas.drawTextOnPath("12",path,45,10,paint);
                }else if(i==50 ||i==55){
                    canvas.drawTextOnPath(String.valueOf(i/5),path,45,10,paint);
                }else{
                    canvas.drawTextOnPath(String.valueOf(i/5),path,50,10,paint);
                }

            }else{
                paint.setStrokeWidth(2);
                paint.setColor(Color.GRAY);
                canvas.drawLine(width/2,height/2-r,width/2,height/2-14*r/15,paint);
            }
            canvas.rotate(6,width/2,height/2);
        }
        canvas.restore();
    }

    private void drawPoints(Canvas canvas){
        calendar.setTimeInMillis(System.currentTimeMillis());
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int secend = calendar.get(Calendar.SECOND);

        //画时针
        int degree = 360/12*hour;
        double radians = Math.toRadians(degree);
        int endX = (int) (width/2+r*0.5*Math.cos(radians));
        int endY = (int) (height/2+r*0.5*Math.sin(radians));
        canvas.save();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(6);
        canvas.rotate(-90,width/2,height/2);
        canvas.drawLine(width/2,height/2,endX,endY,paint);
        radians = Math.toRadians(degree-180);
        endX = (int) (width/2+r*0.07*Math.cos(radians));
        endY = (int) (height/2+r*0.07*Math.sin(radians));
        canvas.drawLine(width/2,height/2,endX,endY,paint);
        canvas.restore();

        // 画分针
        degree = 360/60*minute;
        radians = Math.toRadians(degree);
        endX = (int) (width/2+r*0.65*Math.cos(radians));
        endY = (int) (height/2+r*0.65*Math.sin(radians));
        canvas.save();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(4);
        canvas.rotate(-90,width/2,height/2);
        canvas.drawLine(width/2,height/2,endX,endY,paint);
        radians = Math.toRadians(degree-180);
        endX = (int) (width/2+r*0.10*Math.cos(radians));
        endY = (int) (height/2+r*0.10*Math.sin(radians));
        canvas.drawLine(width/2,height/2,endX,endY,paint);
        canvas.restore();

        //画秒针
        degree = 360/60*secend;
        radians = Math.toRadians(degree);
        endX = (int) (width/2+r*0.8*Math.cos(radians));
        endY = (int) (height/2+r*0.8*Math.sin(radians));
        canvas.save();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);
        canvas.rotate(-90,width/2,height/2);
        canvas.drawLine(width/2,height/2,endX,endY,paint);
        //给秒针加个尾巴
        radians = Math.toRadians(degree-180);
        endX = (int) (width/2+r*0.15*Math.cos(radians));
        endY = (int) (height/2+r*0.15*Math.sin(radians));
        canvas.drawLine(width/2,height/2,endX,endY,paint);
        canvas.restore();
    }
}
