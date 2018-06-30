package com.example.luoling.android_dome.RecyclerViewDemo.SwipeCard;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.List;

/**
 * Created by LK on 2017/6/28.
 * 动脑学院 版权所有
 */

public class SwipeCardCallback extends ItemTouchHelper.SimpleCallback {

    private RecyclerView mRv;
    private RecyclerView.Adapter mAdapter;
    private List mDatas;

    public SwipeCardCallback(int dragDirs, int swipeDirs,
                             RecyclerView.Adapter adapter, List datas,
                             RecyclerView recyclerView) {
        super(0, ItemTouchHelper.DOWN | ItemTouchHelper.UP |
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT);
        mRv = recyclerView;
        mAdapter = adapter;
        mDatas = datas;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    // 在swipe 运动动画结束的时候调用
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Object object = mDatas.remove(viewHolder.getLayoutPosition());
        mDatas.add(0,object);
//        mAdapter.notifyDataSetChanged();
        mAdapter.notifyItemMoved(viewHolder.getLayoutPosition(), 0);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        double maxDistance = recyclerView.getWidth() * 0.5f;
        double distance = Math.sqrt(dX* dX + dY * dY);
        double fraction = distance / maxDistance;

        if (fraction > 1) {
            fraction = 1;
        }

        int itemCount = recyclerView.getChildCount();
        for (int i = 0; i < itemCount; i ++) {
            View view = recyclerView.getChildAt(i);

            int level = itemCount - i - 1;

            if (level >= 0) {
                if (level < CardConfig.MAX_SHOW_COUNT -1) {
                    view.setTranslationY((float) (CardConfig.TRANS_Y_GAP *level - fraction * CardConfig.TRANS_Y_GAP));
                    view.setScaleX((float) (1 - CardConfig.SCALE_GAP * level + fraction *CardConfig.SCALE_GAP));
                    view.setScaleY((float) (1 - CardConfig.SCALE_GAP * level + fraction *CardConfig.SCALE_GAP));
//                    view.setAlpha((float) (1 - 0.1 * level + fraction *CardConfig.SCALE_GAP));
                } else if (level == CardConfig.MAX_SHOW_COUNT -1){ // 控制的是最下层的Item
                    view.setTranslationY((float) (CardConfig.TRANS_Y_GAP *(level -1)));
                    view.setScaleX((float) (1 - CardConfig.SCALE_GAP * (level- 1)));
                    view.setScaleY((float) (1 - CardConfig.SCALE_GAP * (level -1)));
                }
            }
        }



        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public long getAnimationDuration(RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
        return 100;
    }

    @Override
    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        return 0.5f;
    }
}
