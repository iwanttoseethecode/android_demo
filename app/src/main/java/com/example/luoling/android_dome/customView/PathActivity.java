package com.example.luoling.android_dome.customView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.luoling.android_dome.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PathActivity extends AppCompatActivity {

    @BindView(R.id.iv)
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first__paint__canvas_);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8,R.id.btn9, R.id.btn10, R.id.btn11, R.id.btn12})
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
                break;
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
            case R.id.btn9:
                btn9();
                break;
            case R.id.btn10:
                btn10();
                break;
            case R.id.btn11:
                btn11();
                break;
            case R.id.btn12:
                break;
        }
    }

    public void btn1() {
        Bitmap bmpBuffer = Bitmap.createBitmap(500, 800, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmpBuffer);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(0, 150);
        path.rLineTo(300, 0);
        path.rLineTo(-300, 150);
        path.rLineTo(150, -300);
        path.rLineTo(150, 300);
        path.close();//在第一个点和最后一个点之间画一条直线，形成闭合区域。
        canvas.drawPath(path, paint);
        iv.setImageBitmap(bmpBuffer);
    }

    public void btn2() {
        Bitmap bmpBuffer = Bitmap.createBitmap(500, 800, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmpBuffer);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        //往Path对象中添加矩形椭圆园弧，需要调用Path类中定义的一组以“add”开头的方法，这组方法
        //有些需要传递一个类型为path.Direction的参数，这是一个枚举类型，枚举值cw表示顺时针，ccw
        //表示逆时针。

        //矩形
        path.addRect(new RectF(10, 10, 300, 100), Path.Direction.CCW);
        //圆角矩形
        path.addRoundRect(new RectF(10, 120, 300, 220), new float[]{10, 20, 20, 10, 30, 40, 40, 30}, Path.Direction.CCW);
        //椭圆
        path.addOval(new RectF(10, 240, 300, 340), Path.Direction.CCW);
        //圆
        path.addCircle(60, 390, 50, Path.Direction.CCW);
        //弧线
        path.addArc(new RectF(10, 500, 300, 600), -30, -60);
        canvas.drawPath(path, paint);
        iv.setImageBitmap(bmpBuffer);
    }

    public void btn3() {
        //贝塞尔曲线，如果要绘制一条二阶贝塞尔曲线，必须调用moveTo()方法定义起点，再调用quadTo()方法
        //绘制贝塞尔曲线。
        Bitmap bmpBuffer = Bitmap.createBitmap(550, 800, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmpBuffer);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(100, 100);
        path.quadTo(200, 10, 300, 300);
        canvas.drawPath(path, paint);
        //画点（100，100），（200，10），（300，300）
        paint.setStrokeWidth(4);
        paint.setColor(Color.RED);
        canvas.drawPoints(new float[]{100, 100, 200, 10, 300, 300}, paint);
        //绘制三阶赛贝尔曲线
        path.moveTo(100, 400);
        path.cubicTo(200, 250, 260, 200, 450, 550);
        canvas.drawPath(path, paint);
        iv.setImageBitmap(bmpBuffer);
    }

    public void btn4() {
        //三阶赛贝尔曲线有一个起点，2个控制点，一个终点
        Bitmap bmpBuffer = Bitmap.createBitmap(500, 800, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmpBuffer);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(100, 100);
        path.arcTo(new RectF(100, 150, 300, 300), -30, 60, false);
        canvas.drawPath(path, paint);
        iv.setImageBitmap(bmpBuffer);
    }

    public void btn5() {
        Bitmap bmpBuffer = Bitmap.createBitmap(500, 800, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmpBuffer);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        Path path1 = new Path();
        path1.addRect(new RectF(10, 10, 110, 110), Path.Direction.CCW);
        canvas.drawPath(path1, paint);
        Path path2 = new Path();
        path2.addCircle(100, 100, 50, Path.Direction.CCW);
        paint.setColor(Color.RED);
        canvas.drawPath(path2, paint);
        iv.setImageBitmap(bmpBuffer);
    }

    public void btn6() {
        Bitmap bmpBuffer = Bitmap.createBitmap(500, 800, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmpBuffer);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        Path path1 = new Path();
        path1.addRect(new RectF(10, 10, 110, 110), Path.Direction.CCW);
//        canvas.drawPath(path1,paint);
        Path path2 = new Path();
        path2.addCircle(100, 100, 50, Path.Direction.CCW);
        paint.setColor(Color.RED);
        path1.op(path2, Path.Op.DIFFERENCE);//差集
        canvas.drawPath(path1, paint);
        iv.setImageBitmap(bmpBuffer);
    }

    public void btn7() {
        Bitmap bmpBuffer = Bitmap.createBitmap(500, 800, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmpBuffer);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        Path path1 = new Path();
        path1.addRect(new RectF(10, 10, 110, 110), Path.Direction.CCW);
//        canvas.drawPath(path1,paint);
        Path path2 = new Path();
        path2.addCircle(100, 100, 50, Path.Direction.CCW);
        paint.setColor(Color.RED);
        path1.op(path2, Path.Op.INTERSECT);//交集
        canvas.drawPath(path1, paint);
        iv.setImageBitmap(bmpBuffer);
    }

    public void btn8() {
        Bitmap bmpBuffer = Bitmap.createBitmap(500, 800, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmpBuffer);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        Path path1 = new Path();
        path1.addRect(new RectF(10, 10, 110, 110), Path.Direction.CCW);
//        canvas.drawPath(path1,paint);
        Path path2 = new Path();
        path2.addCircle(100, 100, 50, Path.Direction.CCW);
        paint.setColor(Color.RED);
        path1.op(path2, Path.Op.REVERSE_DIFFERENCE);//反差集
        canvas.drawPath(path1, paint);
        iv.setImageBitmap(bmpBuffer);
    }

    public void btn9() {
        Bitmap bmpBuffer = Bitmap.createBitmap(500, 800, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmpBuffer);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        Path path1 = new Path();
        path1.addRect(new RectF(10, 10, 110, 110), Path.Direction.CCW);
//        canvas.drawPath(path1,paint);
        Path path2 = new Path();
        path2.addCircle(100, 100, 50, Path.Direction.CCW);
        paint.setColor(Color.RED);
        path1.op(path2, Path.Op.UNION);//并集
        canvas.drawPath(path1, paint);
        iv.setImageBitmap(bmpBuffer);
    }

    public void btn10() {
        Bitmap bmpBuffer = Bitmap.createBitmap(500, 800, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmpBuffer);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        Path path1 = new Path();
        path1.addRect(new RectF(10, 10, 110, 110), Path.Direction.CCW);
//        canvas.drawPath(path1,paint);
        Path path2 = new Path();
        path2.addCircle(100, 100, 50, Path.Direction.CCW);
        paint.setColor(Color.RED);
        path1.op(path2, Path.Op.XOR);//补集
        canvas.drawPath(path1, paint);
        iv.setImageBitmap(bmpBuffer);
    }

    public void btn11() {
        Bitmap bmpBuffer = Bitmap.createBitmap(500, 800, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmpBuffer);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(14);
        String text = "天宫二号,改时间烦恼无论是啊额，我爱android!";
        canvas.drawText(text,10,50,paint);
        canvas.drawText(text,0,4,10,100,paint);
        canvas.drawText(text.toCharArray(),5,10,10,150,paint);

        Path path = new Path();
        path.moveTo(10,200);
        path.cubicTo(100,100,300,300,400,200);
        canvas.drawTextOnPath(text,path,15,15,paint);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path,paint);
        iv.setImageBitmap(bmpBuffer);
    }

}
