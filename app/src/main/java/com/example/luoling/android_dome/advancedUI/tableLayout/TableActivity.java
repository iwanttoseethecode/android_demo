package com.example.luoling.android_dome.advancedUI.tableLayout;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.luoling.android_dome.R;
import com.example.luoling.android_dome.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TableActivity extends AppCompatActivity {

    @BindView(R.id.table)
    TableLayout table;
    int row = 100;
    int column = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        ButterKnife.bind(this);
        table.setAdapter(new MyAdapter(TableActivity.this));
    }

    class MyAdapter implements BaseTableAdapter{
        LayoutInflater inflater;
        private int width;
        private int height;

        public MyAdapter(Context context){
            Resources resources = context.getResources();
            width = resources.getDimensionPixelSize(R.dimen.tableLayout_width);
            height = resources.getDimensionPixelSize(R.dimen.tableLayout_height);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getRowCount() {
            return row;
        }

        @Override
        public int getColmunCount() {
            return column;
        }

        @Override
        public int getWidth(int colmun) {
            return width;
        }

        @Override
        public int getHeight(int row) {
            return height;
        }

        @Override
        public int getItemViewType(int row, int colmun) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
        }

        @Override
        public View getView(int row, int colmun, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
