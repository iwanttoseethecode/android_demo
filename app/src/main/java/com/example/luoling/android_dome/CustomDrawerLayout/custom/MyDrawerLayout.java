package com.example.luoling.android_dome.CustomDrawerLayout.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyDrawerLayout extends DrawerLayout implements DrawerLayout.DrawerListener{

    private MyDrawerSliderMenu myDrawerSliderMenu;

    private View contentView;
    private float slideOffset;
    private MyDrawerFrameLayout myDrawerFrameLayout;
    private float y;

    public MyDrawerLayout(@NonNull Context context) {
        this(context,null);
    }

    public MyDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        for (int i = 0; i <getChildCount(); i++){
            View view = getChildAt(i);
            if (view instanceof MyDrawerSliderMenu){
                myDrawerSliderMenu = (MyDrawerSliderMenu) view;
            }else{
                contentView = view;
            }
        }

        //实现偷梁换柱在中间包裹一层控件
        removeView(myDrawerSliderMenu);
        myDrawerFrameLayout = new MyDrawerFrameLayout(this.getContext(),myDrawerSliderMenu);
        addView(myDrawerFrameLayout);
        addDrawerListener(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        
        y = ev.getY();
        if (ev.getAction() == MotionEvent.ACTION_UP){
            myDrawerFrameLayout.onMoveUp();
            return super.dispatchTouchEvent(ev);
        }
        
        if (slideOffset < 1){
            return super.dispatchTouchEvent(ev);
        }else{
            myDrawerFrameLayout.setTouchY(y,slideOffset);
        }
        
        return super.dispatchTouchEvent(ev);
    }

    /**
     * @param drawerView
     * @param slideOffset 侧滑的百分比
     */
    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        this.slideOffset = slideOffset;
        myDrawerFrameLayout.setTouchY(y,slideOffset);

        //对内容区域也进行一定的平移
        float contentViewOffset = drawerView.getWidth()*slideOffset / 2;
        contentView.setTranslationX(contentViewOffset);
    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
