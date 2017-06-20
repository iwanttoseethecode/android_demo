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
        int widthMeasureSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMeasureSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMeasureSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMeasureSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        //最终测量的宽度
        int measureWidth = 0;
        //最终测量的高度
        int measureHeight = 0;
        //某一行的宽度
        int lineWidth = 0;
        //某一行高度
        int lineheight = 0;

        //每一行的View集合
        List<View> viewList = new ArrayList<>();

        int childCount = getChildCount();
        for (int i = 0;i<childCount;i++){
            View childView = getChildAt(i);
            measureChild(childView,widthMeasureSpec,heightMeasureSpec);
            MarginLayoutParams lp= (MarginLayoutParams) childView.getLayoutParams();
            int childWidth = childView.getMeasuredWidth()+lp.leftMargin+lp.rightMargin;
            int childHeight = childView.getMeasuredHeight()+lp.topMargin+lp.bottomMargin;

            if (lineWidth+childWidth>widthMeasureSpecSize){

                measureWidth = Math.max(lineWidth,measureWidth);
                measureHeight += lineheight;

                heightRowList.add(lineheight);
                flowLayoutList.add(viewList);

                viewList = new ArrayList<>();


                lineWidth = childWidth;
                lineheight = childHeight;

                viewList.add(childView);

            }else{

                viewList.add(childView);


                lineWidth += childWidth;
                lineheight = Math.max(lineheight,childHeight);
            }

            if (i == childCount-1){

                heightRowList.add(lineheight);
                flowLayoutList.add(viewList);

                measureWidth = Math.max(lineWidth,measureWidth);
                measureHeight += lineheight;
            }
        }

        if(widthMeasureSpecMode == MeasureSpec.EXACTLY){
            measureWidth = widthMeasureSpecSize;
        }
        
        if (heightMeasureSpecMode == MeasureSpec.EXACTLY){
            measureHeight = heightMeasureSpecSize;
        }

        setMeasuredDimension(measureWidth,measureHeight);
    }



    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = 0,top = 0,right = 0,buttom = 0;
        int curLeft = 0,curTop = 0;
        int lines = flowLayoutList.size();
        for (int i = 0;i<lines;i++){
            List<View> viewList = flowLayoutList.get(i);
            int lineViewSize = viewList.size();

            for (int j = 0; j<lineViewSize;j++){
                View childView = viewList.get(j);
                MarginLayoutParams lp= (MarginLayoutParams) childView.getLayoutParams();
                left = curLeft+lp.leftMargin;
                top = curTop+lp.topMargin;
                right = left + childView.getMeasuredWidth();
                buttom = top + childView.getMeasuredHeight();
                childView.layout(left,top,right,buttom);
                curLeft = right+lp.rightMargin;
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
