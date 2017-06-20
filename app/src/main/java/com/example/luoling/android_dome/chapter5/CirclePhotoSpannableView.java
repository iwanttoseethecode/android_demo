package com.example.luoling.android_dome.chapter5;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.example.luoling.android_dome.R;

/**
 * Created by luoling on 2016/12/28.
 */

public class CirclePhotoSpannableView extends View {

    private Bitmap bmpWoman;
    private Bitmap bmpCircle;
    private Canvas cvsCricle;
    private Paint paint;

    public CirclePhotoSpannableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bmpWoman = BitmapFactory.decodeResource(getResources(), R.mipmap.woman);
        int length = Math.min(bmpWoman.getWidth(), bmpWoman.getHeight());
        bmpCircle = Bitmap.createBitmap(length,length, Bitmap.Config.ARGB_8888);
        cvsCricle = new Canvas(bmpCircle);
        int r = length/2;
        cvsCricle.drawCircle(r,r,r,paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int length = cvsCricle.getWidth();
        int layer = canvas.saveLayer(100,100,length+100,length+100,null,Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(bmpWoman,100,100,null);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(bmpCircle,100,100,paint);
        canvas.restoreToCount(layer);
        paint.setXfermode(null);
    }
}
