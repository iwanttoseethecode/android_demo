package com.example.luoling.android_dome.newAnimation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.luoling.android_dome.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OverPageActivity extends AppCompatActivity {

    @BindView(R.id.first)
    ImageView first;
    @BindView(R.id.second)
    ImageView second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_page);
        ButterKnife.bind(this);
    }

    public void startFirstAnim(View v){
        ObjectAnimator firstAlphaAnim = ObjectAnimator.ofFloat(first,"alpha",1.0f,0.7f);
        firstAlphaAnim.setDuration(300);
        ObjectAnimator firstRotationXanim = ObjectAnimator.ofFloat(first,"rotationX",0f,20f);
        firstRotationXanim.setDuration(300);
        ObjectAnimator firstReversalRotationXanim = ObjectAnimator.ofFloat(first,"rotationX",20f,0f);
        firstReversalRotationXanim.setDuration(300);
        firstReversalRotationXanim.setStartDelay(300);
        ObjectAnimator firstScaleXAnim = ObjectAnimator.ofFloat(first,"ScaleX",1.0f,0.8f);
        firstScaleXAnim.setDuration(300);
        ObjectAnimator firstScaleYAnim = ObjectAnimator.ofFloat(first,"ScaleY",1.0f,0.8f);
        firstScaleYAnim.setDuration(300);
        ObjectAnimator firstTranslationYAnim = ObjectAnimator.ofFloat(first,"translationY",0f, -0.1f * first.getHeight());
        firstTranslationYAnim.setDuration(300);

        ObjectAnimator secondTranslationYAnim = ObjectAnimator.ofFloat(second,"translationY",second.getHeight(),0f);
        secondTranslationYAnim.setDuration(300);

        secondTranslationYAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                second.setVisibility(View.VISIBLE);
                first.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        AnimatorSet set = new AnimatorSet();
        set.playTogether(firstAlphaAnim,firstRotationXanim,firstReversalRotationXanim,firstScaleXAnim,firstScaleYAnim,firstTranslationYAnim,secondTranslationYAnim);
        set.start();
    }

}
