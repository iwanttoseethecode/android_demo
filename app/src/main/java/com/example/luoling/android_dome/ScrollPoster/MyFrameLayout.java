package com.example.luoling.android_dome.ScrollPoster;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;

public class MyFrameLayout extends FrameLayout implements ScrollAnimationInterface{
    public MyFrameLayout(@NonNull Context context) {
        super(context);
    }

    private static final int TRANSLATION_FROM_TOP = 0x01;
    private static final int TRANSLATION_FROM_BOTTOM = 0x02;
    private static final int TRANSLATION_FROM_LEFT = 0x04;
    private static final int TRANSLATION_FROM_RIGHT = 0x08;

    private static ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    /**
     * 背景颜色变化开始值
     */
    private int mDiscrollveFromBgColor;
    /**
     * 背景颜色变化结束值
     */
    private int mDiscrollveToBgColor;
    /**
     * 是否需要透明度动画
     */
    private boolean mDiscrollveAlpha;
    /**
     * 平移值
     */
    private int mDiscrollveTranslation;
    /**
     * 是否需要x轴方向放缩
     */
    private boolean mDiscrollveScaleX;
    /**
     * 是否需要y轴放缩
     */
    private boolean mDiscrollveScaleY;

    private int mHeight;
    private int mWidth;

    public void setmDiscrollveFromBgColor(int mDiscrollveFromBgColor) {
        this.mDiscrollveFromBgColor = mDiscrollveFromBgColor;
    }

    public void setmDiscrollveToBgColor(int mDiscrollveToBgColor) {
        this.mDiscrollveToBgColor = mDiscrollveToBgColor;
    }

    public void setmDiscrollveAlpha(boolean mDiscrollveAlpha) {
        this.mDiscrollveAlpha = mDiscrollveAlpha;
    }

    public void setmDiscrollveTranslation(int mDiscrollveTranslation) {
        this.mDiscrollveTranslation = mDiscrollveTranslation;
    }

    public void setmDiscrollveScaleX(boolean mDiscrollveScaleX) {
        this.mDiscrollveScaleX = mDiscrollveScaleX;
    }

    public void setmDiscrollveScaleY(boolean mDiscrollveScaleY) {
        this.mDiscrollveScaleY = mDiscrollveScaleY;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        onResetAnimation();
    }

    @Override
    public void onScrollAnimation(float ratio) {
        //执行动画ratio：0~1
        if(mDiscrollveAlpha){
            setAlpha(ratio);
        }
        if(mDiscrollveScaleX){
            setScaleX(ratio);
        }
        if(mDiscrollveScaleY){
            setScaleY(ratio);
        }
        //平移动画  int值：left,right,top,bottom    left|bottom
        if(isTranslationFrom(TRANSLATION_FROM_BOTTOM)){//是否包含bottom
            setTranslationY(mHeight*(1-ratio));//height--->0(0代表恢复到原来的位置)
        }
        if(isTranslationFrom(TRANSLATION_FROM_TOP)){//是否包含bottom
            setTranslationY(-mHeight*(1-ratio));//-height--->0(0代表恢复到原来的位置)
        }
        if(isTranslationFrom(TRANSLATION_FROM_LEFT)){
            setTranslationX(-mWidth*(1-ratio));//mWidth--->0(0代表恢复到本来原来的位置)
        }
        if(isTranslationFrom(TRANSLATION_FROM_RIGHT)){
            setTranslationX(mWidth*(1-ratio));//-mWidth--->0(0代表恢复到本来原来的位置)
        }
        //判断从什么颜色到什么颜色
        if(mDiscrollveFromBgColor!=-1&&mDiscrollveToBgColor!=-1){
            setBackgroundColor((int) argbEvaluator.evaluate(ratio, mDiscrollveFromBgColor, mDiscrollveToBgColor));
        }
    }

    @Override
    public void onResetAnimation() {
        if(mDiscrollveAlpha){
            setAlpha(0);
        }
        if(mDiscrollveScaleX){
            setScaleX(0);
        }
        if(mDiscrollveScaleY){
            setScaleY(0);
        }
        //平移动画  int值：left,right,top,bottom    left|bottom
        if(isTranslationFrom(TRANSLATION_FROM_BOTTOM)){//是否包含bottom
            setTranslationY(mHeight);//height--->0(0代表恢复到原来的位置)
        }
        if(isTranslationFrom(TRANSLATION_FROM_TOP)){//是否包含bottom
            setTranslationY(-mHeight);//-height--->0(0代表恢复到原来的位置)
        }
        if(isTranslationFrom(TRANSLATION_FROM_LEFT)){
            setTranslationX(-mWidth);//mWidth--->0(0代表恢复到本来原来的位置)
        }
        if(isTranslationFrom(TRANSLATION_FROM_RIGHT)){
            setTranslationX(mWidth);//-mWidth--->0(0代表恢复到本来原来的位置)
        }
    }

    private boolean isTranslationFrom(int translationMask){
        if(mDiscrollveTranslation ==-1){
            return false;
        }
        //fromLeft|fromeBottom & fromBottom = fromBottom
        return (mDiscrollveTranslation & translationMask) == translationMask;
    }
}
