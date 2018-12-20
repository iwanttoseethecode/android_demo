package com.example.luoling.android_dome.customCoordinator;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/12/19.
 */

public class ToolBarBehavior extends Behavior{

    private int maxHeight = 400;

    public ToolBarBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public void onNestedScroll(View scrollView, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(scrollView, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (scrollView.getScrollY() <= maxHeight){
            ViewCompat.setAlpha(target,scrollView.getScrollY()*1.0f/maxHeight);
        }else if (scrollView.getScrollY() <= 0){
            ViewCompat.setAlpha(target,0);
        }
    }
}
