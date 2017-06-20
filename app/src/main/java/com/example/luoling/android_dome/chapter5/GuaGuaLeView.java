package com.example.luoling.android_dome.chapter5;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.luoling.android_dome.R;

import java.util.Random;

/**
 * Created by luoling on 2016/12/29.
 */

public class GuaGuaLeView extends View {
    private Random rnd;
    private Paint paint;
    private Paint clearPaint;
    private static final String[] PRIZE = {
            "无形之刃最为致命",
            "带你装逼带你飞",
            "带你走近垃圾堆",
            "大王叫我来巡山"
    };
    /*涂抹的粗细*/
    private static final int FINGER = 50;
    /*缓冲区*/
    private Bitmap bmpBuffer;
    /*缓冲区画布*/
    private Canvas canvasBuffer;
    private int curX,curY;

    public GuaGuaLeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        rnd = new Random();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(100);
        paint.setColor(Color.WHITE);
        clearPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        clearPaint.setStrokeCap(Paint.Cap.ROUND);
        clearPaint.setStrokeJoin(Paint.Join.ROUND);
        clearPaint.setStrokeWidth(FINGER);
        drawBackground();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //被初始化缓存区
        bmpBuffer = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
        canvasBuffer = new Canvas(bmpBuffer);
        //为缓冲区蒙上一灰色
        canvasBuffer.drawColor(Color.parseColor("#FF808080"));
    }

    /*
        * 随机生成中奖信息
        * @return 数组PRIZE 的索引
        * */
    private int getPrizeIndex(){
        return rnd.nextInt(PRIZE.length);
    }

    /*
    * 绘制背景，背景包括背景图片和文字
    * */
    private void drawBackground(){
        Bitmap bmpBackground = BitmapFactory.decodeResource(getResources(), R.mipmap.ggbackground);
        //从资源中读取的图片不可以修改，复制出一张可以修改的图片
        Bitmap bmpBackgroundMutable = bmpBackground.copy(Bitmap.Config.ARGB_8888,true);
        Canvas cvsBackground = new Canvas(bmpBackgroundMutable);
        //计算出文字所占的区域，将文字放在正中间
        String text = PRIZE[getPrizeIndex()];
        Rect rect = new Rect();
        paint.getTextBounds(text,0,text.length(),rect);
        int x = (bmpBackgroundMutable.getWidth()-rect.width())/2;
        int y = (bmpBackgroundMutable.getHeight()-rect.height())/2;
        this.setLayerType(View.LAYER_TYPE_SOFTWARE,paint);
        paint.setShadowLayer(10,0,0,Color.GREEN);
        cvsBackground.drawText(text,x,y,paint);
        paint.setShadowLayer(0,0,0,Color.YELLOW);
        //画背景
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            this.setBackground(new BitmapDrawable(getResources(),bmpBackgroundMutable));
        }else{
            this.setBackground(new BitmapDrawable(bmpBackgroundMutable));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bmpBuffer,0,0,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                curX = x;
                curY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                canvasBuffer.drawLine(curX,curY,x,y,clearPaint);
                invalidate();
                curX = x;
                curY = y;
                break;
            case MotionEvent.ACTION_UP:
                invalidate();
                break;
        }

        return true;
    }
}
