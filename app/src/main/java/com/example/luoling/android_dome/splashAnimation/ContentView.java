package com.example.luoling.android_dome.splashAnimation;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.example.luoling.android_dome.R;

public class ContentView extends android.support.v7.widget.AppCompatImageView {
    public ContentView(Context context) {
        super(context);
        setImageResource(R.mipmap.content);
    }

    public ContentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setImageResource(R.mipmap.content);
    }

    public ContentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setImageResource(R.mipmap.content);
    }

}
