package com.example.luoling.android_dome.drawble;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;

public class RevealDrawble extends Drawable {

    private Drawable mUnselectedDrawable;
    private Drawable mSelectedDrawable;
    private int mOrienttation;
    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;

    public RevealDrawble(Drawable unSelectedDrawable,Drawable selectedDrawble,int orienttation){
        mUnselectedDrawable = unSelectedDrawable;
        mSelectedDrawable = selectedDrawble;
        mOrienttation = orienttation;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        //绘制
        int level = getLevel();//from 0 to 10000,这是系统约定的
        if (level == 10000 || level == 0){//全部画选中区域
            mUnselectedDrawable.draw(canvas);
        }else if(level == 5000){//全部画选中区域
            mSelectedDrawable.draw(canvas);
        }else{//混合画选中和未选中区域
            float ratio = (level/5000) - 1f;
            Rect bounds = getBounds();
            int w = bounds.width();
            int h = bounds.height();
            Rect tempRect = new Rect();
            {//画未选中部分
                if (mOrienttation == HORIZONTAL){
                    w = (int) (w * Math.abs(ratio));
                }
                if (mOrienttation == VERTICAL){
                    h = (int) (h * Math.abs(ratio));
                }

                int gravity = ratio < 0 ? Gravity.LEFT :Gravity.RIGHT;

                Gravity.apply(gravity,w,h,bounds,tempRect);
                canvas.save();
                canvas.clipRect(tempRect);
                mUnselectedDrawable.draw(canvas);
                canvas.restore();
            }
            {//画选中的部分
                if (mOrienttation == HORIZONTAL){
                    w -= (int) (w * Math.abs(ratio));
                }
                if (mOrienttation == VERTICAL){
                    h -= (int) (h * Math.abs(ratio));
                }

                int gravity = ratio < 0 ? Gravity.RIGHT : Gravity.LEFT;
                Gravity.apply(gravity,w,h,bounds,tempRect);
                canvas.save();
                canvas.clipRect(tempRect);
                mSelectedDrawable.draw(canvas);
                canvas.restore();
            }
        }
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setBounds(@NonNull Rect bounds) {
        super.setBounds(bounds);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        mUnselectedDrawable.setBounds(bounds);
        mSelectedDrawable.setBounds(bounds);
    }

    @Override
    public int getIntrinsicWidth() {
        return Math.max(mUnselectedDrawable.getIntrinsicWidth(),mSelectedDrawable.getIntrinsicWidth());
    }

    @Override
    public int getIntrinsicHeight() {
        return Math.max(mUnselectedDrawable.getIntrinsicHeight(),mSelectedDrawable.getIntrinsicHeight());
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}
