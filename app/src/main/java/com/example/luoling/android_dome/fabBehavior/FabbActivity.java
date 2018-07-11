package com.example.luoling.android_dome.fabBehavior;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.luoling.android_dome.R;

import java.util.ArrayList;
import java.util.List;

public class FabbActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabb);
        initView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        setTitle("网易新闻");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        List<String> list = new ArrayList<>();
        for (int i = 0;i<20;i++){
            list.add("item"+i);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyRecyclerAdapter(list));
    }
}
