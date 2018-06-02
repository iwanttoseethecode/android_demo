package com.example.luoling.android_dome.drawble;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GallaryHorizonalScrollView extends HorizontalScrollView{

    private LinearLayout container;
    private int centerX;
    private int icon_width;

    public GallaryHorizonalScrollView(Context context) {
        super(context);
        init();
    }

    public GallaryHorizonalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        container = new LinearLayout(getContext());
        container.setLayoutParams(params);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        View v = container.getChildAt(0);
        icon_width = v.getWidth();
        centerX = getWidth()/2;
        //中心图片的左边界
        centerX = centerX - icon_width/2;
        container.setPadding(centerX,0,centerX,0);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        reveal();
    }

    private void reveal() {
        int scrollX = getScrollX();
        int index_left = scrollX/icon_width;
        int index_right = index_left + 1;

        for (int i = 0;i < container.getChildCount(); i++){
            if (i == index_left || i==index_right){
                float ratio = 5000f/icon_width;
                ImageView iv_left = (ImageView) container.getChildAt(index_left);
                iv_left.setImageLevel((int) (5000 - scrollX%icon_width*ratio));

                if (index_right<container.getChildCount()){
                    ImageView iv_right = (ImageView) container.getChildAt(index_right);
                    iv_right.setImageLevel((int) (10000 - scrollX%icon_width*ratio));
                }
            }else{
                ImageView iv = (ImageView) container.getChildAt(i);
                iv.setImageLevel(0);
            }
        }
    }

    public void addImageViews(Drawable[] revealDrawables){
        for (int i=0;i<revealDrawables.length;i++){
            ImageView img = new ImageView(getContext());
            img.setImageDrawable(revealDrawables[i]);
            container.addView(img);
            if (i==0){
                img.setImageLevel(5000);
            }
        }
        addView(container);
    }

}
