package com.example.luoling.android_dome.RecyclerViewDemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.luoling.android_dome.R;
import com.example.luoling.android_dome.RecyclerViewDemo.headerFooter.MyAdapter;
import com.example.luoling.android_dome.RecyclerViewDemo.headerFooter.WrapRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeaderFooterRecyclerViewActivity extends AppCompatActivity {

    @BindView(R.id.wrapRecyclerView)
    WrapRecyclerView wrapRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_footer_recycler_view);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        TextView headerView = new TextView(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        headerView.setLayoutParams(layoutParams);
        headerView.setText("headerView,添加头部");
        wrapRecyclerView.addHeaderView(headerView);

        TextView footerView = new TextView(this);
        footerView.setLayoutParams(layoutParams);
        footerView.setText("footerView,添加尾部");
        wrapRecyclerView.addFooterView(footerView);

        ArrayList<String> list = new ArrayList<>();
        for (int i = 0;i < 30;i++){
            list.add(i,"item " + i);
        }
        MyAdapter adapter = new MyAdapter(list);
        wrapRecyclerView.setAdapter(adapter);
        wrapRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
