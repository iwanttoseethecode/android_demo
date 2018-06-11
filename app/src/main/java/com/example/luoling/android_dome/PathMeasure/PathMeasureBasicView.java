package com.example.luoling.android_dome.PathMeasure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/*
* PathMeasure常用的方法有void	setPath(Path path, boolean forceClosed)
boolean	isClosed()
float	getLength()
boolean	nextContour()
boolean	getSegment(float startD, float stopD, Path dst, boolean startWithMoveTo)
boolean	getPosTan(float distance, float[] pos, float[] tan)
boolean	getMatrix(float distance, Matrix matrix, int flags)
* */

public class PathMeasureBasicView extends View {
    private int mWidth;
    private int mHeight;
    private Paint textPaint;
    private Paint mDeafultPaint;
    private Paint lightPaint;
    private int index = 0;
    private IMyDrawable defaultDrawable;

    private List<IMyDrawable> mList;

    public PathMeasureBasicView(Context context) {
        super(context);
    }

    public PathMeasureBasicView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        textPaint = new Paint();
        textPaint.setTextSize(40);
        textPaint.setColor(Color.GREEN);
        textPaint.setStyle(Paint.Style.FILL);
        mDeafultPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDeafultPaint.setColor(Color.RED);
        mDeafultPaint.setStrokeWidth(5);
        mDeafultPaint.setStyle(Paint.Style.STROKE);
        lightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        lightPaint.setColor(Color.DKGRAY);
        lightPaint.setStrokeWidth(4);
        lightPaint.setStyle(Paint.Style.STROKE);

        mList = new ArrayList<IMyDrawable>();
        mList.add(new TestNextContour());
        mList.add(new TestGetSegmentMoveTo());
        mList.add(new TestGetSegment());
        mList.add(new TestForceClosed());
        defaultDrawable = mList.get(index);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawText("点击切换",10,30,textPaint);
        defaultDrawable.draw(canvas);

    }

    interface IMyDrawable{
        void draw(Canvas canvas);
    }

    class TestNextContour implements IMyDrawable{
        @Override
        public void draw(Canvas canvas) {
            Path path = new Path();
            Path path1 = new Path();
            Path path2 = new Path();
            path1.addRect(100,100,200,200,Path.Direction.CW);
            path2.addRect(100,300,200,500,Path.Direction.CW);
            path.op(path1,path2,Path.Op.XOR);
            canvas.drawPath(path,mDeafultPaint);

            PathMeasure pathMeasure = new PathMeasure(path,false);

            float len1 = pathMeasure.getLength();
            pathMeasure.nextContour();
            float len2 = pathMeasure.getLength();

            String tip = "len1 = "+len1+"    len2 = "+len2;

            canvas.drawText(tip,30,mHeight - 60,textPaint);

        }
    }

    class TestGetSegmentMoveTo implements IMyDrawable{

        @Override
        public void draw(Canvas canvas) {
            Path path = new Path();
            path.addRect(100,100,300,300,Path.Direction.CW);

            Path dst = new Path();
            dst.moveTo(100,100);

            PathMeasure pathMeasure = new PathMeasure(path,false);

            //startWithMoveTo是否启用MoveTo的点开始截取
            pathMeasure.getSegment(0,600,dst,true);
            canvas.drawPath(path,lightPaint);

            canvas.drawPath(dst,mDeafultPaint);

        }
    }

    class TestGetSegment implements IMyDrawable{

        @Override
        public void draw(Canvas canvas) {
            Path path = new Path();
            path.addRect(100,100,300,300,Path.Direction.CW);

            Path dst = new Path();

            PathMeasure pathMeasure = new PathMeasure(path,false);

            //startWithMoveTo是否启用MoveTo的点开始截取,不启用就从坐标原点开始
            pathMeasure.getSegment(0,600,dst,false);
            canvas.drawPath(path,lightPaint);

            canvas.drawPath(dst,mDeafultPaint);
        }
    }

    class TestForceClosed implements IMyDrawable{

        @Override
        public void draw(Canvas canvas) {
            Path path = new Path();
            path.moveTo(100,100);
            path.lineTo(300,100);
            path.lineTo(300,200);
            path.lineTo(100,200);

            PathMeasure measure1 = new PathMeasure(path,false);
            PathMeasure measure2 = new PathMeasure(path,true);

            canvas.drawPath(path,mDeafultPaint);

            canvas.drawText("forceClosed=false length = "+measure1.getLength(),20,mHeight-100,textPaint);
            canvas.drawText("forceClosed=true length = "+measure2.getLength(),20,mHeight-40,textPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                index++;
                if (index >= mList.size()){
                    index = 0;
                }
                defaultDrawable = mList.get(index);
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }
}
