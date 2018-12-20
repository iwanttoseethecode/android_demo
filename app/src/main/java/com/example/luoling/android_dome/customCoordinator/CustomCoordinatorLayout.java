package com.example.luoling.android_dome.customCoordinator;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.NestedScrollingParent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.example.luoling.android_dome.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Administrator on 2018/12/18.
 */

public class CustomCoordinatorLayout extends RelativeLayout implements ViewTreeObserver.OnGlobalLayoutListener,NestedScrollingParent{
    public CustomCoordinatorLayout(Context context) {
        super(context);
    }

    public CustomCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public RelativeLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(),attrs);
    }

    @Override
    protected void onSizeChanged(int w,int h,int oldw,int oldh){
        super.onSizeChanged(w,h,oldw,oldh);
        for (int i = 0;i < getChildCount();i++){
            View child = getChildAt(i);
            LayoutParams params = (LayoutParams) child.getLayoutParams();
            if (params.getBehavior() != null){
                params.getBehavior().onSizeChanged(this,child,w,h,oldw,oldh);
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    /**
     * 当view的onLayout之后才会调用
     */
    @Override
    public void onGlobalLayout() {
        for (int i=0; i<getChildCount();i++){
            View child = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
            if (layoutParams.getBehavior() != null){
                layoutParams.getBehavior().onLayoutFinish(this,child);
            }
        }
    }

    private float lastX;
    private float lastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = event.getRawX();
                lastY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                onTouchMove(event);
                break;
        }
        return super.onTouchEvent(event);
    }

    private void onTouchMove(MotionEvent event) {
        float moveX = event.getRawX();
        float moveY = event.getRawY();
        for (int i = 0;i < getChildCount(); i++){
            View child = getChildAt(i);
            LayoutParams param = (LayoutParams) child.getLayoutParams();
            if (param.getBehavior() != null){
                param.getBehavior().onTouchMove(this,child,event,moveX,moveY,lastX,lastY);
            }
        }
        lastY = moveY;
        lastX = moveX;
    }


    /**
     * 滚动事件
     * 返回true才会调用NestedScrolling滚动机制
     * @param child
     * @param target
     * @param nestedScrollAxes
     * @return
     */
    public boolean onStartNestedScroll(View child,View target, int nestedScrollAxes){
        return true;
    }

    @Override
    public void onStopNestedScroll(View child) {
        super.onStopNestedScroll(child);
    }

    /**
     * 最重要
     // 参数target:同上
     // 参数dxConsumed:表示target已经消费的x方向的距离
     // 参数dyConsumed:表示target已经消费的x方向的距离
     // 参数dxUnconsumed:表示x方向剩下的滑动距离
     // 参数dyUnconsumed:表示y方向剩下的滑动距离
     */
    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        for (int i = 0;i < getChildCount(); i++){
            View child = getChildAt(i);
            LayoutParams param = (LayoutParams) child.getLayoutParams();
            if (param.getBehavior() != null){
                param.getBehavior().onNestedScroll(target,child,dxConsumed,dyConsumed,dxUnconsumed,dyUnconsumed);
            }
        }
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        super.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return super.onNestedPreFling(target, velocityX, velocityY);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }

    public static class LayoutParams extends RelativeLayout.LayoutParams{

        private static final String TAG = "touch";
        private Behavior behavior;

        public LayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomCoordinatorLayout);
            behavior = parseBehavior(context,attrs,a.getString(R.styleable.CustomCoordinatorLayout_layout_behavior));
            a.recycle();
        }

        public Behavior getBehavior(){
            return behavior;
        }

        private Behavior parseBehavior(Context context,AttributeSet attrs,String name){
            if (TextUtils.isEmpty(name)){
                return null;
            }
            try{
                final String fullName;
                if (name.startsWith(".")){
                    fullName = context.getPackageName() + name;
                }else {
                    fullName = name;
                }
                final Class<Behavior> clazz = (Class<Behavior>) Class.forName(fullName,true,context.getClassLoader());
                Constructor<Behavior> constructor = clazz.getConstructor(new Class<?>[]{Context.class,AttributeSet.class});
                constructor.setAccessible(true);
                return constructor.newInstance(context,attrs);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        }

        public LayoutParams(int w,int h){
            super(w,h);
        }

        public LayoutParams(ViewGroup.LayoutParams source){ super(source);}
    }

}
