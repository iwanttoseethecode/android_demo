package com.example.luoling.android_dome.newAnimation;


/*属性动画之TimeInterpolator和TypeEvaluator
  转载地址：http://blog.csdn.net/javazejian/article/details/52334098
  每个TimeInterpolator和TypeEvaluator的衍生类都是代表着一个数学中的函数公式，而每一个ValueAnimator也不过是一个TimeInterpolator和一个TypeEvaluator的结合体。
  从数学的函数角度来说，ValueAnimator就是由TimeInterpolator和TypeEvaluator这两个简单函数组合而成的一个复合函数，
  ValueAnimator而就是通过TimeInterpolator和TypeEvaluator相结合的方式来对一个数值作运动的。

  继承关系 TimeInterpolator <—— Interpolator <—— BaseInterpolator <——其中常用的有LinearInterpolator（线性插值器：匀速动画，刚分析过），
  AccelerateDecelerateInterpolator（加速减速器：动画开头和结束慢，中间快，默认插值器），DecelerateInterpolator（减速插值器：动画越来越慢）、
  BounceInterpolator （弹跳插值器）、AnticipateInterpolator （回荡秋千插值器）、CycleInterpolator （正弦周期变化插值器）等等

*/


import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.example.luoling.android_dome.R;

public class ValueAnimatorActivity extends Activity implements View.OnClickListener{

    public TextView RunningbollTxtView;
    public Button resetbtn,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_animator);
        RunningbollTxtView = (TextView) findViewById(R.id.runningboll);
        resetbtn = (Button) findViewById(R.id.resetbtn);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btn10 = (Button) findViewById(R.id.btn10);

        resetbtn.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.resetbtn:
                ObjectAnimator resetAnimator = ObjectAnimator.ofFloat(RunningbollTxtView,"translationY",0f);
                resetAnimator.setDuration(800);
                resetAnimator.setInterpolator(new LinearInterpolator());
                resetAnimator.start();
                break;
            case R.id.btn1: //线性插值器
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(RunningbollTxtView,"translationY",0f,800f);
                objectAnimator1.setDuration(5000);
                objectAnimator1.setInterpolator(new LinearInterpolator());
                objectAnimator1.start();
                break;
            case R.id.btn2: //加速减速器
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(RunningbollTxtView,"translationY",0f,800f);
                objectAnimator2.setDuration(5000);
                objectAnimator2.setInterpolator(new AccelerateDecelerateInterpolator());
                objectAnimator2.start();
                break;
            case R.id.btn3://加速器插值器
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(RunningbollTxtView,"translationY",0f,800f);
                objectAnimator3.setDuration(5000);
                objectAnimator3.setInterpolator(new AccelerateInterpolator());
                objectAnimator3.start();
                break;
            case R.id.btn4://减速插值器
                ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(RunningbollTxtView,"translationY",0f,800f);
                objectAnimator4.setDuration(5000);
                objectAnimator4.setInterpolator(new DecelerateInterpolator());
                objectAnimator4.start();
                break;
            case R.id.btn5://回荡插值器，特点慢速反向运动然后加速会往回落
                ObjectAnimator objectAnimator5 = ObjectAnimator.ofFloat(RunningbollTxtView,"translationY",200f,800f);
                objectAnimator5.setDuration(5000);
                objectAnimator5.setInterpolator(new AnticipateInterpolator());
                objectAnimator5.start();
                break;
            case R.id.btn6://正弦周期插值器，特点以指定的周期重复动画，变化率曲线为正弦
                ObjectAnimator objectAnimator6 = ObjectAnimator.ofFloat(RunningbollTxtView,"translationY",400f,800f);
                objectAnimator6.setDuration(5000);
                objectAnimator6.setInterpolator(new CycleInterpolator(2));
                objectAnimator6.start();
                break;
            case R.id.btn7://弹跳插值器，特点是动画结尾，呈弹跳状态。
                ObjectAnimator objectAnimator7 = ObjectAnimator.ofFloat(RunningbollTxtView,"translationY",0f,800f);
                objectAnimator7.setDuration(5000);
                objectAnimator7.setInterpolator(new BounceInterpolator());
                objectAnimator7.start();
                break;
            case R.id.btn8://特点开始向上推，然后向下荡，荡过最低线。然后再回到最低线。
                ObjectAnimator objectAnimator8 = ObjectAnimator.ofFloat(RunningbollTxtView,"translationY",0f,800f);
                objectAnimator8.setDuration(5000);
                objectAnimator8.setInterpolator(new AnticipateOvershootInterpolator());
                objectAnimator8.start();
                break;
            case R.id.btn9://自定义Interpolator
                ObjectAnimator objectAnimator9 = ObjectAnimator.ofFloat(RunningbollTxtView,"translationY",200f,400f);
                objectAnimator9.setDuration(5000);
                objectAnimator9.setInterpolator(new MyInterpolator(3));
                objectAnimator9.setEvaluator(new TypeEvaluator() {
                    @Override
                    public Object evaluate(float fraction, Object startValue, Object endValue) {
                        return null;
                    }
                });
                objectAnimator9.start();

                break;
            case R.id.btn10://自定义抛物线运动
                //PointF数值为float,Point数值为int,我们这里选择PointF
                ValueAnimator valueAnimator = new ValueAnimator();
                valueAnimator.setObjectValues(new PointF(0,0));
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.setEvaluator(new MyEvalutor());
//                valueAnimator.setObjectValues(new PointF(0,0),new PointF(100,100));
//                final PointF pointF = new PointF();
//                valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
//                    @Override
//                    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
//                        pointF.x = 100f*(fraction*5);
//                        pointF.y = 0.5f*9.8f*(fraction*5)*(fraction*5);
//                        pointF.y = 10f*0.5f*9.8f*(fraction*5)*(fraction*5);
//                        return pointF;
//                    }
//
//                });
                valueAnimator.setDuration(5000);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        PointF pointf = (PointF) valueAnimator.getAnimatedValue();
                        //设置动画
                        RunningbollTxtView.setX(pointf.x);
                        RunningbollTxtView.setY(pointf.y);
                    }
                });
                valueAnimator.start();
                break;
        }
    }
}
