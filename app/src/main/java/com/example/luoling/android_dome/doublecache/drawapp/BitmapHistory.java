package com.example.luoling.android_dome.doublecache.drawapp;

import android.graphics.Bitmap;

import java.util.Stack;

/**
 * Created by luoling on 2016/10/12.
 */
public class BitmapHistory {
    private static Stack<Bitmap> stack;
    private static BitmapHistory self;
    private BitmapHistory(){
        if (stack == null) {
            stack = new Stack<Bitmap>();
        }
    }
    public static BitmapHistory getInstance(){
        if (self == null){
            self = new BitmapHistory();
        }
        return self;
    }
    public void pushBitmap(Bitmap bitmap){
        int count = stack.size();
        if(count>=5){
            Bitmap bmp = stack.remove(0);
            if (!bmp.isRecycled()){
                bmp.recycle();
                System.gc();
                bmp = null;
            }
        }
        stack.push(bitmap);
    }
    /*撤销*/
    public Bitmap reDo(){
        Bitmap bmp = stack.pop();//将顶部元素删除
        if(stack.empty()){
            return null;
        }else{
            //返回撤销后栈顶部的位图对象
            return stack.peek();
        }
    }
    /*
    * 判断是否还能撤销
    * */
    public boolean isReDo(){
        return !stack.empty();
    }
}
