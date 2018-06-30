package com.example.luoling.android_dome.RecyclerViewDemo.SwipeCard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class UniversalAdapter<T> extends RecyclerView.Adapter<UniversalViewHolder> {

    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected ViewGroup mRv;
    private UniversalOnItemClickListener<T> mOnItemClickListener;

    public UniversalAdapter setOnItemClickListener(UniversalOnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
        return this;
    }

    public UniversalOnItemClickListener getOnItemClickListener(){
        return this.mOnItemClickListener;
    }

    public UniversalAdapter(Context context,List<T> datas,int layoutId){
        this.mContext = context;
        this.mLayoutId = layoutId;
        this.mDatas = datas;
    }

    @Override
    public UniversalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        UniversalViewHolder viewHolder = UniversalViewHolder.get(mContext,null,parent,mLayoutId);
        if (null == this.mRv){
            this.mRv = parent;
        }
        return viewHolder;
    }

    protected int getPosition(UniversalViewHolder viewHolder){
        return viewHolder.getAdapterPosition();
    }

    protected boolean isEnabled(int viewType){
        return true;
    }

    protected void setListener(final ViewGroup parent, final UniversalViewHolder viewholder, int viewType){
        if (this.isEnabled(viewType) && mOnItemClickListener != null){
            viewholder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getPosition(viewholder);
                    mOnItemClickListener.onItemClick(parent,v, mDatas.get(position),position);
                }
            });
            viewholder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getPosition(viewholder);
                    return mOnItemClickListener.onItemLongClick(parent,v,mDatas.get(position),position);
                }
            });
        }
    }

    protected void setListener(final int position, final UniversalViewHolder viewHolder){
        if (this.isEnabled(getItemViewType(position)) && mOnItemClickListener != null){
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(mRv,v,mDatas.get(position),position);
                }
            });
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return mOnItemClickListener.onItemLongClick(mRv,v,mDatas.get(position),position);
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(UniversalViewHolder holder, int position) {
        this.setListener(position,holder);
        convert(holder,mDatas.get(position));
    }

    public abstract void convert(UniversalViewHolder var, T var2);

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    public void setDatas(List<T> list) {
        if (mDatas != null){
            if (null != list){
                ArrayList<T> temp = new ArrayList<>();
                temp.addAll(list);
                mDatas.clear();
                mDatas.addAll(temp);
            }else{
                mDatas.clear();
            }
        } else {
            mDatas = list;
        }
        notifyDataSetChanged();
    }

    public void addDatas(List<T> list){
        if (null != list){
            ArrayList<T> temp = new ArrayList<>();
            temp.addAll(list);
            if (mDatas != null){
                mDatas.addAll(temp);
            }else{
                mDatas = temp;
            }
            notifyDataSetChanged();
        }
    }

    public List<T> getDatas(){
        return mDatas;
    }

    public T getItem(int position){
        return (position > -1 && null != mDatas && mDatas.size() > position) ? mDatas.get(position):null;
    }
}
