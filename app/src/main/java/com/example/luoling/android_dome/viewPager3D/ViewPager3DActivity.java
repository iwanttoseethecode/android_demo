package com.example.luoling.android_dome.viewPager3D;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.luoling.android_dome.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPager3DActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    WelcomePagerAdapter welcomePagerAdapter;
    WelcompagerTransformer welcompagerTransformer;
    @BindView(R.id.spinner)
    Spinner spinner;
    private int[] layouts = {R.layout.viewpager3d_1, R.layout.viewpager3d_2, R.layout.viewpager3d_3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager3_d);
        ButterKnife.bind(this);

        spinner.setOnItemSelectedListener(this);

        welcomePagerAdapter = new WelcomePagerAdapter(getSupportFragmentManager());
        welcompagerTransformer = new WelcompagerTransformer();
        viewPager.setPageTransformer(true, welcompagerTransformer);
        viewPager.setAdapter(welcomePagerAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String name = (String) parent.getItemAtPosition(position);
        if (TextUtils.equals(name,"效果1")){
            welcompagerTransformer.setEffect(WelcompagerTransformer.Effect.EFFECT1);
        }else if (TextUtils.equals(name,"效果2")){
            welcompagerTransformer.setEffect(WelcompagerTransformer.Effect.EFFECT2);
        }else if (TextUtils.equals(name,"效果3")){
            welcompagerTransformer.setEffect(WelcompagerTransformer.Effect.EFFECT3);
        }else if (TextUtils.equals(name,"效果4")){
            welcompagerTransformer.setEffect(WelcompagerTransformer.Effect.EFFECT4);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    class WelcomePagerAdapter extends FragmentPagerAdapter {

        public WelcomePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = new TranslateFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("layoutId", layouts[position]);
            f.setArguments(bundle);
            return f;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
