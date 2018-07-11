package com.example.luoling.android_dome.fabBehavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 2017/7/12.
 */

public class MyBeahvior extends CoordinatorLayout.Behavior{

    private static final String TAG = "tuch";

    /**
     * 写构造方法   不写奔溃
     * @param context
     * @param set
     */
    public MyBeahvior(Context context, AttributeSet set) {
        super(context,set);
    }




    /**
     * CoordinatorLayout  调用此方法
     *
     * View child      TextView
     *
     *  CoordinatorLayout遍历所有的子控件
     *调用多次
     *
     * boolean   告诉父 容器  我是否需要监听它
     *   dependency   Button
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        Log.i(TAG, "layoutDependsOn: "+child+"  dependency  "+ dependency);
        return dependency instanceof Button;
    }

    /**
     *
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        Log.i(TAG, "onDependentViewChanged: "+dependency.getX()+dependency.getWidth());
        child.setX(dependency.getX());
        child.setY(dependency.getY()+dependency.getHeight());
        return  true;
    }
}
