package com.example.luoling.android_dome.RecyclerViewDemo.dragSwipe;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luoling.android_dome.R;

import java.util.Collections;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyHolder> implements ItemTouchHelperAdapterCallback{

    private List<String> mList;
    private StartDragListener dragListener;

    public MessageAdapter(java.util.List<String> list, StartDragListener dragListener) {
        mList = list;
        this.dragListener = dragListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_list_item,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        holder.textView.setText(mList.get(position));
        holder.delete_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemSwiped(holder.getAdapterPosition());
            }
        });
        holder.update_action.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"refresh Click "+holder.getAdapterPosition(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        //让数据集合中的两个数据交换位置
        Collections.swap(mList,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
        return true;
    }

    @Override
    public void onItemSwiped(int adapterPosition) {
        //删除数据集合中的position位置的数据
        mList.remove(adapterPosition);
        //刷新adapter
//        notifyItemRemoved(adapterPosition);
        notifyDataSetChanged();
    }

    public interface StartDragListener{
        public void onStartDrag(RecyclerView.ViewHolder viewHolder);
    }

    class MyHolder extends RecyclerView.ViewHolder{
         TextView textView;
         FrameLayout swipeLayout;
         LinearLayout contentLayout;
         LinearLayout action_container;
         TextView delete_action,update_action;
        public MyHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv);
            contentLayout = itemView.findViewById(R.id.contentLayout);
            action_container = itemView.findViewById(R.id.action_container);
            delete_action = itemView.findViewById(R.id.delete_action);
            update_action = itemView.findViewById(R.id.update_action);
            swipeLayout = itemView.findViewById(R.id.swipeLayout);
//            itemView.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    dragListener.onStartDrag(MyHolder.this);
//                    return true;
//                }
//            });
        }
    }

}
