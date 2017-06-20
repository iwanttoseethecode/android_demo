package com.example.luoling.android_dome.doublecache.drawapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by luoling on 2016/10/12.
 */
public class BitmapBuffer {
    private Bitmap bitmap;
    private Canvas canvas;
    private static BitmapBuffer self;
    private BitmapBuffer(int width,int height){
        init(width,height);
    }
    private void init(int width,int height){
        bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas();
        canvas.setBitmap(bitmap);
    }
    public static BitmapBuffer getInstance(){
        if (self == null){
            self = new BitmapBuffer(SystemParams.areaWidth,SystemParams.areaHeight);
        }
        return self;
    }
    /*获取缓冲区的画布*/
    public Canvas getCanvas(){
        return canvas;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
    /*将当前的绘图结果保存到栈中*/
    public void pushBitmap(){
        BitmapHistory.getInstance().pushBitmap(bitmap.copy(Bitmap.Config.ARGB_8888,false));
    }
    /*撤销*/
    public void redo(){
        BitmapHistory his = BitmapHistory.getInstance();
        if (his.isReDo()){
            Bitmap bmp = his.reDo();
            if (bmp!=null){
                bitmap = bmp.copy(Bitmap.Config.ARGB_8888,true);
                canvas.setBitmap(bitmap);
                if(bmp!=null && !bmp.isRecycled()){
                    bmp.recycle();
                    System.gc();
                    bmp = null;
                }
            }
        }

    }
}
