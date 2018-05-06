package com.example.luoling.android_dome.praise;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.luoling.android_dome.R;

import java.util.Random;

public class LoveLayout extends RelativeLayout {

    private int mWidth;
    private int mHeight;

    private Drawable[] drawbles;
    private Random random = new Random();
    private LayoutParams params;
    private int dWidth;
    private int dHeight;

    private Interpolator line = new LinearInterpolator();
    private Interpolator acc = new AccelerateInterpolator();
    private Interpolator dec = new DecelerateInterpolator();
    private Interpolator accdec = new AccelerateDecelerateInterpolator();
    private Interpolator[] interpolators;

    public LoveLayout(Context context) {
        super(context);
        init();
    }

    public LoveLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    private void init(){
        interpolators = new Interpolator[]{line,line,acc,dec,accdec};
        drawbles = new Drawable[]{getResources().getDrawable(R.mipmap.love_blue),getResources().getDrawable(R.mipmap.love_red),getResources().getDrawable(R.mipmap.love_yellow)};

        dWidth = drawbles[0].getIntrinsicWidth();
        dHeight = drawbles[0].getIntrinsicHeight();
        params = new LayoutParams(dWidth,dHeight);

        //将子元素添加父容器的底部、水平居中位置。
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(ALIGN_PARENT_BOTTOM);
    }

    public void addLoveIcon(){
        final ImageView imageView = new ImageView(getContext());

        imageView.setImageDrawable(drawbles[random.nextInt(3)]);
        imageView.setLayoutParams(params);
        addView(imageView);

        AnimatorSet set = getAnimator(imageView);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                removeView(imageView);
            }
        });
        set.start();
    }

    private AnimatorSet getAnimator(ImageView imageView) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView,"alpha",0.3f,1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView,"scaleX",0.3f,1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView,"scaleY",0.3f,1f);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(600);
        set.playTogether(alpha,scaleX,scaleY);

        ValueAnimator bezierAnimator = getBezierValueAnimator(imageView);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(set,bezierAnimator);
        animatorSet.setTarget(imageView);
        return animatorSet;
    }

    private ValueAnimator getBezierValueAnimator(final ImageView imageView) {
        //设置3阶贝塞尔曲线的四个点
        PointF pointF0 = new PointF((mWidth - dWidth)/2,mHeight - dHeight);
        PointF pointF3 = new PointF(random.nextInt(mWidth),0);
        PointF pointF1 = getPointF(PointType.inflectionFirst);
        PointF pointF2 = getPointF(PointType.inflectionsecond);

        BezierEvaluator evaluator = new BezierEvaluator(pointF1,pointF2);
        ValueAnimator animator = ValueAnimator.ofObject(evaluator,pointF0,pointF3);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                imageView.setX(pointF.x);
                imageView.setY(pointF.y);
                imageView.setAlpha(1 - animation.getAnimatedFraction());
            }
        });
        animator.setDuration(4000);
        animator.setInterpolator(interpolators[random.nextInt(5)]);
        return  animator;
    }

    enum PointType{
        inflectionFirst,inflectionsecond
    }

    private PointF getPointF(PointType type) {
        PointF pointF = new PointF();
        pointF.x = random.nextInt(mWidth);
        //为了好看尽量保证point2.y>point1.y
        if (type == PointType.inflectionFirst){
            pointF.y = random.nextInt(mHeight/2);
        }else {
            pointF.y = random.nextInt(mHeight/2)+mHeight/2;
        }

        return pointF;
    }


}
