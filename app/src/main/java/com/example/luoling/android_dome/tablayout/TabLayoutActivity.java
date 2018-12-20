package com.example.luoling.android_dome.tablayout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

import com.example.luoling.android_dome.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TabLayoutActivity extends AppCompatActivity {


    private final String[] titels = {"分类", "主页", "热门推荐", "热门收藏", "本月热榜", "今日热榜", "专栏", "随机"};
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    private int[] drawbles = {R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e, R.mipmap.f, R.mipmap.g, R.mipmap.h};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_tab_layout);
        ButterKnife.bind(this);

        setToolBarStyle(toolbar);

        toolbar.setTitle("安静苦笑");
        //顺序非常重要  设置 文字 样式调用setSupportActionBar  之前     设置监听在 setSupportActionBar之后
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        actionBarDrawerToggle.syncState();
        drawer.addDrawerListener(actionBarDrawerToggle);


        MyAdatper myAdatper = new MyAdatper(getSupportFragmentManager());
        viewpager.setAdapter(myAdatper);
        //设置联动
        tablayout.setupWithViewPager(viewpager);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                colorChage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        colorChage(0);
    }

    /**
     * 如果页面 发生切换     根据BItmap改变toolBar的颜色
     *
     * @param position
     */
    private void colorChage(int position) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawbles[position]);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                //拿到鲜艳的颜色
                Palette.Swatch vibreant = palette.getVibrantSwatch();
                if (vibreant == null) {
                    for (Palette.Swatch swatch : palette.getSwatches()) {
                        vibreant = swatch;
                        break;
                    }
                }

                viewpager.setBackgroundColor(vibreant.getRgb());
                tablayout.setSelectedTabIndicatorColor(vibreant.getRgb());
                tablayout.setBackgroundColor(vibreant.getRgb());
                toolbar.setBackgroundColor(vibreant.getRgb());
                if (Build.VERSION.SDK_INT >= 21) {
                    Window window = getWindow();
                    //状态栏 颜色加深
                    window.setStatusBarColor(colorBurn(vibreant.getRgb()));
                    window.setNavigationBarColor(vibreant.getRgb());
                }

            }
        });
    }

    private int colorBurn(int rgb) {
        //加深颜色
//        int all= (int) (rgb*1.1f);
//        红色
        int red = rgb >> 16 & 0xFF;
        int gree = rgb >> 8 & 0xFF;
        int blue = rgb & 0xFF;

        //
        red = (int) Math.floor(red * (1 - 0.2));
        gree = (int) Math.floor(gree * (1 - 0.2));
        blue = (int) Math.floor(blue * (1 - 0.2));
        return Color.rgb(red, gree, blue);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    class MyAdatper extends FragmentPagerAdapter {


        public MyAdatper(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new ImageFrament();
            Bundle bundle = new Bundle();
            bundle.putInt("id", drawbles[position]);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return drawbles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titels[position];
        }
    }


    private void setToolBarStyle(Toolbar toolbar){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            int statusHeight = getStatusHeight();
//                layoutParams.height=statusHeight;
//                topView.setBackgroundColor(Color.GREEN);
            if (toolbar != null){
                toolbar.setPadding(0,toolbar.getPaddingTop()+statusHeight,0,0);
            }

            //下面的导航栏,boottomView是一个与NavigationBar一样高度的View
//            if (haveNavgtion() && bottomView != null) {
//                ViewGroup.LayoutParams layoutParams=bottomView.getLayoutParams();
//                layoutParams.height+=getNavigationHeight();
//                Log.i("tuch", "getNavigationHeight  " + getNavigationHeight());
//                bottomView.setLayoutParams(layoutParams);
//                bottomView.setBackgroundColor(styleColor);
//
//            }

        }
    }

    /*
    * 获取statusbar的高度
    * */
    private int getStatusHeight() {
        int height=-1;
        try {
            Class<?> clazz=Class.forName("com.android.internal.R$dimen");
            Object  object=clazz.newInstance();
            String heightStr=clazz.getField("status_bar_height").get(object).toString();
            height = Integer.parseInt(heightStr);
            //dp--px
            height = getResources().getDimensionPixelSize(height);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


        return height;
    }

    private int getNavigationHeight() {
        int height=-1;
        try {
            Class<?> clazz=Class.forName("com.android.internal.R$dimen");
            Object  object=clazz.newInstance();
            String heightStr=clazz.getField("navigation_bar_height").get(object).toString();
            height = Integer.parseInt(heightStr);
            //dp--px
            height = getResources().getDimensionPixelSize(height);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return height;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private boolean haveNavgtion() {
        //屏幕的高度  真实物理的屏幕
        Display display=getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics=new DisplayMetrics();
        display.getRealMetrics(displayMetrics);
        int heightDisplay=displayMetrics.heightPixels;
        //为了防止横屏
        int widthDisplay=displayMetrics.widthPixels;
        DisplayMetrics contentDisplaymetrics=new DisplayMetrics();
        display.getMetrics(contentDisplaymetrics);
        int contentDisplay=contentDisplaymetrics.heightPixels;
        int contentDisplayWidth=contentDisplaymetrics.widthPixels;
        //屏幕内容高度  显示内容的屏幕
        int w=widthDisplay-contentDisplayWidth;
        //哪一方大于0   就有导航栏
        int h=heightDisplay-contentDisplay;
        return w>0||h>0;
    }

}
