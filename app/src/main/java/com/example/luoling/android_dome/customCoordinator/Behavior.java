package com.example.luoling.android_dome.customCoordinator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2018/12/18.
 */

public class Behavior {

    public Behavior(Context context, AttributeSet attributeSet){

    }

    public void onTouchMove(View parent, View child, MotionEvent event, float x,float y,float oldx,float oldy){}

    public boolean onTouchMove(View parent,View child,MotionEvent event){
        return false;
    }

    public void onLayoutFinish(View parent,View child){}

    public void onSizeChanged(View parent,View child,int w,int h,int oldw,int oldh){}

    public boolean onLayoutChild(CustomCoordinatorLayout parent,View child,int layoutDirection){
        return false;
    }

    public boolean onStartNestedScroll(View child,View target,int nestedScrollAxes){
        return true;
    }

    public void onStopNestedScroll(View child){

    }

    public void onNestedScrollAccepted(View child,View target,int axes){

    }

    public void onNestedScroll(View scrollView,View target,int dxConsumed,int dyConsumed,int dxUnconsumed,int dyUnconsumed){
    }

    public void onNestedPreScroll(View target,int dx,int dy,int[] consumed){
    }

    public boolean onNestedFling(View target,float velocityX,float velocityY,float consumed){
        return false;
    }

}
