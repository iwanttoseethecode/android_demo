package com.example.luoling.android_dome.RecyclerViewDemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.luoling.android_dome.R;
import com.example.luoling.android_dome.RecyclerViewDemo.dragSwipe.MessageAdapter;
import com.example.luoling.android_dome.RecyclerViewDemo.dragSwipe.MessageItemTouchCallback;
import com.example.luoling.android_dome.RecyclerViewDemo.dragSwipe.modifySource.ItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DragSwipeRecyclerViewActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_swipe_recycler_view);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
         List<String> list= generate();

        MessageAdapter adapter = new MessageAdapter(list, new MessageAdapter.StartDragListener() {
            @Override
            public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
                itemTouchHelper.startDrag(viewHolder);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL, Color.BLACK,2));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new MessageItemTouchCallback(adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private List<String> generate() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++){
            list.add("item    ###   " + i);
        }
        return list;
    }


}
