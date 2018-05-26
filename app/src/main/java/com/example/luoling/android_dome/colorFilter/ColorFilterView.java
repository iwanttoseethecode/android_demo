package com.example.luoling.android_dome.colorFilter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.EmbossMaskFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.luoling.android_dome.R;

public class ColorFilterView extends View {
    public ColorFilterView(Context context) {
        super(context);
        init();
    }

    public ColorFilterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    Paint paint;
    Bitmap bitmap;

    FilterDraw mFilterDraw;
    ViewType viewType = ViewType.BlurMaskFilter;

    private void init(){
        paint = new Paint();
        paint.setColor(Color.RED);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.p4);
        mFilterDraw = new BlurMaskFilterDraw();
    }

    public void setViewType(ViewType viewType,int mode) {
        this.viewType = viewType;

        if (viewType.equals(ViewType.BlurMaskFilter)) {
            mFilterDraw = new BlurMaskFilterDraw();
        }else if (viewType.equals(ViewType.EmbossMaskFilter)){
            mFilterDraw = new EmbossMaskFilterDraw();
        }else if (viewType.equals(ViewType.ColorMatrixColorFilter)){
            mFilterDraw = new ColorMatrixFilterDraw();
        }else if (viewType.equals(ViewType.LightingColorFilter)){
            mFilterDraw = new LightingColorFilterDraw();
        }else if (viewType.equals(ViewType.PorterDuffColorFilter)){
            mFilterDraw = new PorterDuffColorFilterDraw();
        }
        mFilterDraw.setMode(mode);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int finalWidth = 0;
        int finalHeight = 0;

        if (widthMode == MeasureSpec.EXACTLY){
            finalWidth = width;
        }else if (heightMode == MeasureSpec.AT_MOST){
            finalWidth = 1080;
        }

        if (heightMode == MeasureSpec.EXACTLY){
            finalHeight = height;
        }else if (heightMode == MeasureSpec.AT_MOST){
            finalHeight = 1920;
        }

        setMeasuredDimension(finalWidth,finalHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mFilterDraw.onDraw(canvas);
    }

    interface FilterDraw{
        void onDraw(Canvas canvas);
        void setMode(int mode);
    }

    //在一个区间取中间数
    private int getRangeNum(int num,int max,int min){
        return Math.min(Math.max(num,min),max);
    }

    class BlurMaskFilterDraw implements FilterDraw {

        int mode = 0;

        @Override
        public void onDraw(Canvas canvas) {
            setLayerType(View.LAYER_TYPE_SOFTWARE,null);
            RectF rectF = new RectF(100,100,bitmap.getWidth(),bitmap.getHeight());
            paint.reset();
            paint.setColor(Color.RED);

            canvas.drawRect(rectF,paint);

            RectF rectF1 = new RectF(100,300 + bitmap.getHeight(),bitmap.getWidth(),bitmap.getHeight());

            //BlurMaskFilter.Blur.NORMAL,BlurMaskFilter.Blur.INNER,BlurMaskFilter.Blur.OUTER,BlurMaskFilter.Blur.SOLID
            BlurMaskFilter.Blur[] blurs = BlurMaskFilter.Blur.values();
            mode = getRangeNum(mode,blurs.length,0);

            //给图形添加阴影模糊
            paint.setMaskFilter(new BlurMaskFilter(30,blurs[mode]));
            canvas.drawRect(rectF1,paint);
        }

        @Override
        public void setMode(int mode) {
            this.mode = mode;
        }
    }

    class EmbossMaskFilterDraw implements FilterDraw{

        @Override
        public void onDraw(Canvas canvas) {
            RectF rectF = new RectF(100,100,bitmap.getWidth(),bitmap.getHeight());
            paint.reset();
            paint.setColor(Color.RED);
            canvas.drawRect(rectF,paint);

            /**
             * @param direction  指定光源的位置，长度为xxx的数组标量[x,y,z]
             * @param ambient    环境光的因子 （0~1），越接近0，环境光越暗
             * @param specular   镜面反射系数 越接近0，镜面反射越强
             * @param blurRadius 模糊半径 值越大，模糊效果越明显
             */
            paint.setMaskFilter(new EmbossMaskFilter(new float[]{1,1,1},0.2f,60,80));

            RectF rectF1 = new RectF(100,300 + bitmap.getHeight(),bitmap.getWidth(),bitmap.getHeight());
            canvas.drawRect(rectF1,paint);
        }

        @Override
        public void setMode(int mode) {

        }
    }

    class ColorMatrixFilterDraw implements FilterDraw{

        int mode = 0;

        @Override
        public void onDraw(Canvas canvas) {
            paint.reset();
//            paint.setColor(Color.RED);
            canvas.drawBitmap(bitmap,100,100,paint);

            mode = getRangeNum(mode,9,0);
            ColorMatrix colorMatrix = getColorMatrix(mode);
            paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
            canvas.drawBitmap(bitmap,100,300 + bitmap.getHeight(),paint);
        }

        @Override
        public void setMode(int mode) {
            this.mode = mode;
        }

        private ColorMatrix getColorMatrix(int mode){
            ColorMatrix colorMatrix = null;
            switch (mode){
                case 0:
                    //平移运算--加法，给green通道添加颜色值
                    colorMatrix = new ColorMatrix(new float[]{
                            1,0,0,0,0,
                            0,1,0,0,100,
                            0,0,1,0,0,
                            0,0,0,1,0
                    });
                    break;
                case 1:
                    //相反效果 -- 底片效果
                    colorMatrix = new ColorMatrix(new float[]{
                            -1,0,0,0,0,
                            0,-1,0,0,0,
                            0,0,-1,0,0,
                            0,0,0,-1,0
                    });
                    break;
                case 2:
                    //缩放运算--颜色增强
                    colorMatrix = new ColorMatrix(new float[]{
                            1.2f,0,0,0,0,
                            0,1.2f,0,0,0,
                            0,0,1.2f,0,0,
                            0,0,0,1.2f,0
                    });
                    break;
                case 3:
                    /*
                    * 黑白照片
                    * 去色原理：只要把rgb三个颜色通道的色彩信息设置成一样，那么图像就会变成灰色，同时为了保证图像亮度不变，同一个通道里的R+G+B = 1
                    * */
                    colorMatrix = new ColorMatrix(new float[]{
                            0.213f,0.715f,0.072f,0,0,
                            0.213f,0.715f,0.072f,0,0,
                            0.213f,0.715f,0.072f,0,0,
                            0,0,0,1,0
                    });
                    break;
                case 4:
                    //发色效果 ---（比如红色和绿色交换）
                    colorMatrix = new ColorMatrix(new float[]{
                            0,1,0,0,0,
                            1,0,0,0,0,
                            0,0,1,0,0,
                            0,0,0,1,0
                    });
                    break;
                case 5:
                    //复古效果
                    colorMatrix = new ColorMatrix(new float[]{
                            1/2f,1/2f,1/2f,0,0,
                            1/3f,1/3f,1/3f,0,0,
                            1/4f,1/4f,1/4f,0,0,
                            0,0,0,1,0
                    });
                    break;
                case 6:
                    //颜色通道过滤
                    colorMatrix = new ColorMatrix(new float[]{
                            1,0,0,0,0,
                            0,0,0,0,0,
                            0,0,0,0,0,
                            0,0,0,1,0
                    });
                    break;
                case 7:
                    //将一个颜色安系数进行放缩
                    colorMatrix = new ColorMatrix();
                    colorMatrix.setScale(1.2f,1.2f,1.2f,1);

                    break;
                case 8:
                    //设置饱和度
                    colorMatrix = new ColorMatrix();
                    colorMatrix.setSaturation(0.5f);
                    break;
                case 9:
                    colorMatrix = new ColorMatrix();
                    //aixs-- 0 红色轴，1，绿色，2，蓝色
                    // degrees -- 旋转的角度
                    colorMatrix.setRotate(0,50);
                    break;
            }
            return colorMatrix;
        }
    }

    class LightingColorFilterDraw implements FilterDraw{

        int mode = 0;

        @Override
        public void onDraw(Canvas canvas) {
            paint.reset();
//            paint.setColor(Color.RED);
            canvas.drawBitmap(bitmap,100,100,paint);

            mode = getRangeNum(mode,4,0);
            paint.setColorFilter(getColorFilter(mode));
            canvas.drawBitmap(bitmap,100,300 + bitmap.getHeight(),paint);
        }

        private ColorFilter getColorFilter(int mode){
            //修改颜色亮度
            ColorFilter colorFilter = null;
            switch (mode){
                case 0:
                    colorFilter = new LightingColorFilter(0x800000,0x3f0000);
                    break;
                case 1:
                    colorFilter = new LightingColorFilter(0x008000,0x003f00);
                    break;
                case 2:
                    colorFilter = new LightingColorFilter(0x000080,0x00003f);
                    break;
                case 3:
                    colorFilter = new LightingColorFilter(0x808080,0x3f3f3f);
                    break;
            }

            return colorFilter;
        }

        @Override
        public void setMode(int mode) {
            this.mode = mode;
        }
    }

    class PorterDuffColorFilterDraw implements FilterDraw{
        int mode = 0;
        @Override
        public void onDraw(Canvas canvas) {
            paint.reset();
//            paint.setColor(Color.RED);
            canvas.drawBitmap(bitmap,100,100,paint);

            mode = getRangeNum(mode,4,0);
            paint.setColorFilter(getColorFilter(mode));
            canvas.drawBitmap(bitmap,100,300 + bitmap.getHeight(),paint);
        }

        private PorterDuffColorFilter getColorFilter(int mode){
            PorterDuffColorFilter porterDuffColorFilter = null;
            switch (mode){
                case 0:
                    porterDuffColorFilter = new PorterDuffColorFilter(Color.BLUE, PorterDuff.Mode.DST_IN);
                    break;
                case 1:
                    porterDuffColorFilter = new PorterDuffColorFilter(Color.GREEN,PorterDuff.Mode.SRC_ATOP);
                    break;
                case 2:
                    porterDuffColorFilter = new PorterDuffColorFilter(Color.RED,PorterDuff.Mode.SRC_IN);
                    break;
            }
            return porterDuffColorFilter;
        }

        @Override
        public void setMode(int mode) {
            this.mode = mode;
        }
    }

    enum ViewType{
        BlurMaskFilter,
        EmbossMaskFilter,
        ColorMatrixColorFilter,
        LightingColorFilter,
        PorterDuffColorFilter
    }
}
