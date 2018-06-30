package com.example.luoling.android_dome.CustomDrawerLayout.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.widget.LinearLayout;

import com.example.luoling.android_dome.R;

public class MyDrawerSliderMenu extends LinearLayout {
    private float maxTranslationX;

    private boolean opened = false;

    public MyDrawerSliderMenu(Context context) {
        this(context,null);
    }

    public MyDrawerSliderMenu(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyDrawerSliderMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr,0);
        if (attrs != null){
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SiderMenu);
            maxTranslationX = a.getDimension(R.styleable.SiderMenu_maxTranslationX,0);
            a.recycle();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (maxTranslationX > w/4 || maxTranslationX <= 0){
            maxTranslationX = w/4;
        }
    }

    public void setTouchY(float y, float slideOffset){
        //如果slideOffset == 1 则侧滑控件完全打开了

        opened = slideOffset == 1;
        for (int i = 0; i <getChildCount();i++){
            View child = getChildAt(i);
            child.setPressed(false);

            if (opened){
                boolean isHover = y > child.getTop() && y < child.getBottom();
                if (isHover){
                    child.setPressed(true);
                }
            }

            apply(getParent(),child,y,slideOffset);
        }
    }

    private void apply(ViewParent parent, View child, float y, float slideOffset) {
        float translationX = 0;

        int centerY = child.getTop() + child.getBottom() / 2;

        //控件中心点和手指所在点的相对距离
        float distance = Math.abs(y - centerY);
        float scale = distance/getHeight() * 1.5f;
        translationX = maxTranslationX - scale * maxTranslationX;
        child.setTranslationX(translationX);
    }

    /**
     * 手指释放的时候
     */
    public void onMotionUp(){
        for (int i = 0;opened && i < getChildCount();i++){
            View view = getChildAt(i);
            view.setTranslationX(0);
            if (view.isPressed()){
                view.performClick();
            }
        }
    }

}
