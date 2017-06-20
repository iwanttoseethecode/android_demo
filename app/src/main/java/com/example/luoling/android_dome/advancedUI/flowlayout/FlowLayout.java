package com.example.luoling.android_dome.advancedUI.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luoling on 2017/6/20.
 */

public class FlowLayout extends ViewGroup {
    //保存每一行的View
    private List<List<View>> flowLayoutList = new ArrayList<>();
    //保存每行的最大高度
    private List<Integer> heightRowList = new ArrayList<>();

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(this.getContext(),attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);

        //本控件的最终测量宽度
        int flowlayoutWidth = 0;
        //本控件的最终测量高度
        int flowlayoutHeight = 0;

        if (widthMeasureMode == MeasureSpec.EXACTLY && heightMeasureMode == MeasureSpec.EXACTLY){
            flowlayoutWidth = widthMeasureSize;
            flowlayoutHeight = heightMeasureSize;
        }else{
            //记录每一行的最大宽度
            int lineWidth = 0;
            //记录每一行的最大高度
            int maxLineHeight = 0;
            int childrenCount = getChildCount();

            //存放某一行的View
            List<View> lineViewList = new ArrayList<>();

            for (int i = 0; i < childrenCount; i++) {
                View child = getChildAt(i);
                measureChild(child,widthMeasureSpec,heightMeasureSpec);

                MarginLayoutParams lp= (MarginLayoutParams) child.getLayoutParams();
                int childWidth = child.getMeasuredWidth()+lp.leftMargin+lp.rightMargin;
                int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

                if (lineWidth + childWidth > widthMeasureSize){//这一行加上当前view的宽度已经大于容器的最大宽度

                    flowlayoutWidth = Math.max(lineWidth,flowlayoutWidth);
                    flowlayoutHeight += maxLineHeight;

                    flowLayoutList.add(lineViewList);

                    lineViewList = new ArrayList<>();
                    lineViewList.add(child);

                    heightRowList.add(maxLineHeight);

                    lineWidth = childWidth;
                    maxLineHeight = childHeight;

                }else{
                    lineWidth += childWidth;
                    maxLineHeight = Math.max(maxLineHeight,childHeight);

                    lineViewList.add(child);

                }

                if (i == childrenCount-1){

                    flowlayoutWidth = Math.max(lineWidth,flowlayoutWidth);
                    flowlayoutHeight += maxLineHeight;

                    flowLayoutList.add(lineViewList);
                    heightRowList.add(maxLineHeight);
                }
            }



        }

        setMeasuredDimension(flowlayoutWidth,flowlayoutHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int left=0,top=0,right=0,bottom=0;
        int curLeft=0;
        int curTop=0;

        int lineCount = flowLayoutList.size();

        for (int i = 0; i < lineCount; i++) {
            List<View> lineViewList = flowLayoutList.get(i);

            int count = lineViewList.size();
            for (int j=0;j<count;j++){
                View childView = lineViewList.get(j);
                MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();

                left = curLeft + layoutParams.leftMargin;
                top = curTop + layoutParams.topMargin;
                right = left + childView.getMeasuredWidth();
                bottom = top + childView.getMeasuredHeight();
                childView.layout(left,top,right,bottom);
                curLeft += childView.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            }
            curLeft = 0;
            curTop += heightRowList.get(i);
        }
        flowLayoutList.clear();
        heightRowList.clear();
    }

    public interface OnItemClickListener{
        /**
         * @param v 点击的view
         * @param index 点击view的索引值
         */
        void ItemClick(View v,int index);
    }

    public void setOnItemClickListener(final OnItemClickListener listener){
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View v = getChildAt(i);
            final int index = i;

            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.ItemClick(v,index);
                }
            });
        }
    }
}
