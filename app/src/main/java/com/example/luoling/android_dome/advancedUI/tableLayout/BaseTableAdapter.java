package com.example.luoling.android_dome.advancedUI.tableLayout;

import android.view.View;
import android.view.ViewGroup;

public interface BaseTableAdapter {
    int getRowCount();
    int getColmunCount();
    int getWidth(int colmun);
    int getHeight(int row);
    int getItemViewType(int row,int colmun);
    int getViewTypeCount();
    View getView(int row, int colmun, View convertView, ViewGroup parent);
}
