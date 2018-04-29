package com.example.luoling.android_dome.customView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.example.luoling.android_dome.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
* Paint 尽量不要大量创建
* */

public class first_Paint_Canvas_Activity extends Activity {

    @BindView(R.id.iv)
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first__paint__canvas_);
        ButterKnife.bind(this);
        initView();
    }

    public void initView() {

    }


    private int sptopx(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    @OnClick({R.id.btn1, R.id.btn2,R.id.btn3,R.id.btn4,R.id.btn5,R.id.btn6,R.id.btn7,R.id.btn8})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                btn1();
                break;
            case R.id.btn2:
                btn2();
                break;
            case R.id.btn3:
                btn3();
                break;
            case R.id.btn4:
                btn4();
            case R.id.btn5:
                btn5();
                break;
            case R.id.btn6:
                btn6();
                break;
            case R.id.btn7:
                btn7();
                break;
            case R.id.btn8:
                btn8();
                break;
        }
    }

    public void btn1() {
        Bitmap bmp = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(sptopx(14));
        paint.setTextSkewX(0.5f);
        paint.setUnderlineText(true);
        paint.setFakeBoldText(true);
        canvas.drawText("天气很好，可以试试放松放松", 10, 50, paint);

        //绘制图形
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(20);
        paint.setStrokeJoin(Paint.Join.BEVEL);
        //绘制一个矩形
        canvas.drawRect(new Rect(50, 100, 350, 350), paint);
        iv.setImageBitmap(bmp);
    }

    public void btn2() {
        Bitmap bmpBuffer = Bitmap.createBitmap(500, 800, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmpBuffer);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        //原大小绘制
        canvas.drawBitmap(bmp, 0, 0, null);
        //对图片进行放缩
        int bmpWidth = bmp.getWidth();
        int bmpHeight = bmp.getHeight();
        Rect src = new Rect(0, 0, bmpWidth, bmpHeight);
        Rect dst = new Rect(0, bmpHeight, bmpWidth * 3, bmpHeight * 3 + bmpHeight);
        //从bitmap中抠出一块大小区域为src的图片并绘制到canvas的dst处
        canvas.drawBitmap(bmp, src, dst, null);

        iv.setImageBitmap(bmpBuffer);
    }

    public void btn3(){
        Bitmap bmpBuffer = Bitmap.createBitmap(500,800,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmpBuffer);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(4);
        //画个红色的点
        canvas.drawPoint(120,20,paint);
        paint.setColor(Color.BLUE);
        //两个数一组画4个蓝色的点
        float[] points1 = new float[]{10,10,50,50,50,100,50,150};
        canvas.drawPoints(points1,paint);
        //canvas.drawPoints(points1,1,4,paint);
        paint.setColor(Color.GREEN);
        iv.setImageBitmap(bmpBuffer);
    }

    public void btn4(){
        Bitmap bmpBuffer = Bitmap.createBitmap(500,800,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmpBuffer);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(1);
        final int offsetDy = 50;
        for(int i = 0;i<5;i++){
            canvas.drawLine(10,offsetDy*i,300,offsetDy*i,paint);
        }
        iv.setImageBitmap(bmpBuffer);
    }

    public void btn5(){
        Bitmap bmpBuffer = Bitmap.createBitmap(500,800,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmpBuffer);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawRect(10,10,400,300,paint);
//        30，30分别代表水平半径和垂直半径的椭圆
//        canvas.drawRoundRect(10,10,400,300,30,30,paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        canvas.drawRect(new Rect(10,320,400,620),paint);
        iv.setImageBitmap(bmpBuffer);
    }

   public void btn6(){
       Bitmap bmpBuffer = Bitmap.createBitmap(500,800,Bitmap.Config.ARGB_8888);
       Canvas canvas = new Canvas(bmpBuffer);
       Paint paint = new Paint();
       paint.setColor(Color.RED);
       paint.setStyle(Paint.Style.STROKE);
       paint.setStrokeWidth(4);
//       canvas.drawOval(10,10,300,400,paint);
       canvas.drawOval(new RectF(10,10,400,300),paint);
       paint.setStyle(Paint.Style.FILL);
       canvas.drawCircle(200,450,100,paint);
       iv.setImageBitmap(bmpBuffer);
   }

    public void btn7(){
        Bitmap bmpBuffer = Bitmap.createBitmap(500,1000,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmpBuffer);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        //参数startAngle表示起始角度，sweep表示扇形或弧线所占有的角度，正数表示顺时针，负数表示逆时针。userCenter表示是否使用中心点，true表示扇形，false表示弧线。
        canvas.drawArc(new RectF(10,10,400,300),30,60,false,paint);
        canvas.drawArc(new RectF(10,310,400,600),30,60,true,paint);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawArc(new RectF(10,610,400,900),30,60,true,paint);
        iv.setImageBitmap(bmpBuffer);
    }

    public void btn8(){
        Bitmap bmpBuffer = Bitmap.createBitmap(500,800,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmpBuffer);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(0,150);
        path.rLineTo(300,0);
        path.rLineTo(-300,150);
        path.rLineTo(150,-300);
        path.rLineTo(150,300);
        path.close();//在第一个点和最后一个点之间画一条直线，形成闭合区域。
        canvas.drawPath(path,paint);
        iv.setImageBitmap(bmpBuffer);
    }

}
