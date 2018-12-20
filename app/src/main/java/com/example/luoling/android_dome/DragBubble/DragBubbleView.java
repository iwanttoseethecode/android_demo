package com.example.luoling.android_dome.DragBubble;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.PointFEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.example.luoling.android_dome.R;

/**
 *
 */
public class DragBubbleView extends View {

    /**
     * 气泡默认状态--静止
     */
    private final int BUBBLE_STATE_DEFAUL = 0;
    /**
     * 气泡相连
     */
    private final int BUBBLE_STATE_CONNECT = 1;
    /**
     * 气泡分离
     */
    private final int BUBBLE_STATE_APART = 2;
    /**
     * 气泡消失
     */
    private final int BUBBLE_STATE_DISMISS = 3;

    /**
     * 气泡半径
     */
    private float mBubbleRadius;
    /**
     * 气泡颜色
     */
    private int mBubbleColor;
    /**
     * 气泡消息文字
     */
    private String mTextStr;
    /**
     * 气泡消息文字的颜色
     */
    private int mTextColor;
    /**
     * 气泡消息文字的大小
     */
    private float mTextSize;
    /**
     * 不动气泡的半径
     */
    private float mBubStillRadius;
    /**
     * 可动气泡的半径
     */
    private float mBubMoveableRadius;
    /**
     * 不动气泡的圆心
     */
    private PointF mBubStillCenter;
    /**
     * 可动气泡的圆心
     */
    private PointF mBubMoveableCenter;
    /**
     * 气泡的画笔
     */
    private Paint mBubblePaint;
    /**
     * 贝塞尔曲线path
     */
    private Path mBezierPath;

    private Paint mTextPaint;
    private Rect mTextRect;
    private Paint mBurstPaint;
    private RectF mBurstRect;

    /**
     * 气泡状态标志
     */
    private int mBubbleState = BUBBLE_STATE_DEFAUL;
    /**
     * 两气泡圆心距离
     */
    private float mDist;
    /**
     * 气泡相连状态最大圆心距离
     */
    private float mMaxDist;
    /**
     * 手指触摸，默认偏移量，超过这个值才认为是发生了触摸移动
     */
    private float MOVE_OFFSET;
    /**
     * 气泡爆炸bitmap数组
     */
    private Bitmap[] mBurstBitmapsArray;
    /**
     * 是否在执行气泡爆炸动画
     */
    private boolean mIsBurstAnimStart = false;
    /**
     * 当前气泡爆炸图片index
     */
    private int mCurDrawableIndex;
    /**
     * 气泡爆炸的图片id数组
     */
    private int[] mBurstDrawablesArray = {R.mipmap.burst_1,R.mipmap.burst_2,R.mipmap.burst_3,R.mipmap.burst_4,R.mipmap.burst_5};

    public DragBubbleView(Context context) {
        this(context,null);
    }

    public DragBubbleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DragBubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public DragBubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.DragBubbleView,defStyleAttr,0);
        mBubbleRadius = array.getDimension(R.styleable.DragBubbleView_bubble_radius,0);
        mBubbleColor = array.getColor(R.styleable.DragBubbleView_bubble_color, Color.RED);
        mTextColor = array.getColor(R.styleable.DragBubbleView_bubble_textColor,Color.WHITE);
        mTextSize = array.getDimension(R.styleable.DragBubbleView_bubble_textSize,0);
        mTextStr = array.getString(R.styleable.DragBubbleView_bubble_text);

        array.recycle();

        mBubStillRadius = mBubbleRadius;
        mBubMoveableRadius = mBubStillRadius;
        mMaxDist = 8*mBubbleRadius;
        MOVE_OFFSET = mMaxDist /4;

        mBubblePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBubblePaint.setColor(mBubbleColor);
        mBubblePaint.setStyle(Paint.Style.FILL);

        mBezierPath = new Path();

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);

        mTextRect = new Rect();

        mBurstPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBurstPaint.setFilterBitmap(true);
        mBurstRect = new RectF();
        mBurstBitmapsArray = new Bitmap[mBurstDrawablesArray.length];
        for (int i = 0; i < mBurstDrawablesArray.length; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),mBurstDrawablesArray[i]);
            mBurstBitmapsArray[i] = bitmap;
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initView(w,h);
    }

    private void initView(int w, int h) {
        if (mBubStillCenter == null){
            mBubStillCenter = new PointF(w/2,h/2);
        }else{
            mBubStillCenter.set(w/2,h/2);
        }

        if (mBubMoveableCenter == null){
            mBubMoveableCenter = new PointF(w/2,h/2);
        }else{
            mBubMoveableCenter.set(w/2,h/2);
        }
        mBubbleState = BUBBLE_STATE_DEFAUL;
    }

    interface DragBubbleBindParentListenter{
        void changeParent();
        void resoreParent();
        void removeParent();
    }

    private DragBubbleBindParentListenter mDragBubbleBindParentListenter;
    public void setDragBubbleBindParentListenter(DragBubbleBindParentListenter dragBubbleBindParentListenter){
        this.mDragBubbleBindParentListenter = dragBubbleBindParentListenter;
    }

    private int windowX = 0;
    private int windowY = 0;

    public int getWindowX() {
        return windowX;
    }

    public void setWindowX(int windowX) {
        this.windowX = windowX;
    }

    public int getWindowY() {
        return windowY;
    }

    public void setWindowY(int windowY) {
        this.windowY = windowY;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            {
                if(mBubbleState != BUBBLE_STATE_DISMISS){
                    if (mDragBubbleBindParentListenter != null){
                        mDragBubbleBindParentListenter.changeParent();
                    }
                    mDist = (float) Math.hypot(event.getX() - mBubStillCenter.x,
                            event.getY() - mBubStillCenter.y);
                    if(mDist < mBubbleRadius + MOVE_OFFSET){
                        // 加上MOVE_OFFSET是为了方便拖拽
                        mBubbleState = BUBBLE_STATE_CONNECT;
                    }else{
                        mBubbleState = BUBBLE_STATE_DEFAUL;
                    }
                }
            }
            break;

            case MotionEvent.ACTION_MOVE:
            {
                if(mBubbleState != BUBBLE_STATE_DEFAUL){
                    mBubMoveableCenter.x = event.getX();
                    mBubMoveableCenter.y = event.getY();
                    mDist = (float) Math.hypot(event.getX() - mBubStillCenter.x,
                            event.getY() - mBubStillCenter.y);
                    if(mBubbleState == BUBBLE_STATE_CONNECT){
                        // 减去MOVE_OFFSET是为了让不动气泡半径到一个较小值时就直接消失
                        // 或者说是进入分离状态
                        if(mDist < mMaxDist - MOVE_OFFSET){

                            mBubStillRadius = mBubbleRadius - mDist / 8;
                        }else{
                            mBubbleState = BUBBLE_STATE_APART;
                        }
                    }
                    invalidate();
                }
            }
            break;

            case MotionEvent.ACTION_UP:
            {
                if(mBubbleState == BUBBLE_STATE_CONNECT){
                    startBubbleResetAnim();
                }else if(mBubbleState == BUBBLE_STATE_APART){
                    if(mDist < 2 * mBubbleRadius){
                        startBubbleResetAnim();
                    }else{
                        startBubbleBurstAnim();
                    }
                }
            }
            break;
        }
        return true;
    }

    private void startBubbleBurstAnim() {
        mBubbleState = BUBBLE_STATE_DISMISS;
        mIsBurstAnimStart = true;
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0,mBurstBitmapsArray.length);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurDrawableIndex = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mIsBurstAnimStart = false;
                if (mDragBubbleBindParentListenter != null){
                    mDragBubbleBindParentListenter.removeParent();
                }
            }
        });
        valueAnimator.start();
    }

    private void startBubbleResetAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new PointFEvaluator(),
                new PointF(mBubMoveableCenter.x,mBubMoveableCenter.y),
                new PointF(mBubStillCenter.x,mBubStillCenter.y));
        valueAnimator.setDuration(1600);
        valueAnimator.setInterpolator(new OvershootInterpolator(2f));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mBubMoveableCenter = (PointF) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mBubbleState = BUBBLE_STATE_DEFAUL;
                if (mDragBubbleBindParentListenter != null){
                    mDragBubbleBindParentListenter.resoreParent();
                }
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画相连的气泡状态
        if (mBubbleState == BUBBLE_STATE_CONNECT){
            //画静止的气泡
            canvas.drawCircle(mBubStillCenter.x,mBubStillCenter.y,mBubStillRadius,mBubblePaint);
            //画两个气泡相连的部分，利用到了二阶贝塞尔曲线
            //计算控制点
            int anchorX = (int) ((mBubStillCenter.x + mBubMoveableCenter.x)/2);
            int anchorY = (int) ((mBubStillCenter.y + mBubMoveableCenter.y) / 2);

            //计算两个圆相连的切点，一共有四个，这四个点可以通过三角函数得到。

            int iAnchorX = (int) ((mBubStillCenter.x + mBubMoveableCenter.x) / 2);
            int iAnchorY = (int) ((mBubStillCenter.y + mBubMoveableCenter.y) / 2);

            float cosTheta = (mBubMoveableCenter.x - mBubStillCenter.x) / mDist;
            float sinTheta = (mBubMoveableCenter.y - mBubStillCenter.y) / mDist;

            float iBubStillStartX = mBubStillCenter.x - mBubStillRadius * sinTheta;
            float iBubStillStartY = mBubStillCenter.y + mBubStillRadius * cosTheta;
            float iBubMoveableEndX = mBubMoveableCenter.x - mBubMoveableRadius * sinTheta;
            float iBubMoveableEndY = mBubMoveableCenter.y + mBubMoveableRadius * cosTheta;
            float iBubMoveableStartX = mBubMoveableCenter.x + mBubMoveableRadius * sinTheta;
            float iBubMoveableStartY = mBubMoveableCenter.y - mBubMoveableRadius * cosTheta;
            float iBubStillEndX = mBubStillCenter.x + mBubStillRadius * sinTheta;
            float iBubStillEndY = mBubStillCenter.y - mBubStillRadius * cosTheta;

            mBezierPath.reset();
            // 画上半弧
            mBezierPath.moveTo(iBubStillStartX,iBubStillStartY);
            mBezierPath.quadTo(iAnchorX,iAnchorY,iBubMoveableEndX,iBubMoveableEndY);
            // 画下半弧
            mBezierPath.lineTo(iBubMoveableStartX,iBubMoveableStartY);
            mBezierPath.quadTo(iAnchorX,iAnchorY,iBubStillEndX,iBubStillEndY);
            mBezierPath.close();
            canvas.drawPath(mBezierPath,mBubblePaint);
        }

        //画拖拽的气泡和文字
        if (mBubbleState != BUBBLE_STATE_DISMISS){
            canvas.drawCircle(mBubMoveableCenter.x,mBubMoveableCenter.y,mBubMoveableRadius,mBubblePaint);

            mTextPaint.getTextBounds(mTextStr,0,mTextStr.length(),mTextRect);
            Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
            float textBaseLine = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom + mBubMoveableCenter.y;
            canvas.drawText(mTextStr,mBubMoveableCenter.x - mTextRect.width()/2,textBaseLine,mTextPaint);
        }

        // 画消失状态
        if (mIsBurstAnimStart){
            mBurstRect.set(mBubMoveableCenter.x - mBubMoveableRadius,
                    mBubMoveableCenter.y - mBubMoveableRadius,
                    mBubMoveableCenter.x + mBubMoveableRadius,
                    mBubMoveableCenter.y + mBubMoveableRadius);
            canvas.drawBitmap(mBurstBitmapsArray[mCurDrawableIndex],null,mBurstRect,mBubblePaint);
        }
    }

    public void reset(){
        initView(getWidth(),getHeight());
        invalidate();
        if (mDragBubbleBindParentListenter != null){
            mDragBubbleBindParentListenter.resoreParent();
        }
    }
}
