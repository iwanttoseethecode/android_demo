package com.example.luoling.android_dome.propertyAnimaton;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.luoling.android_dome.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CoinActivity extends AppCompatActivity {

    @BindView(R.id.coinTxtView)
    TextView coinTxtView;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;

    private boolean isHeads = true; //是不是正面，ture是正面，false不是正面


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn1, R.id.btn2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
               AnimatorRotationY();
                break;
            case R.id.btn2:
                throw_coin_animator();
                break;
        }
    }

    public void AnimatorRotationY(){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(coinTxtView, "rotationY", 0f, 360f);
        objectAnimator.setDuration(2000);
        objectAnimator.setRepeatCount(1);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (valueAnimator.getAnimatedFraction() >= 0.25f && isHeads) {
                    coinTxtView.setBackgroundResource(R.drawable.shap_coin_green);
                    coinTxtView.setText("反面");
                    isHeads=false;
                }
                if (valueAnimator.getAnimatedFraction() > 0.75f && !isHeads) {
                    coinTxtView.setBackgroundResource(R.drawable.shape_coin_rad);
                    coinTxtView.setText("正面");
                    isHeads=true;
                }
            }
        });
        objectAnimator.start();
    }

    public void throw_coin_animator(){
        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.throw_coin_animator);
        animatorSet.setTarget(coinTxtView);
        ObjectAnimator objectAnimator = (ObjectAnimator) animatorSet.getChildAnimations().get(0);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (valueAnimator.getAnimatedFraction() >= 0.25f && isHeads) {
                    coinTxtView.setBackgroundResource(R.drawable.shap_coin_green);
                    coinTxtView.setText("反面");
                    isHeads=false;
                }
                if (valueAnimator.getAnimatedFraction() > 0.75f && !isHeads) {
                    coinTxtView.setBackgroundResource(R.drawable.shape_coin_rad);
                    coinTxtView.setText("正面");
                    isHeads=true;
                }
            }
        });
        animatorSet.start();
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        if(event.getAction()==MotionEvent.ACTION_DOWN){
//            AnimatorRotationY();
//            return true;
//        }
//        return super.onTouchEvent(event);
//    }
}
