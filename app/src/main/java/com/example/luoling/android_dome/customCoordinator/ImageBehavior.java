package com.example.luoling.android_dome.customCoordinator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2018/12/19.
 */

public class ImageBehavior extends Behavior {

    private static final String TAG = "tuch";

    public ImageBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private int maxHeight = 600;
    private int originHeight;

    @Override
    public void onLayoutFinish(View parent, View child) {
        super.onLayoutFinish(parent, child);
        if (originHeight == 0){
            originHeight = child.getHeight();
        }
    }

    @Override
    public void onNestedScroll(View scrollView, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(scrollView, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (scrollView.getScrollY() > 0){
            ViewGroup.LayoutParams params = target.getLayoutParams();
            params.height = params.height - Math.abs(dyConsumed);
            if (params.height < originHeight){
                params.height = originHeight;
            }
            target.setLayoutParams(params);
        }else if (scrollView.getScrollY() <= 0){
            ViewGroup.LayoutParams params = target.getLayoutParams();
            params.height = params.height + Math.abs(dyUnconsumed);
            if (params.height >= maxHeight){
                params.height = maxHeight;
            }
            target.setLayoutParams(params);
        }
    }
}
