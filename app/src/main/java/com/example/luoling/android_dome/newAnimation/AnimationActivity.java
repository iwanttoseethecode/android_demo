package com.example.luoling.android_dome.newAnimation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.luoling.android_dome.R;

public class AnimationActivity extends Activity implements View.OnClickListener {

    private Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        initView();
    }

    public void initView(){
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn1:
                ObjectAnimator btn1ObjectAnimator = ObjectAnimator.ofFloat(btn1,"translationX",0f,300f);//后面的参数代表像素点
                btn1ObjectAnimator.setDuration(2000);
                btn1ObjectAnimator.start();
                break;
            case R.id.btn2:
                ObjectAnimator btn2ObjectAnimator = ObjectAnimator.ofFloat(btn2,"translationX",0f,300f,0f);
                btn2ObjectAnimator.setDuration(2000);
                btn2ObjectAnimator.start();
                break;
            case R.id.btn3:
                ObjectAnimator btn3ObjectAnimator = ObjectAnimator.ofArgb(btn3,"backgroundColor",getResources().getColor(android.R.color.holo_blue_bright),getResources().getColor(android.R.color.holo_red_dark));
                //设置无限循环，设置模式ValueAnimator.REVERSE表示反向播放,ValueAnimator.RESTART表示从头播放
                btn3ObjectAnimator.setRepeatCount(ValueAnimator.INFINITE);
                //设置反转效果
                btn3ObjectAnimator.setRepeatMode(ValueAnimator.REVERSE);
                btn3ObjectAnimator.setDuration(2000);
                btn3ObjectAnimator.start();
                break;
            case R.id.btn4:
                ObjectAnimator.ofFloat(btn4,"alpha",0f,1f).setDuration(2000).start();
                break;
            case R.id.btn5:
                //同时执行动画
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(ObjectAnimator.ofFloat(btn5,"alpha",0,1,0.5f,1),
                        ObjectAnimator.ofFloat(btn5,"rotation",0,360,0),
                        ObjectAnimator.ofFloat(btn5,"scaleX",0,1,1.5f,1),
                        ObjectAnimator.ofFloat(btn5,"scaleY",0,1,1.5f,1),
                        ObjectAnimator.ofFloat(btn5,"translationX",0,200f,0),
                        ObjectAnimator.ofFloat(btn5,"translationY",0,100f,0));
                animatorSet.setDuration(5000);
                animatorSet.start();
                break;
            case R.id.btn6:
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,1);
                valueAnimator.setDuration(5000);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    //每播放一帧，就会掉一次onAnimationUpdate方法
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        //设置当前动画的进度值
                        btn6.setAlpha((Float) valueAnimator.getAnimatedValue());
                        btn6.setScaleX((Float) valueAnimator.getAnimatedValue());
                        btn6.setScaleY((Float) valueAnimator.getAnimatedValue());
                    }
                });
                valueAnimator.start();
                break;
            case R.id.btn7:
                //动画顺序执行
                ObjectAnimator moveObjectAnimator = ObjectAnimator.ofFloat(btn7,"translationX",-200f,0f);
                ObjectAnimator rotateObjectAnimator = ObjectAnimator.ofFloat(btn7,"rotation",0f,360f);
                ObjectAnimator fadeoutinObjectAnimator = ObjectAnimator.ofFloat(btn7,"alpha",1f,0f,1f);
                AnimatorSet animatorSet1 = new AnimatorSet();
                animatorSet1.play(rotateObjectAnimator).with(fadeoutinObjectAnimator).after(moveObjectAnimator);
                animatorSet1.setDuration(4000);
                animatorSet1.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                animatorSet1.start();
                break;
            case R.id.btn8:
                AnimatorSet animatorSet2 = (AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.btn_anim);
                animatorSet2.setTarget(btn8);
                animatorSet2.start();
                break;
            case R.id.btn9:
                Intent intent = new Intent(this,ValueAnimatorActivity.class);
                startActivity(intent);
                break;
        }
    }
}
