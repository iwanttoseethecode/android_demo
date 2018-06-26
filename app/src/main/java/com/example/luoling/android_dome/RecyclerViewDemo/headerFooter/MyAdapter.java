package com.example.luoling.android_dome.RecyclerViewDemo.headerFooter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.luoling.android_dome.R;

import java.util.List;

public class MyAdapter extends Adapter<MyAdapter.ViewHolder> {
    private List<String> listData;

    public MyAdapter(List<String> list){
        listData = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_listitem, parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv);
        }
    }
}
