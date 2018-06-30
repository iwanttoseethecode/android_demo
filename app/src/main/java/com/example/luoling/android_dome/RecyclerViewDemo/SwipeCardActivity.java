package com.example.luoling.android_dome.RecyclerViewDemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.luoling.android_dome.R;
import com.example.luoling.android_dome.RecyclerViewDemo.SwipeCard.CardConfig;
import com.example.luoling.android_dome.RecyclerViewDemo.SwipeCard.SwipeCardCallback;
import com.example.luoling.android_dome.RecyclerViewDemo.SwipeCard.SwipeCardLayoutManager;
import com.example.luoling.android_dome.RecyclerViewDemo.SwipeCard.UniversalAdapter;
import com.example.luoling.android_dome.RecyclerViewDemo.SwipeCard.UniversalViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SwipeCardActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<SwipeCardBean> datalist;
    private UniversalAdapter<SwipeCardBean> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_card);
        ButterKnife.bind(this);

        datalist = initData();
        adapter = new UniversalAdapter<SwipeCardBean>(this,datalist,R.layout.item_swipe_card) {
            @Override
            public void convert(UniversalViewHolder var, SwipeCardBean var2) {
                var.setText(R.id.tvName,var2.getName());
                var.setText(R.id.tvPrecent,var2.getPosition() + "/" + datalist.size());
                Glide.with(SwipeCardActivity.this).load(var2.getUrl()).into((ImageView)var.getView(R.id.iv));
            }
        };
        recyclerView.setAdapter(adapter);

        CardConfig.initConfig(getApplicationContext());

        RecyclerView.LayoutManager layoutManager = new SwipeCardLayoutManager();
        recyclerView.setLayoutManager(layoutManager);

        SwipeCardCallback cardCallback = new SwipeCardCallback(0,0,adapter,datalist,recyclerView);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(cardCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private List<SwipeCardBean> initData(){
        List<SwipeCardBean> list = new ArrayList<>();
        int i = 1;
        list.add(new SwipeCardBean(i++,"http://p0.so.qhimgs1.com/t01e1a39655c9befc5a.jpg","美女1"));
        list.add(new SwipeCardBean(i++,"http://cdnq.duitang.com/uploads/item/201505/04/20150504155014_irFvu.thumb.700_0.jpeg","美女2"));
        list.add(new SwipeCardBean(i++,"http://img5q.duitang.com/uploads/item/201309/17/20130917032356_hdGjF.thumb.700_0.jpeg","美女3"));
        list.add(new SwipeCardBean(i++,"http://img5q.duitang.com/uploads/item/201505/04/20150504155137_JWcRK.thumb.700_0.jpeg","美女4"));
        list.add(new SwipeCardBean(i++,"http://p1.so.qhimgs1.com/t01945b569baf7d65fd.jpg","美女5"));
        list.add(new SwipeCardBean(i++,"http://img5q.duitang.com/uploads/item/201504/26/201504262026_weMcT.thumb.700_0.jpeg","美女6"));
        list.add(new SwipeCardBean(i++,"http://img5q.duitang.com/uploads/item/201504/26/201504261920_HsSm3.thumb.700_0.jpeg","美女7"));
        list.add(new SwipeCardBean(i++,"http://p0.so.qhimgs1.com/sdr/200_200_/t01835173377162b1ba.jpg","美女8"));
        list.add(new SwipeCardBean(i++,"http://p2.so.qhimgs1.com/t01d3869cc0ab6165f2.jpg","美女9"));
        list.add(new SwipeCardBean(i++,"http://p1.so.qhimgs1.com/t01f085fb54ff7434e1.jpg","美女10"));

        return list;
    }


    class SwipeCardBean{
        private String url;
        private String name;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        private int position;

        public SwipeCardBean(int position,String url, String name) {
            this.url = url;
            this.name = name;
            this.position = position;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
