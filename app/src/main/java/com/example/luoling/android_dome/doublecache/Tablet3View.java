package com.example.luoling.android_dome.doublecache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by luoling on 2016/10/10.
 * 本控件的优势在于既有TabletView的双缓存，又有Tablet2View尔塞尔曲线算法，可以使路径更平滑
 */
public class Tablet3View extends View {

    private Path path;
    private int preX,preY;
    private Paint paint;
    private Bitmap bitmapBuffer;
    private Canvas bitmapCanvas;

    public Tablet3View(Context context) {
        this(context,null);
    }

    public Tablet3View(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Tablet3View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(25);
        paint.setColor(Color.RED);
        path = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(bitmapBuffer==null){
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            bitmapBuffer = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
            bitmapCanvas = new Canvas(bitmapBuffer);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmapBuffer,0,0,null);
        canvas.drawPath(path,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.reset();
                preX = x;
                preY = y;
                path.moveTo(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                //使用贝塞尔曲线可以使路径更加平滑
                path.quadTo((preX+x)/2,(preY+y)/2,x,y);
                invalidate();
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_UP:
                bitmapCanvas.drawPath(path,paint);
                invalidate();
                break;
        }
        return true;
    }
}
