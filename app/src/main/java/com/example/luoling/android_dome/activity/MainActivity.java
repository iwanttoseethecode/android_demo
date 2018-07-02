package com.example.luoling.android_dome.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.luoling.android_dome.ButterKnife.ButterKnifeActivity;
import com.example.luoling.android_dome.CustomDrawerLayout.DrawerLayoutActivity;
import com.example.luoling.android_dome.DragBubble.DragBubleActivity;
import com.example.luoling.android_dome.Graphics2D.BallMoveActivity;
import com.example.luoling.android_dome.Graphics2D.BoomActivity;
import com.example.luoling.android_dome.Graphics2D.ClipActivity;
import com.example.luoling.android_dome.Graphics2D.CoordinateActivity;
import com.example.luoling.android_dome.Graphics2D.WatchActivity;
import com.example.luoling.android_dome.PathMeasure.PathMeasureActivity;
import com.example.luoling.android_dome.R;
import com.example.luoling.android_dome.RecyclerViewDemo.DragSwipeRecyclerViewActivity;
import com.example.luoling.android_dome.RecyclerViewDemo.HeaderFooterRecyclerViewActivity;
import com.example.luoling.android_dome.RecyclerViewDemo.SwipeCardActivity;
import com.example.luoling.android_dome.RetrofitDemo.RetrofitActivity;
import com.example.luoling.android_dome.ScrollPoster.ScrollPosterActivity;
import com.example.luoling.android_dome.advancedUI.flowlayout.FlowLayoutActivity;
import com.example.luoling.android_dome.advancedUI.waterfallLayout.WaterfallActivity;
import com.example.luoling.android_dome.camera3D.Camera3DActivity;
import com.example.luoling.android_dome.chapter5.After5_4Activity;
import com.example.luoling.android_dome.chapter5.ShadowActivity;
import com.example.luoling.android_dome.chapter6.Chapter6Activity;
import com.example.luoling.android_dome.circleprogressbar.CircleProgressBarActivity;
import com.example.luoling.android_dome.colorFilter.ColorFilterActivity;
import com.example.luoling.android_dome.commondapter.CommonAdapter;
import com.example.luoling.android_dome.commondapter.ViewHolder;
import com.example.luoling.android_dome.customView.PaintDetailActivity;
import com.example.luoling.android_dome.customView.PathActivity;
import com.example.luoling.android_dome.customView.first_Paint_Canvas_Activity;
import com.example.luoling.android_dome.doublecache.DrawRectActivity;
import com.example.luoling.android_dome.doublecache.TabletActivity;
import com.example.luoling.android_dome.drawble.DrawbleActivity;
import com.example.luoling.android_dome.newAnimation.AnimationActivity;
import com.example.luoling.android_dome.newAnimation.OverPageActivity;
import com.example.luoling.android_dome.okhttpdome.OkhttpStudyActivity;
import com.example.luoling.android_dome.praise.PraiseActivity;
import com.example.luoling.android_dome.propertyAnimaton.ChangeLayoutActivity;
import com.example.luoling.android_dome.propertyAnimaton.CoinActivity;
import com.example.luoling.android_dome.radar.RadarActivity;
import com.example.luoling.android_dome.splashAnimation.SplashAnimationActivity;
import com.example.luoling.android_dome.svg.SVGChinaMapActivity;
import com.example.luoling.android_dome.svg.SVGDemoActivity;
import com.example.luoling.android_dome.tablayout.TabLayoutActivity;
import com.example.luoling.android_dome.tagetcricle.TagetCricleActivity;
import com.example.luoling.android_dome.viewPager3D.ViewPager3DActivity;
import com.example.luoling.android_dome.xformode.XformodeActivity;
import com.example.luoling.android_dome.xformode.XformodeDemoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luoling on 2016/7/14.
 *
 */
public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    private ListView myListView;
    private List<Model>  mList = new ArrayList<Model>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_mainactivity);
        myListView = (ListView) findViewById(R.id.myListView);

        myListView.setOnItemClickListener(this);

        addModel();
        MyAdapter myAdapter = new MyAdapter(this,mList,R.layout.textview_layout);
        myListView.setAdapter(myAdapter);
        myListView.invalidate();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

   public void addModel(){
       mList.add(new Model("自定义控件", Chapter6Activity.class));
       mList.add(new Model("自定义流式布局", FlowLayoutActivity.class));
       mList.add(new Model("瀑布流布局", WaterfallActivity.class));
       mList.add(new Model("camera的三维应用", Camera3DActivity.class));
       mList.add(new Model("RecyclerView添加头部和尾部的应用",HeaderFooterRecyclerViewActivity.class));
       mList.add(new Model("RecyclerView拖拽侧滑", DragSwipeRecyclerViewActivity.class));
       mList.add(new Model("RecyclerView仿探探侧滑图片浏览", SwipeCardActivity.class));
       mList.add(new Model("自定义贝塞尔drawerLayout", DrawerLayoutActivity.class));
       mList.add(new Model("Tablayout和Palette打造风格统一的首页", TabLayoutActivity.class));
//       mList.add(new Model("自定义表单布局", TableActivity.class));
       mList.add(new Model("自定义靶心视图",TagetCricleActivity.class));
       mList.add(new Model("布局变换动画", ChangeLayoutActivity.class));
       mList.add(new Model("硬币动画视图", CoinActivity.class));
       mList.add(new Model("动画视图",AnimationActivity.class));
       mList.add(new Model("翻页动画", OverPageActivity.class));
       mList.add(new Model("点赞动画", PraiseActivity.class));
       mList.add(new Model("炫酷的开场动画", SplashAnimationActivity.class));
       mList.add(new Model("滑动海报动画", ScrollPosterActivity.class));
       mList.add(new Model("viewPager的3D翻转效果", ViewPager3DActivity.class));
       mList.add(new Model("SVG使用及动画", SVGDemoActivity.class));
       mList.add(new Model("SVG中国地图",SVGChinaMapActivity.class));
       mList.add(new Model("Canvas、Paint使用",first_Paint_Canvas_Activity.class));
       mList.add(new Model("Paint的详细应用",PaintDetailActivity.class));
       mList.add(new Model("自定义环形进度条",CircleProgressBarActivity.class));
       mList.add(new Model("Path使用", PathActivity.class));
       mList.add(new Model("PathMeasure使用", PathMeasureActivity.class));
       mList.add(new Model("来回滚动刷新", BallMoveActivity.class));
       mList.add(new Model("坐标转换", CoordinateActivity.class));
       mList.add(new Model("Xformode效果展示",XformodeActivity.class));
       mList.add(new Model("Xformode案例", XformodeDemoActivity.class));
       mList.add(new Model("ColorFilter效果", ColorFilterActivity.class));
       mList.add(new Model("图片剪切", ClipActivity.class));
       mList.add(new Model("自定义drawable", DrawbleActivity.class));
       mList.add(new Model("仿qq消息气泡", DragBubleActivity.class));
       mList.add(new Model("爆炸效果动画", BoomActivity.class));
       mList.add(new Model("自定义时钟", WatchActivity.class));
       mList.add(new Model("写字板", TabletActivity.class));
       mList.add(new Model("画矩形", DrawRectActivity.class));
       mList.add(new Model("阴影", ShadowActivity.class));
       mList.add(new Model("雷达扫描和水波纹按钮",RadarActivity.class));
       mList.add(new Model("图层", After5_4Activity.class));
       mList.add(new Model("注解框架ButterKnife", ButterKnifeActivity.class));
       mList.add(new Model("网络框架之OkHttp", OkhttpStudyActivity.class));
       mList.add(new Model("网络框架之Retrofit", RetrofitActivity.class));
   }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Model model = (Model) adapterView.getAdapter().getItem(i);
        Intent intent = new Intent(this, model.getIntentObject());
        startActivity(intent);
    }

    protected class MyAdapter extends CommonAdapter<Model> {

        public MyAdapter(Context mContext, List<Model> mList, int LayoutId) {
            super(mContext, mList, LayoutId);

        }

        @Override
        public void convert(ViewHolder holder, final Model item) {
            TextView mTextView = holder.getView(R.id.classtext);
            mTextView.setText(item.getTitle());
//            holder.getConvertView().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(MainActivity.this,item.getIntentObject());
//                    startActivity(intent);
//                }
//            });
        }
    }

    class Model{
        private String title;
        private Class<?> intentObject;

        public Model( String title,Class<?> intentObject) {
            this.intentObject = intentObject;
            this.title = title;
        }

        public Class<?> getIntentObject() {
            return intentObject;
        }

        public void setIntentObject(Class<?> intentObject) {
            this.intentObject = intentObject;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        return super.onKeyDown(keyCode, event);
    }
}
