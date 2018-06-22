package com.example.luoling.android_dome.advancedUI.tableLayout;

import android.content.Context;
import android.support.annotation.Px;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.example.luoling.android_dome.R;

import java.util.ArrayList;
import java.util.List;

public class TableLayout extends ViewGroup {

    private BaseTableAdapter adapter;
    private int currentX;
    private int currentY;
    private int scrollX;
    private int scrollY;
    /*
    * 第一行
    * */
    private int firstRow;
    /*
    * 第一列
    * */
    private int firstColumn;

    private int[] widths;
    private int[] heights;

    private View headView;
    private List<View> rowViewList;
    private List<View> columnViewList;
    private List<List<View>> bodyViewTable;
    private int rowCount;
    private int columnCount;
    private int width;
    private int height;

    private VelocityTracker velocityTracker;
    private Flinger flinger;
    private int minimumVelocity;
    private int maximumVelocity;

    private int touchSlop;

    private Recycler recycler;

    public TableLayout(Context context) {
        this(context,null);
    }

    public TableLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        ViewConfiguration configuration = ViewConfiguration.get(context);
        this.touchSlop = configuration.getScaledTouchSlop();
        minimumVelocity = configuration.getScaledMinimumFlingVelocity();
        maximumVelocity = configuration.getScaledMaximumFlingVelocity();
        rowViewList = new ArrayList<View>();
        columnViewList = new ArrayList<View>();
        bodyViewTable = new ArrayList<List<View>>();
    }

    public BaseTableAdapter getAdapter(){
        return adapter;
    }

    public void setAdapter(BaseTableAdapter adapter) {
        this.adapter = adapter;
        recycler = new Recycler(adapter.getViewTypeCount());
        scrollX = 0;
        scrollY = 0;
        firstColumn = 0;
        firstRow = 0;

        requestLayout();
    }

    private class Flinger implements Runnable{

        private Scroller scroller;
        private int lastX = 0;
        private int lastY = 0;

        Flinger(Context context){
            scroller = new Scroller(context);
        }

        public void start(int initX,int initY,int initialVelocityX,int initialVelocityY,int maxX,int maxY){
            scroller.fling(initX,initY,initialVelocityX,initialVelocityY,0,maxX,0,maxY);
            lastX = initX;
            lastY = initY;
            post(this);
        }

        @Override
        public void run() {

        }

        boolean isFinished(){
            return scroller.isFinished();
        }

        void forceFinished(){
            if (!scroller.isFinished()) {
                scroller.forceFinished(true);
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                currentX = (int) ev.getRawX();
                currentY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int x2 = (int) Math.abs(currentX - ev.getRawX());
                int y2 = (int) Math.abs(currentY - ev.getRawX());
                if (x2 > touchSlop || y2 > touchSlop){
                    intercept = true;
                }
                break;
        }
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (velocityTracker == null){
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (!flinger.isFinished()){
                    flinger.forceFinished();
                }
                currentX = (int) event.getRawX();
                currentY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int x2 = (int) event.getRawX();
                int y2 = (int) event.getRawY();
                int diffX = currentX - x2;
                int diffY = currentY - y2;
                scrollBy(diffX,diffY);
                break;
            case MotionEvent.ACTION_UP:
                velocityTracker.computeCurrentVelocity(1000,maximumVelocity);
                int velocityX = (int) velocityTracker.getXVelocity();
                int velocityY = (int) velocityTracker.getYVelocity();

                if (Math.abs(velocityX) > minimumVelocity || Math.abs(velocityY)>minimumVelocity){
                    flinger.start(getActualScrollX(),getActualScrollY(),velocityX,velocityY,getMaxScrollX(),getMaxScrollY());
                }else{
                    if (velocityTracker != null){
                        velocityTracker.recycle();
                        this.velocityTracker = null;
                    }
                }
                break;
        }
        return true;
    }

    private int getMaxScrollY() {
        return Math.max(0,sumArray(widths) - width);
    }

    private int getMaxScrollX() {
        return Math.max(0,sumArray(widths) - width);
    }

    private int getActualScrollX() {
        return scrollX + sumArray(widths,1,firstColumn);
    }

    private int getActualScrollY(){
        return scrollY + sumArray(heights,1,firstRow);
    }

    @Override
    public void scrollBy(@Px int x, @Px int y) {

        scrollX+=x;
        scrollY+=y;
        //修整ScrollX  ScrollY
        scrollBounds();
        //    int diffX=currentX-x2;
        if(scrollX==0){
            //如果 等于 什么都不做
        } else if (scrollX  > 0) {
            //手指 往左滑
            while (scrollX >widths[firstColumn+1]) {
                if (!rowViewList.isEmpty()) {
                    removeLeft();
                }
                //复位ScrollX
                scrollX -= widths[firstColumn + 1];
                //现在的第一列 被更新
                firstColumn++;
            }

            //找到右边添加一列   的临界条件
            while (getFilledWidth() < width) {
                addRight();
            }

        }else {
            //scrillX<0
            //手指往右滑
            //移除右边的Item的临界值

            while (!rowViewList.isEmpty()&&getFilledWidth() - widths[firstColumn + rowViewList.size()] >= width) {
                removeRight();
            }
            //添加左边的临界值
            while (scrollX < 0) {

                addLeft();
                //更新firstColumn
                firstColumn--;
                scrollX+=widths[firstColumn+1];
            }
        }

        if (scrollY == 0) {
            // no op
        } else if (scrollY > 0) {
            while (heights[firstRow + 1] < scrollY) {
                if (!columnViewList.isEmpty()) {
                    removeTop();
                }
                scrollY -= heights[firstRow + 1];
                firstRow++;
            }
            while (getFilledHeight() < height) {
                addBottom();
            }
        } else {
            while (!columnViewList.isEmpty() && getFilledHeight() - heights[firstRow + columnViewList.size()] >= height) {
                removeBottom();
            }
            while (0 > scrollY) {
                addTop();
                firstRow--;
                scrollY += heights[firstRow + 1];
            }
        }

        repositionViews();
    }

    private void removeBottom() {
        removeTopOrBottom(columnViewList.size() - 1);
    }

    private int getFilledHeight() {
        return heights[0] + sumArray(heights, firstRow + 1, columnViewList.size()) - scrollY;
    }

    private void addTop() {
        addTopAndBottom(firstRow - 1, 0);
    }

    private void addBottom() {
        final int size = columnViewList.size();
        addTopAndBottom(firstRow + size, size);
    }

    private void addTopAndBottom(int row, int index) {
        View view = obtainView(row, -1, widths[0], heights[row + 1]);
        columnViewList.add(index, view);

        List<View> list = new ArrayList<View>();
        final int size = rowViewList.size() + firstColumn;
        for (int i = firstColumn; i < size; i++) {
            view = obtainView(row, i, widths[i + 1], heights[row + 1]);
            list.add(view);
        }
        bodyViewTable.add(index, list);
    }

    private void removeTop() {
        removeTopOrBottom(0);
    }

    private void removeTopOrBottom(int position) {
        removeView(columnViewList.remove(position));
        List<View> remove = bodyViewTable.remove(position);
        for (View view : remove) {
            removeView(view);
        }
    }

    private void addLeft() {
        addLeftOrRight(firstColumn-1,0);
    }

    private void removeRight() {
        removeLeftOrRight(rowViewList.size()-1);
    }

    private void addRight() {
        int size=rowViewList.size();
        addLeftOrRight(firstColumn+size,size);
    }

    private void addLeftOrRight(int column, int index) {
        //添加首行  右边的View
        View view=obtainView(-1,column,widths[column+1],heights[0]);
        //更新 rowViewList
        rowViewList.add(index,view);
        int i=firstRow;

        for (List<View> list : bodyViewTable) {
            view = obtainView(i, column, widths[column + 1], heights[i + 1]);
            list.add(index, view);
            i++;
        }
    }

    private int getFilledWidth() {
        //往右滑时scrollX《0    -scrollX 为正
        return widths[0]+sumArray(widths,firstColumn+1,rowViewList.size())-scrollX;
    }

    private void removeLeft() {
        removeLeftOrRight(0);
    }

    private void removeLeftOrRight(int i) {
        //移除 View
        removeView(rowViewList.remove(i));

        //移除
        for (List<View> list : bodyViewTable) {
            removeView(list.remove(i));
        }
    }

    private void scrollBounds() {
        scrollX = scrollBounds(scrollX,firstColumn,widths,width);
        scrollY = scrollBounds(scrollY,firstRow,heights,height);
    }

    private int scrollBounds(int desiredScroll,int firstCell,int sizes[],int viewSize){
        if (desiredScroll == 0){

        }else if (desiredScroll < 0){
            desiredScroll = Math.max(desiredScroll,-sumArray(sizes,1,firstCell));
        }else{
            desiredScroll = Math.max(desiredScroll,Math.max(0,sumArray(sizes,firstCell+1,sizes.length - 1 - firstCell) + sizes[0] - viewSize));
        }
        return desiredScroll;
    }

    @Override
    public void removeView(View view) {
        super.removeView(view);
        final int typeView = (int) view.getTag(R.id.tag_type_view);
        recycler.addRecyclerView(view,typeView);
    }

    private void repositionViews() {
        int left, top, right, bottom, i;

        left = widths[0] - scrollX;
        i = firstColumn;
        for (View view : rowViewList) {
            right = left + widths[++i];
            view.layout(left, 0, right, heights[0]);
            left = right;
        }

        top = heights[0] - scrollY;
        i = firstRow;
        for (View view : columnViewList) {
            bottom = top + heights[++i];
            view.layout(0, top, widths[0], bottom);
            top = bottom;
        }

        top = heights[0] - scrollY;
        i = firstRow;
        for (List<View> list : bodyViewTable) {
            bottom = top + heights[++i];
            left = widths[0] - scrollX;
            int j = firstColumn;
            for (View view : list) {
                right = left + widths[++j];
                view.layout(left, top, right, bottom);
                left = right;
            }
            top = bottom;
        }
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(widthMeasureSpec);

        int w = 0;
        int h = 0;
        if (adapter != null){
            rowCount = adapter.getRowCount();
            columnCount = adapter.getColmunCount();

            widths = new int[columnCount];
            for (int i = 0;i<columnCount;i++){
                widths[i] = adapter.getWidth(i);
            }
            heights = new int[rowCount];
            for (int i = 0; i < rowCount;i++){
                heights[i] = adapter.getHeight(i);
            }

            if (widthMode == MeasureSpec.AT_MOST){
                w = Math.min(widthSize,sumArray(widths));
            }else{
                w = widthSize;
            }

            if (heightMode == MeasureSpec.AT_MOST){
                h = Math.min(heightSize,sumArray(heights));
            }else{
                h = heightSize;
            }
        }
        setMeasuredDimension(w,h);
    }

    private int sumArray(int[] array) {
        return sumArray(array,0,array.length);
    }

    private int sumArray(int array[],int start,int end){
        int sum = 0;
        for (int i = start; i < end ; i++){
            sum += array[i];
        }
        return sum;
    }

    private void resetTable(){
        headView = null;
        rowViewList.clear();
        columnViewList.clear();
        bodyViewTable.clear();
        removeAllViews();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (adapter == null){
            throw new RuntimeException("adapter is null");
        }
        if (changed){
            resetTable();
        }
        width = r - l;
        height = b - t;
        int left, top,right,bottom;

        //绘制第一个view
        headView = makeAndStep(0,0,0,0,widths[0],heights[0]);

        left = widths[0] - scrollX;
        //填充第一行
        for (int i = firstColumn; i < columnCount && left < width;i++){
            right = left + widths[i];
            View view = makeAndStep(0,i,left,0,right,heights[0]);
            rowViewList.add(view);
            left = right;
        }

        top = heights[0] - scrollY;
        for (int i = firstRow; i < rowCount && top < height;i++){
            bottom = top + heights[i];
            View view = makeAndStep(i,0,0,top,widths[0],bottom);
            columnViewList.add(view);
            top = bottom;
        }

        top = heights[0] -scrollY;
        for (int i = firstRow;i < rowCount && top < height; i++){
            bottom = top + heights[i];
            left = widths[0] - scrollX;
            List<View> list = new ArrayList<>();
            for (int j = firstColumn; j < columnCount && left < width; j++){
                right = left + widths[j];
                View view = makeAndStep(i,j,left,top,right,bottom);
                list.add(view);
                left = right;
            }
            bodyViewTable.add(list);
            top = bottom;
        }
    }

    private View makeAndStep(int row ,int column, int left,int top,int right,int bottom){
        View view = obtainView(row,column,right - left,bottom - top);
        view.layout(left,top,right,bottom);
        return view;
    }

    private View obtainView(int row,int column,int width,int height){
        //获取当前控件的类型
        int itemType = adapter.getItemViewType(row,column);
        //从回收池拿到一个View
        View recyclerView = recycler.getRecyclerView(itemType);
        View view = adapter.getView(row,column,recyclerView,this);
        if (view == null){
            throw new RuntimeException("view 不能为空");
        }
        view.setTag(R.id.tag_type_view,itemType);
        view.setTag(R.id.tag_row,row);
        view.setTag(R.id.tag_column,column);
        view.measure(MeasureSpec.makeMeasureSpec(width,MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY));
        addView(view);
        return view;
    }

    /*
     * The expected value is: percentageOfViewScrolled * computeHorizontalScrollRange()
     */
    @Override
    protected int computeHorizontalScrollExtent() {
        final float tableSize = width - widths[0];
        final float contentSize = sumArray(widths) - widths[0];
        final float percentageOfVisibleView = tableSize / contentSize;

        return Math.round(percentageOfVisibleView * tableSize);
    }

    /*
     * The expected value is between 0 and computeHorizontalScrollRange() - computeHorizontalScrollExtent()
     */
    @Override
    protected int computeHorizontalScrollOffset() {
        final float maxScrollX = sumArray(widths) - width;
        final float percentageOfViewScrolled = getActualScrollX() / maxScrollX;
        final int maxHorizontalScrollOffset = width - widths[0] - computeHorizontalScrollExtent();

        return widths[0] + Math.round(percentageOfViewScrolled * maxHorizontalScrollOffset);
    }

    /*
     * The base measure
     */
    @Override
    protected int computeHorizontalScrollRange() {
        return width;
    }

    /*
     * The expected value is: percentageOfViewScrolled * computeVerticalScrollRange()
     */
    @Override
    protected int computeVerticalScrollExtent() {
        final float tableSize = height - heights[0];
        final float contentSize = sumArray(heights) - heights[0];
        final float percentageOfVisibleView = tableSize / contentSize;

        return Math.round(percentageOfVisibleView * tableSize);
    }

    /*
     * The expected value is between 0 and computeVerticalScrollRange() - computeVerticalScrollExtent()
     */
    @Override
    protected int computeVerticalScrollOffset() {
        final float maxScrollY = sumArray(heights) - height;
        final float percentageOfViewScrolled = getActualScrollY() / maxScrollY;
        final int maxHorizontalScrollOffset = height - heights[0] - computeVerticalScrollExtent();

        return heights[0] + Math.round(percentageOfViewScrolled * maxHorizontalScrollOffset);
    }

    /*
     * The base measure
     */
    @Override
    protected int computeVerticalScrollRange() {
        return height;
    }
}
