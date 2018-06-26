package com.example.luoling.android_dome.RecyclerViewDemo.dragSwipe;

public interface ItemTouchHelperAdapterCallback {

    /**
     * 当拖拽的时候回调
     * @param fromPosition
     * @param toPosition
     * @return
     */
    boolean onItemMove(int fromPosition, int toPosition);

    /**
     * 当侧滑的时候回调
     * @param adapterPosition
     * @return
     */
    void onItemSwiped(int adapterPosition);
}
