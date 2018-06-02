package com.example.luoling.android_dome.drawble;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.luoling.android_dome.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 * Drawable的源码解析文章，看完之后基本上会自定义简单的Drawable了 https://www.jianshu.com/p/c56b762210f2
 * */
public class DrawbleActivity extends AppCompatActivity {

    @BindView(R.id.ghsv)
    GallaryHorizonalScrollView ghsv;
    @BindView(R.id.roundImageView)
    ImageView roundImageView;
    @BindView(R.id.circleImageView)
    ImageView circleImageView;
    @BindView(R.id.searchImgView)
    SearchImageView searchImgView;
    private int[] mImgIds = new int[]{
            R.mipmap.avft,
            R.mipmap.box_stack,
            R.mipmap.bubble_frame,
            R.mipmap.bubbles,
            R.mipmap.bullseye,
            R.mipmap.circle_filled,
            R.mipmap.circle_outline,
            R.mipmap.avft,
            R.mipmap.box_stack,
            R.mipmap.bubble_frame,
            R.mipmap.bubbles,
            R.mipmap.bullseye,
            R.mipmap.circle_filled,
            R.mipmap.circle_outline
    };

    private int[] mImgIds_active = new int[]{
            R.mipmap.avft_active,
            R.mipmap.box_stack_active,
            R.mipmap.bubble_frame_active,
            R.mipmap.bubbles_active,
            R.mipmap.bullseye_active,
            R.mipmap.circle_filled_active,
            R.mipmap.circle_outline_active,
            R.mipmap.avft_active,
            R.mipmap.box_stack_active,
            R.mipmap.bubble_frame_active,
            R.mipmap.bubbles_active,
            R.mipmap.bullseye_active,
            R.mipmap.circle_filled_active,
            R.mipmap.circle_outline_active
    };

    public Drawable[] revealDrawables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawble);
        ButterKnife.bind(this);
        initReveal();
        initRoundImage();
        initCircleImage();
        initSearchImage();
    }

    private void initSearchImage() {
        searchImgView.setImageDrawable(new SearchDrawable());
    }

    private void initCircleImage() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.p4);
        circleImageView.setImageDrawable(new CircleImageDrawable(bitmap));
    }

    private void initRoundImage() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.p4);
        roundImageView.setImageDrawable(new RoundImageDrawable(bitmap, 40));
    }

    private void initReveal() {
        revealDrawables = new Drawable[mImgIds.length];
        for (int i = 0; i < mImgIds.length; i++) {
            RevealDrawble rd = new RevealDrawble(getResources().getDrawable(mImgIds[i]), getResources().getDrawable(mImgIds_active[i]), RevealDrawble.HORIZONTAL);
            revealDrawables[i] = rd;
        }
        ghsv.addImageViews(revealDrawables);
    }

}
