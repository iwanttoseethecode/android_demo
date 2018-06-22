package com.example.luoling.android_dome.viewPager3D;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.example.luoling.android_dome.R;

import java.util.Random;

public class WelcompagerTransformer implements ViewPager.PageTransformer {

    enum Effect{
        EFFECT1,EFFECT2,EFFECT3,EFFECT4
    }

    private Effect type = Effect.EFFECT1;

    public void setEffect(Effect type){
        this.type = type;
    }

    /**
     * 此方法是滑动的时候每一个页面View都会调用该方法
     * view:当前的页面
     * position:当前滑动的位置
     * 视差效果：在View正常滑动的情况下，给当前View或者当前view里面的每一个子view来一个加速度
     * 而且每一个加速度大小不一样。
     * -1~0
     * 0~1
     */
    @Override
    public void transformPage(View page, float position) {
        if (position < 1 && position >-1){
            //找到里面的子控件
            ViewGroup v = page.findViewById(R.id.rl);
            int childCount = v.getChildCount();
            for (int i = 0; i < childCount; i++){
                View childView = v.getChildAt(i);
                float factor = 1f;
                if (childView.getTag() == null){
                    Random random = new Random(1);
                    factor = random.nextFloat()*3;
                    childView.setTag(factor);
                }else{
                    factor = (float) childView.getTag();
                }
                /*
                * 每一个子控件达到不同的视差效果，translationX是不一样的
                * */
                childView.setTranslationX(factor * childView.getWidth() * position);
            }

            switch (type){
                case EFFECT1:
                    //效果1
                    page.setScaleX(1-Math.abs(position));
                    page.setScaleY(1-Math.abs(position));
                    break;
                case EFFECT2:
                    //效果2
                    page.setScaleX(Math.max(0.9f,1-Math.abs(position)));
                    page.setScaleY(Math.max(0.9f,1-Math.abs(position)));
                    break;
                case EFFECT3:
                    //效果3 3D翻转
//                    page.setPivotX(position < 0f ? page.getWidth() : 0);
//                    page.setPivotY(page.getHeight() * 0.5f);
                    page.setRotationY(-position * 45f);
                    page.setScaleX(Math.max(0.9f,1-Math.abs(position)));
                    page.setScaleY(Math.max(0.9f,1-Math.abs(position)));
                    break;
                case EFFECT4:
                    //效果4
                    page.setPivotX(page.getWidth()*0.5f);
                    page.setPivotY(page.getHeight()*0.5f);
                    page.setRotationY(-position*45f);
                    break;
            }
        }
    }


}
