package com.example.luoling.android_dome.advancedUI.tableLayout;

import android.view.View;

import java.util.Stack;

public class Recycler {
    Stack<View>[] stack;

    public Recycler(int viewType){
        stack = new Stack[viewType];

        for (int i=0;i<viewType;i++){
            stack[i] = new Stack<View>();
        }
    }

    public void addRecyclerView(View view,int viewType){
        stack[viewType].push(view);
    }

    public View getRecyclerView(int viewType){
        return stack[viewType].pop();
    }
}
