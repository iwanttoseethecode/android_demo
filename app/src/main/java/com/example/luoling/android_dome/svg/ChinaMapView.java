package com.example.luoling.android_dome.svg;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.luoling.android_dome.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ChinaMapView extends View {
    private Thread myThread;
    private int width;
    private int height;

    private Paint paint;
    private int[] colorArray = new int[]{0xff239bd7,0xff30a9e5,0xff80cbf1,0xff30dae2,0xffaa88dd};
    private List<ProviceItem> proviceItemList = new ArrayList<>();
    private ProviceItem selectItem;

    //放大地图倍数
    private float scale = 1.9f;

    //动画执行百分比
    private float fraction;
    private ValueAnimator valueAnimator;

    public ChinaMapView(Context context) {
        this(context,null);
    }

    public ChinaMapView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ChinaMapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr,0);
        init(context);
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    private void init(Context context) {
        paint = new Paint();
        WeakReference<Context> weakReference = new WeakReference<>(context);
        myThread = new MyThread(weakReference);
        myThread.start();
    }

    class MyThread extends Thread{
        WeakReference<Context> mWeakReference;
        public MyThread(WeakReference<Context> weakReference) {
            this.mWeakReference = weakReference;
        }

        @Override
        public void run() {
            Context context = mWeakReference.get();
            List<ProviceItem> list = new ArrayList<>();
            InputStream inputStream = context.getResources().openRawResource(R.raw.chinahigh);

            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(inputStream);

                Element rootElement = document.getDocumentElement();
                NodeList items = rootElement.getElementsByTagName("path");
                for (int i = 0; i < items.getLength();i++){
                    Element element = (Element) items.item(i);
                    String pathData = element.getAttribute("android:pathData");
                    String name = element.getAttribute("android:name");


                    //大神写的PathParser类，用于解析svg的path路径
                    Path path = PathParser.createPathFromPathData(pathData);
                    ProviceItem proviceItem = new ProviceItem(path,name);
                    list.add(proviceItem);
                }
                Message msg = Message.obtain();
                msg.obj = list;
                handler.sendMessage(msg);
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            proviceItemList = (List<ProviceItem>) msg.obj;
            if (proviceItemList == null){
                return;
            }
            int length = proviceItemList.size();
            for (int i = 0; i < length; i ++ ){
                int color = Color.WHITE;
                switch(i%5){
                    case 0:
                        color = colorArray[0];
                        break;
                        case 1:
                            color = colorArray[1];
                            break;
                        case 2:
                            color = colorArray[2];
                        break;
                        case 3:
                            color = colorArray[3];
                        break;
                        case 4:
                            color = colorArray[4];
                        break;
                }
                proviceItemList.get(i).setDrawColor(color);
            }
            postInvalidate();
        }
    };

    public void destory(){
        if (myThread != null){
            myThread.interrupt();
            myThread = null;
        }
        handler.removeCallbacksAndMessages(null);
        handler = null;
        valueAnimator.cancel();
        valueAnimator.removeAllUpdateListeners();
        valueAnimator = null;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int defaultWidth = (int) 1800;
        int dfaultHeight = (int) 1080;

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int measureWidth = 0;
        int measureHeight = 0;

        switch (widthMode){
            case MeasureSpec.EXACTLY:
                measureWidth = width;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                measureWidth = defaultWidth;
                break;
        }

        switch (heightMode){
            case MeasureSpec.EXACTLY:
                measureHeight = width;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                measureHeight = dfaultHeight;
                break;
        }

        setMeasuredDimension(measureWidth,measureHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (proviceItemList.isEmpty()){
            return;
        }
        canvas.save();
        canvas.scale(scale,scale);


        for (ProviceItem proviceItem : proviceItemList){
            if (proviceItem != selectItem){
                proviceItem.drawItem(canvas,paint,fraction,false);
            }
        }
        if (selectItem != null){
            String name = selectItem.getName();
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(30);
            Rect rect = new Rect();
            paint.getTextBounds(name,0,name.length(),rect);
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            //计算baseline
            int y = (int) ((fontMetrics.bottom -fontMetrics.top)/2 - fontMetrics.top + 50);
            canvas.drawText(name,(width - rect.width())/(2.3f * scale),y,paint);

            selectItem.drawItem(canvas,paint,fraction,true);
        }
        canvas.restore();
    }

    public void startAnim(ProviceItem proviceItem){
        PathMeasure pathMeasure = new PathMeasure(proviceItem.getPath(),true);
        int size = 0;
        do{
            size += pathMeasure.getLength();
        } while (pathMeasure.nextContour());
        int time = (int) (size * 8);
        valueAnimator = ValueAnimator.ofFloat(0,1f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                fraction = animation.getAnimatedFraction();
                invalidate();
            }
        });
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setDuration(time);
        valueAnimator.start();
    }

    public void stopAnim(){
        if (valueAnimator == null){
            return;
        }
        valueAnimator.cancel();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        handleTouch(event.getX(),event.getY());
        return true;
    }

    private void handleTouch(float x, float y) {
        if (proviceItemList == null){
            return;
        }
        ProviceItem tmpItem = null;
        for (ProviceItem item : proviceItemList){
            if (item.isTouch(x / scale,y /scale)){
                tmpItem = item;
                break;
            }
        }
        if (tmpItem != null){
            selectItem = tmpItem;
            stopAnim();
            startAnim(selectItem);
            invalidate();
        }
    }


}
