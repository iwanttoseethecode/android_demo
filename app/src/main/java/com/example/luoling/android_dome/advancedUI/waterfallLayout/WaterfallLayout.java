package com.example.luoling.android_dome.advancedUI.waterfallLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zh on 2017/6/20.
 */

public class WaterfallLayout extends ViewGroup {

    private int mColumns = 3;
    private int mHorizontalSpace = 20;
    private int mVerticalSpace = 20;
    private int mChildWidth = 0;
    private int mTop[];


    public WaterfallLayout(Context context) {
        this(context,null);
    }

    public WaterfallLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WaterfallLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTop = new int[mColumns];
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMeasureSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMeasureSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMeasureSpecSize =MeasureSpec.getSize(heightMeasureSpec);
        int heightMeasureSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        int measureWidth = 0;
        int measureHeight = 0;

        measureChildren(widthMeasureSpec,heightMeasureSpec);

        mChildWidth = (widthMeasureSpecSize - (mColumns-1)*mHorizontalSpace)/mColumns;

        int childCount = getChildCount();
        if (childCount < mColumns){
            measureWidth = mChildWidth * childCount + (childCount-1)*mHorizontalSpace;
        }else{
            measureWidth = widthMeasureSpecSize;
        }

        clearTop();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            int childHeight = child.getMeasuredHeight() * mChildWidth / child.getMeasuredWidth();

            int minColumn = minHeightColumn();
            WaterfallLayoutParams wp = (WaterfallLayoutParams) child.getLayoutParams();
            wp.left = minColumn *(mChildWidth+mHorizontalSpace);
            wp.top = mTop[minColumn];
            wp.right = wp.left + mChildWidth;
            wp.bottom = wp.top + childHeight;

            mTop[minColumn] += mVerticalSpace + childHeight;
        }
        measureHeight = maxHeight();

        if (widthMeasureSpecMode == MeasureSpec.EXACTLY && heightMeasureSpecMode == MeasureSpec.EXACTLY){
            measureWidth = widthMeasureSpecSize;
            measureHeight = heightMeasureSpecSize;
        }

        setMeasuredDimension(measureWidth,measureHeight);
    }

    private int minHeightColumn(){
        int minColumns = 0;
        int minHeight = mTop[0];
        for (int i = 0; i < mColumns; i++) {
            if (minHeight > mTop[i]){
                minColumns=i;
            }
        }
        return minColumns;
    }

    private int maxHeight(){
        int maxHeight = 0;
        for (int i = 0; i < mColumns;i++){
            if (maxHeight < mTop[i]){
                maxHeight = mTop[i];
            }
        }
        return maxHeight;
    }

    private void clearTop(){
        int length = mTop.length;
        for (int i = 0; i < length; i++) {
            mTop[i] = 0;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        clearTop();
        for (int i = 0; i<childCount;i++){
            View child = getChildAt(i);
            WaterfallLayoutParams wp= (WaterfallLayoutParams) child.getLayoutParams();
            child.layout(wp.left,wp.top,wp.right,wp.bottom);
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new WaterfallLayoutParams(WaterfallLayoutParams.WRAP_CONTENT, WaterfallLayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return super.generateLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return super.generateLayoutParams(attrs);
    }

    public interface OnItemClickListener{
        /**
         * @param v 点击的view
         * @param index 点击的view的索引号
         */
        void ItemClick(View v,int index);
    }

    public void setOnItemClickListener(final OnItemClickListener listener){
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = getChildAt(i);
            final int  finalI = i;
            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.ItemClick(v,finalI);
                }
            });
        }
    }

    static class WaterfallLayoutParams extends MarginLayoutParams{

        public int left = 0;
        public int top = 0;
        public int right = 0;
        public int bottom = 0;

        public WaterfallLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public WaterfallLayoutParams( int width, int height) {
            super(width, height);
        }

        public WaterfallLayoutParams(MarginLayoutParams source) {
            super(source);
        }
    }
}
