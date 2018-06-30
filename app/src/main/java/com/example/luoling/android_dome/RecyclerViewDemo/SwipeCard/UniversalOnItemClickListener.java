package com.example.luoling.android_dome.RecyclerViewDemo.SwipeCard;

import android.view.View;
import android.view.ViewGroup;

public interface UniversalOnItemClickListener<T> {
    void onItemClick(ViewGroup var1, View var2, T var3, int var4);

    boolean onItemLongClick(ViewGroup var1,View var2, T var3,int var4);
}
