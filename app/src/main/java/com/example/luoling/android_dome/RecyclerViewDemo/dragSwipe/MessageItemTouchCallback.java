package com.example.luoling.android_dome.RecyclerViewDemo.dragSwipe;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.luoling.android_dome.RecyclerViewDemo.dragSwipe.modifySource.ItemTouchHelper;


public class MessageItemTouchCallback extends ItemTouchHelper.Callback {

    private ItemTouchHelperAdapterCallback adapterCallback;

    public MessageItemTouchCallback(ItemTouchHelperAdapterCallback adapterCallback) {
        this.adapterCallback = adapterCallback;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //callback回调监听哪些动作

        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlags,swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        adapterCallback.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//        adapterCallback.onItemSwiped(viewHolder.getAdapterPosition());
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    //返回值决定是否有滑动操作
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if (dY != 0 && dX == 0){
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        MessageAdapter.MyHolder myHolder = (MessageAdapter.MyHolder) viewHolder;

        if (dX < -myHolder.action_container.getWidth()){
            dX = -myHolder.action_container.getWidth();
        }else if (dX > myHolder.action_container.getWidth()){
            dX = myHolder.action_container.getWidth();
        }

        if (myHolder.contentLayout.getX()>=0 && dX>0){
            dX = 0;
        }

        if (myHolder.contentLayout.getTranslationX()<0){
            myHolder.action_container.setVisibility(View.VISIBLE);
        }else {
            myHolder.action_container.setVisibility(View.INVISIBLE);
        }

        myHolder.contentLayout.setTranslationX(dX);

    }

    //滑动生效的距离设置，值是0—1f,1f代表item的完整高度或者宽度。
    @Override
    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        return 0.5f;
    }

    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
        return super.getSwipeEscapeVelocity(defaultValue);
    }

    //设置手指离开后ViewHolder的动画时间
    @Override
    public long getAnimationDuration(RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
        return 200;
    }

    @Override
    public float getMoveThreshold(RecyclerView.ViewHolder viewHolder) {
        return super.getMoveThreshold(viewHolder);
    }
}
