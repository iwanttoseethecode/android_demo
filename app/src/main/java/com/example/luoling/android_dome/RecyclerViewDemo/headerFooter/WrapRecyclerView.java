package com.example.luoling.android_dome.RecyclerViewDemo.headerFooter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class WrapRecyclerView extends RecyclerView {
    public WrapRecyclerView(Context context) {
        super(context);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private ArrayList<View> mHeaderViewInfos = new ArrayList<>();
    private ArrayList<View> mFooterViewInfos = new ArrayList<>();
    private Adapter mAdapter;

    public void addHeaderView(View v){
        mHeaderViewInfos.add(v);

        if (mAdapter != null){
            if (mAdapter instanceof WrapRecyclerAdapter){
                mAdapter = new WrapRecyclerAdapter(mHeaderViewInfos,mFooterViewInfos,mAdapter);
            }
        }
    }

    public void addFooterView(View v){
        mFooterViewInfos.add(v);
        if (mAdapter != null){
            if (mAdapter instanceof WrapRecyclerAdapter){
                mAdapter = new WrapRecyclerAdapter(mHeaderViewInfos,mFooterViewInfos,mAdapter);
            }
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (mHeaderViewInfos.size() > 0 || mFooterViewInfos.size() > 0){
            mAdapter = new WrapRecyclerAdapter(mHeaderViewInfos,mFooterViewInfos,adapter);
        }else{
            mAdapter = adapter;
        }
        super.setAdapter(mAdapter);
    }
}
