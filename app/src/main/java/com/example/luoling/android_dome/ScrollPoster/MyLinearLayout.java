package com.example.luoling.android_dome.ScrollPoster;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.luoling.android_dome.R;

public class MyLinearLayout extends LinearLayout {
    public MyLinearLayout(Context context) {
        super(context);
        setOrientation(VERTICAL);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }

    public class MyLayoutParams extends LinearLayout.LayoutParams{

        public int mDiscrollveFromBgColor;
        public int mDiscrollveToBgColor;
        public boolean mDiscrollveAlpha;
        public int mDiscrollveTranslation;
        public boolean mDiscrollveScaleX;
        public boolean mDiscrollveScaleY;

        public MyLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.DiscrollView_LayoutParams);
            mDiscrollveAlpha = a.getBoolean(R.styleable.DiscrollView_LayoutParams_discrollve_alpha,false);
            mDiscrollveScaleX = a.getBoolean(R.styleable.DiscrollView_LayoutParams_discrollve_scaleX,false);
            mDiscrollveScaleY = a.getBoolean(R.styleable.DiscrollView_LayoutParams_discrollve_scaleY,false);
            mDiscrollveFromBgColor = a.getColor(R.styleable.DiscrollView_LayoutParams_discrollve_fromBgColor,-1);
            mDiscrollveToBgColor = a.getColor(R.styleable.DiscrollView_LayoutParams_discrollve_toBgColor,-1);
            mDiscrollveTranslation = a.getInt(R.styleable.DiscrollView_LayoutParams_discrollve_translation,-1);
            a.recycle();
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MyLayoutParams(getContext(),attrs);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        MyLayoutParams p = (MyLayoutParams) params;
        if (!isDiscrollvable(p)){
            super.addView(child, params);
        }else{
            MyFrameLayout myFrameLayout = new MyFrameLayout(getContext());
            myFrameLayout.addView(child);
            myFrameLayout.setmDiscrollveAlpha(p.mDiscrollveAlpha);
            myFrameLayout.setmDiscrollveFromBgColor(p.mDiscrollveFromBgColor);
            myFrameLayout.setmDiscrollveToBgColor(p.mDiscrollveToBgColor);
            myFrameLayout.setmDiscrollveScaleX(p.mDiscrollveScaleX);
            myFrameLayout.setmDiscrollveScaleY(p.mDiscrollveScaleY);
            myFrameLayout.setmDiscrollveTranslation(p.mDiscrollveTranslation);
            super.addView(myFrameLayout,params);
        }
    }

    private boolean isDiscrollvable(MyLayoutParams p){
        return p.mDiscrollveAlpha || p.mDiscrollveScaleX || p.mDiscrollveScaleY || p.mDiscrollveTranslation!=-1 || (p.mDiscrollveFromBgColor != -1 && p.mDiscrollveToBgColor != -1);
    }
}
