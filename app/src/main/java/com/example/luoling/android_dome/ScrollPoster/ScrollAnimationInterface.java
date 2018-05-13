package com.example.luoling.android_dome.ScrollPoster;

public interface ScrollAnimationInterface {

    /**
     * 滑动的时候调用该方法，用来控制里面的控件执行相应动画
     * @param ratio 动画执行的百分比 0~1
     */
    public void onScrollAnimation(float ratio);

    /**
     * 重置动画，让view所有的属性都恢复到原来的样子
     */
    public void onResetAnimation();
}
