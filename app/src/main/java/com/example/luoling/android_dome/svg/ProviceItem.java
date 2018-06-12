package com.example.luoling.android_dome.svg;

import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.graphics.Region;
import android.text.TextUtils;

public class ProviceItem {
    private Path path;
    private int drawColor;
    private String name;
    private ColorMatrix lightColorMatrix;
    private ColorMatrix darkColorMatrix;
    private PathMeasure pathMeasure;

    public ProviceItem(Path path,String name){
        this.path = path;
        this.name = name;
        pathMeasure = new PathMeasure(path,true);
        generateColorMatrix();
    }

    private void generateColorMatrix(){
        lightColorMatrix = new ColorMatrix(new float[]{
                1.2f,0,0,0,0,
                0,1.2f,0,0,0,
                0,0,1.2f,0,0,
                0,0,0,1.2f,0,
        });
        darkColorMatrix = new ColorMatrix(new float[]{
                0.8f,0,0,0,0,
                0,0.8f,0,0,0,
                0,0,0.8f,0,0,
                0,0,0,0.8f,0,
        });
    }

    public void drawItem(Canvas canvas, Paint paint,float fraction,boolean isSelect){
        paint.reset();
        if (isSelect){
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            paint.setStrokeWidth(3);
            paint.setColor(drawColor);
            paint.setColorFilter(new ColorMatrixColorFilter(lightColorMatrix));
            paint.setShadowLayer(10,4,4,0xffffff);
            canvas.drawPath(path,paint);

            Path dst = new Path();


            //排除那些很短的path,某些省份的岛路径很短，动画一直在这个岛的path上执行，看不到效果
            {
                do {

                    if (TextUtils.equals(name,"河北")){
                        if (pathMeasure.getLength() > 40f){
                            break;
                        }
                    }else{
                        if (pathMeasure.getLength() > 15f){
                            break;
                        }
                    }
                }while (pathMeasure.nextContour());

            }
            float length = pathMeasure.getLength();
            float segment = length/10;
            float start = length * fraction;
            float stop = start + segment;
            pathMeasure.getSegment(start,stop,dst,true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColorFilter(new ColorMatrixColorFilter(darkColorMatrix));
            canvas.drawPath(dst,paint);
        }else{
            //非选中时
            paint.clearShadowLayer();
            paint.setAntiAlias(true);
            paint.setColor(drawColor);
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(1);
            canvas.drawPath(path,paint);

            paint.setStyle(Paint.Style.STROKE);
            int strokeColor = 0xffd0e8f4;
            paint.setColor(strokeColor);
            canvas.drawPath(path,paint);
        }
    }

    public boolean isTouch(float x,float y){
        RectF rectF = new RectF();
        //计算控制点的边界
        path.computeBounds(rectF,true);

        Region region = new Region();
        //setPath方法看英文注释。
        region.setPath(path,new Region((int) rectF.left,(int)rectF.top,(int)rectF.right,(int)rectF.bottom));
        return region.contains((int) x,(int) y);
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public int getDrawColor() {
        return drawColor;
    }

    public void setDrawColor(int drawColor) {
        this.drawColor = drawColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
