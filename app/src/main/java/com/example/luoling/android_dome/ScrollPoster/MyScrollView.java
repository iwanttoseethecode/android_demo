package com.example.luoling.android_dome.ScrollPoster;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {

    private MyLinearLayout mContent;

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() { //当布局被引入完成之后回调
        super.onFinishInflate();
        View view = null;
        for (int i = 0 ; i < getChildCount(); i++){
            view = getChildAt(i);
            if (view instanceof MyLinearLayout){
                mContent = (MyLinearLayout) view;
                break;
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        int scrollViewHeight = getHeight();
        //监听滑动的程度---childView从下面冒出来多少距离/childView.getHeight();----0~1：动画执行的百分比ratio
        //动画执行的百分比ratio控制动画执行
        for (int i=0;i<mContent.getChildCount();i++){
            View child = mContent.getChildAt(i);//MyFrameLayout
            int childHeight = child.getHeight();
            if(!(child instanceof ScrollAnimationInterface)){
                continue;
            }
            //接口回掉，传递执行的百分比给MyFrameLayout
            //低耦合高内聚
//            MyFrameLayout mf = (MyFrameLayout) child;
            ScrollAnimationInterface scrollAnimationInterface = (ScrollAnimationInterface) child;
            //child离parent顶部的高度
            int childTop = child.getTop();
            //滑出去的这一截高度：t
//            child离屏幕顶部的高度
            int absoluteTop = childTop -t;
            if(absoluteTop<=scrollViewHeight) {
                //child浮现的高度 = ScrollView的高度 - child离屏幕顶部的高度
                int visibleGap = scrollViewHeight - absoluteTop;
                //float ratio = child浮现的高度/child的高度
                float ratio = visibleGap / (float) childHeight;
                //确保ratio是在0~1的范围
                scrollAnimationInterface.onScrollAnimation(clamp(ratio,1f,0f));
            }else{
                scrollAnimationInterface.onResetAnimation();
            }
        }
    }

    /**
     *
     * 求这个三个数中的中间数
     * @param value
     * @param max
     * @param min
     * @return
     */
    public  float clamp(float value,float max,float min){
        return Math.min(Math.max(value,min),max);
    }
}
