package com.example.luoling.android_dome.sceneTransition;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.example.luoling.android_dome.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SceneTransOneActivity extends AppCompatActivity {

    @BindView(R.id.img1)
    View img1;
    @BindView(R.id.img2)
    View img2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_scene_trans_one);
        ButterKnife.bind(this);
    }

    public void click1(View view) {
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, img1,getString(R.string.scene_trans_1));
        ActivityCompat.startActivity(this,new Intent(this,SceneTransTwoActivity.class),compat.toBundle());
    }

    public void click2(View view) {
        Pair<View,String> pair1 = Pair.create(img1,getString(R.string.scene_trans_1));
        Pair<View,String> pair2 = Pair.create(img2,getString(R.string.scene_trans_2));

        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this,pair1,pair2);
        ActivityCompat.startActivity(this,new Intent(this,SceneTransTwoActivity.class),compat.toBundle());

    }
}
