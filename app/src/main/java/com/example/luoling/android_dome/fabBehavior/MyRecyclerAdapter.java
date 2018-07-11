package com.example.luoling.android_dome.fabBehavior;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> list;

    public MyRecyclerAdapter(List<String> list){
        this.list = list;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(android.R.layout.simple_list_item_1,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String string = list.get(position);
        MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.tv.setText(string);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(android.R.id.text1);
        }
    }
}
