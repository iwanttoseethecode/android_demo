package com.example.luoling.android_dome.Graphics2D;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

import com.example.luoling.android_dome.R;

/**
 * Created by luoling on 2016/9/16.
 */
public class ClipView extends View {

    private int serialNnumber = 0;

    public ClipView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.aodi);
        //绘制图片
        canvas.drawBitmap(bmp,0,0,null);
        //平移坐标
        canvas.translate(0,600);
        //定义剪切区
        canvas.clipRect(new Rect(100,50,400,400));
        Path path = new Path();
        path.addCircle(400,250,200,Path.Direction.CCW);
        switch(serialNnumber){
            case 0:
                break;
            case 1:
                canvas.clipPath(path, Region.Op.DIFFERENCE);
                break;
            case 2:
                canvas.clipPath(path, Region.Op.REVERSE_DIFFERENCE);
                break;
            case 3:
                canvas.clipPath(path, Region.Op.INTERSECT);
                break;
            case 4:
                canvas.clipPath(path, Region.Op.REPLACE);
                break;
            case 5:
                canvas.clipPath(path, Region.Op.UNION);
                break;
            case 6:
                canvas.clipPath(path, Region.Op.XOR);
                break;
        }
        canvas.drawBitmap(bmp,0,0,null);
    }
    public void setSerialNnumber(int type){
        serialNnumber = type;
    }
}
