package com.example.luoling.android_dome.propertyAnimaton;

import android.animation.Animator;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.luoling.android_dome.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
* 布局变化的动画，只需要在xml中设置android:animateLayoutChanges标签，或者在代码中添加一个LayoutTransition对象，即可实现任何ViewGroup改变布局是的动画
* */

public class ChangeLayoutActivity extends AppCompatActivity {

    @BindView(R.id.verticalContainer)
    LinearLayout verticalContainer;
    @BindView(R.id.verticalContainer2)
    LinearLayout verticalContainer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_layout);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button_add, R.id.button_add2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_add:
                Button button = new Button(this);
                button.setText("Click To Remove");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        verticalContainer.removeView(view);
                    }
                });
                verticalContainer.addView(button, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                break;
            case R.id.button_add2:
                //布局改变时的动画
                LayoutTransition transition = new LayoutTransition();
                verticalContainer2.setLayoutTransition(transition);
                //通过翻转进入的动画代替默认的出现动画
                Animator appearAnim = ObjectAnimator.ofFloat(null, "rotationY", 90f, 0f).setDuration(500);
                transition.setAnimator(LayoutTransition.APPEARING, appearAnim);
                //通过翻转消失的动画代替默认的消失动画
                Animator disappearAnimator = ObjectAnimator.ofFloat(null, "rotationX", 0f, 90f).setDuration(transition.getDuration(LayoutTransition.DISAPPEARING));
                transition.setAnimator(LayoutTransition.DISAPPEARING, disappearAnimator);
                //通过滑动动画代替默认的布局改变时的动画，我们需要立即设置一些动画属性，所以创建了多个PropertyValueHolder对象的动画这个动画会让视图滑动进入并短暂的缩小一半长度
                PropertyValuesHolder pvsSlide = PropertyValuesHolder.ofFloat("y", 0, 1);
                PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0.5f, 1f);
                PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0.5f, 1f);
                Animator changingAppearingAnim = ObjectAnimator.ofPropertyValuesHolder(this, pvsSlide, pvhScaleY, pvhScaleY);
                //其他视图消失导致某些视图改变
                changingAppearingAnim.setDuration(transition.getDuration(LayoutTransition.CHANGE_DISAPPEARING));
                transition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, changingAppearingAnim);

                Button button2 = new Button(this);
                button2.setText("Click To Remove");
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        verticalContainer2.removeView(view);
                    }
                });
                verticalContainer2.addView(button2, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                break;
        }
    }

//    @OnClick(R.id.button_add)
//    public void onClick() {
//        Button button = new Button(this);
//        button.setText("Click To Remove");
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                verticalContainer.removeView(view);
//            }
//        });
//
//        verticalContainer.addView(button, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//
//    }


}
