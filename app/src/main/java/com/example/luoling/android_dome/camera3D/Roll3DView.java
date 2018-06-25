package com.example.luoling.android_dome.camera3D;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Roll3DView extends View {
    public Roll3DView(Context context) {
        super(context);
        init(context);
    }

    public Roll3DView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Roll3DView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private int viewWidth,viewHeight;
    private Context context;
    private Paint paint;
    private Camera camera;
    private Matrix matrix;

    private float rotateDegree = 0;
    private float axisX = 0,axisY = 0;
    private int partNumber = 1;
    private List<Bitmap> bitmapList;
    private Bitmap[][] bitmaps;
    //滚动方向，0是横向，1是纵向
    private int direction = 1;
    int averageWidth = 0,averageHeight = 0;
    private RollMode rollMode = RollMode.SepartConbine;
    private int preIndex = 0, currIndex = 0,nextIndex = 0;
    private ValueAnimator valueAnimator;
    private int rollDuration = 1*1000;
    //正在翻转
    private boolean rolling;

    public enum RollMode{
        Roll2D,Whole3D,SepartConbine,RollInTurn,Jalousie;
    }

    private void init(Context context) {
        bitmapList = new ArrayList<>();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        camera = new Camera();
        matrix = new Matrix();
    }

    private void initBitmaps(){
        if (viewHeight <= 0 && viewWidth <= 0){
            return;
        }
        if (null == bitmapList || bitmapList.size() <= 0){
            return;
        }
        bitmaps = new Bitmap[bitmapList.size()][partNumber];
        initIndex();

        averageWidth = (int) viewWidth/partNumber;
        averageHeight = (int) viewHeight/partNumber;
        Bitmap partBitmap;
        for (int i = 0; i < bitmapList.size(); i++) {
            for (int j = 0; j < partNumber; j++) {
                Rect rect;
                if (rollMode != RollMode.Jalousie) {
                    if (direction == 1) {//纵向分块
                        rect = new Rect(j * averageWidth, 0, (j + 1) * averageWidth, viewHeight);
                        partBitmap = getPartBitmap(bitmapList.get(i), j * averageWidth, 0, rect);
                    } else {//横向分块
                        rect = new Rect(0, j * averageHeight, viewWidth, (j + 1) * averageHeight);
                        partBitmap = getPartBitmap(bitmapList.get(i), 0, j * averageHeight, rect);
                    }
                } else {
                    if (direction == 1) {
                        rect = new Rect(0, j * averageHeight, viewWidth, (j + 1) * averageHeight);
                        partBitmap = getPartBitmap(bitmapList.get(i), 0, j * averageHeight, rect);
                    } else {
                        rect = new Rect(j * averageWidth, 0, (j + 1) * averageWidth, viewHeight);
                        partBitmap = getPartBitmap(bitmapList.get(i), j * averageWidth, 0, rect);
                    }
                }

                bitmaps[i][j] = partBitmap;
            }
        }
    }

    private Bitmap getPartBitmap(Bitmap bitmap,int x, int y,Rect rect) {
        Bitmap bitmap1 = Bitmap.createBitmap(bitmap,x,y,rect.width(),rect.height());
        return bitmap1;
    }

    private void initIndex() {
        int listSize = bitmapList.size();
        nextIndex = currIndex + 1;
        preIndex = currIndex - 1;
        if (nextIndex > listSize -1){
            nextIndex = 0;
        }
        if (preIndex < 0){
            preIndex = listSize -1;
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initBitmaps();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = getWidth();
        viewHeight = getHeight();

        if (viewWidth != 0 && viewHeight != 0){
            for (int i = 0; i < bitmapList.size(); i++){
                bitmapList.set(i,scaleBitmap(bitmapList.get(i)));
            }
        }
        initBitmaps();
    }

    private Bitmap scaleBitmap(Bitmap bitmap) {
        if (bitmap == null){
            return null;
        }
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        float scaleWidth = viewWidth / (float) width;
        float scaleHeight = viewHeight / (float) height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap,0,0,width,height,matrix,false);
        return newBitmap;
    }

    public void setRollMode(RollMode rollMode){
        this.rollMode = rollMode;
        initBitmaps();
    }

    public void setRollDuration(int rollDuration) {
        this.rollDuration = rollDuration;
        initBitmaps();
    }

    public void setDirection(int direction) {
        this.direction = direction;
        initBitmaps();
    }

    public void setPartNumber(int partNumber){
        this.partNumber = partNumber;
        initBitmaps();
    }

    public void addImageBitmap(Bitmap bitmap){
        bitmapList.add(bitmap);
        initBitmaps();
        invalidate();
    }


    public void removeBitmapAt(int index){
        bitmapList.remove(index);
    }

    public void setRotateDegree(float rotateDegree){
        this.rotateDegree = rotateDegree;
        if (direction == 1){
            axisY = rotateDegree / (float) (rollMode == RollMode.Jalousie ? 180 : 90) * viewHeight;
        }else{
            axisX =  rotateDegree / (float) (rollMode == RollMode.Jalousie ? 180 : 90) * viewWidth;
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (null == bitmapList || bitmapList.size() <= 0){
            return;
        }
        switch(rollMode){
            case Roll2D:
                drawRollWhole3D(canvas, true);
                break;
            case Whole3D:
                drawRollWhole3D(canvas, false);
                break;
            case SepartConbine:
                drawSepartConbine(canvas);
                break;
            case RollInTurn:
                drawRollInTurn(canvas);
                break;
            case Jalousie:
                drawJalousie(canvas);
                break;
        }
    }

    private void drawJalousie(Canvas canvas) {
        for (int i = 0; i < partNumber; i++) {
            Bitmap currBitmap = bitmaps[currIndex][i];
            Bitmap nextBitmap = bitmaps[nextIndex][i];

            canvas.save();
            //注意 百叶窗的翻转方向和其他模式是相反的  横向的时候纵翻  纵向的时候横翻
            if (direction == 1) {

                if (rotateDegree < 90) {
                    camera.save();
                    camera.rotateX(rotateDegree);
                    camera.getMatrix(matrix);
                    camera.restore();

                    matrix.preTranslate(-currBitmap.getWidth() / 2, -currBitmap.getHeight() / 2);
                    matrix.postTranslate(currBitmap.getWidth() / 2, currBitmap.getHeight() / 2 + i * averageHeight);
                    canvas.drawBitmap(currBitmap, matrix, paint);
                } else {
                    camera.save();
                    camera.rotateX(180 - rotateDegree);
                    camera.getMatrix(matrix);
                    camera.restore();

                    matrix.preTranslate(-nextBitmap.getWidth() / 2, -nextBitmap.getHeight() / 2);
                    matrix.postTranslate(nextBitmap.getWidth() / 2, nextBitmap.getHeight() / 2 + i * averageHeight);
                    canvas.drawBitmap(nextBitmap, matrix, paint);
                }


            } else {
                if (rotateDegree < 90) {
                    camera.save();
                    camera.rotateY(rotateDegree);
                    camera.getMatrix(matrix);
                    camera.restore();

                    matrix.preTranslate(-currBitmap.getWidth() / 2, -currBitmap.getHeight() / 2);
                    matrix.postTranslate(currBitmap.getWidth() / 2 + i * averageWidth, currBitmap.getHeight() / 2);
                    canvas.drawBitmap(currBitmap, matrix, paint);
                } else {
                    camera.save();
                    camera.rotateY(180 - rotateDegree);
                    camera.getMatrix(matrix);
                    camera.restore();

                    matrix.preTranslate(-nextBitmap.getWidth() / 2, -nextBitmap.getHeight() / 2);
                    matrix.postTranslate(nextBitmap.getWidth() / 2 + i * averageWidth, nextBitmap.getHeight() / 2);
                    canvas.drawBitmap(nextBitmap, matrix, paint);
                }

            }
            canvas.restore();
        }
    }

    private void drawRollInTurn(Canvas canvas) {
        for (int i = 0; i < partNumber; i++) {
            Bitmap currBitmap = bitmaps[currIndex][i];
            Bitmap nextBitmap = bitmaps[nextIndex][i];

            float tDegree = rotateDegree - i * 30;
            if (tDegree < 0)
                tDegree = 0;
            if (tDegree > 90)
                tDegree = 90;


            canvas.save();
            if (direction == 1) {
                float tAxisY = tDegree / 90f * viewHeight;
                if (tAxisY > viewHeight)
                    tAxisY = viewHeight;
                if (tAxisY < 0)
                    tAxisY = 0;

                camera.save();
                camera.rotateX(-tDegree);
                camera.getMatrix(matrix);
                camera.restore();

                matrix.preTranslate(-currBitmap.getWidth(), 0);
                matrix.postTranslate(currBitmap.getWidth() + i * averageWidth, tAxisY);
                canvas.drawBitmap(currBitmap, matrix, paint);

                camera.save();
                camera.rotateX((90 - tDegree));
                camera.getMatrix(matrix);
                camera.restore();

                matrix.preTranslate(-nextBitmap.getWidth(), -nextBitmap.getHeight());
                matrix.postTranslate(nextBitmap.getWidth() + i * averageWidth, tAxisY);
                canvas.drawBitmap(nextBitmap, matrix, paint);

            } else {
                float tAxisX = tDegree / 90f * viewWidth;
                if (tAxisX > viewWidth)
                    tAxisX = viewWidth;
                if (tAxisX < 0)
                    tAxisX = 0;
                camera.save();
                camera.rotateY(tDegree);
                camera.getMatrix(matrix);
                camera.restore();

                matrix.preTranslate(0, -currBitmap.getHeight() / 2);
                matrix.postTranslate(tAxisX, currBitmap.getHeight() / 2 + i * averageHeight);
                canvas.drawBitmap(currBitmap, matrix, paint);

                //
                camera.save();
                camera.rotateY(tDegree - 90);
                camera.getMatrix(matrix);
                camera.restore();

                matrix.preTranslate(-nextBitmap.getWidth(), -nextBitmap.getHeight() / 2);
                matrix.postTranslate(tAxisX, nextBitmap.getHeight() / 2 + i * averageHeight);
                canvas.drawBitmap(nextBitmap, matrix, paint);
            }
            canvas.restore();
        }
    }

    private void drawSepartConbine(Canvas canvas) {
        for (int i = 0; i < partNumber; i++) {
            Bitmap currBitmap = bitmaps[currIndex][i];
            Bitmap nextBitmap = bitmaps[nextIndex][i];

            canvas.save();
            if (direction == 1) {

                camera.save();
                camera.rotateX(-rotateDegree);
                camera.getMatrix(matrix);
                camera.restore();

                matrix.preTranslate(-currBitmap.getWidth() / 2, 0);
                matrix.postTranslate(currBitmap.getWidth() / 2 + i * averageWidth, axisY);
                canvas.drawBitmap(currBitmap, matrix, paint);

                camera.save();
                camera.rotateX((90 - rotateDegree));
                camera.getMatrix(matrix);
                camera.restore();

                matrix.preTranslate(-nextBitmap.getWidth() / 2, -nextBitmap.getHeight());
                matrix.postTranslate(nextBitmap.getWidth() / 2 + i * averageWidth, axisY);
                canvas.drawBitmap(nextBitmap, matrix, paint);

            } else {
                camera.save();
                camera.rotateY(rotateDegree);
                camera.getMatrix(matrix);
                camera.restore();

                matrix.preTranslate(0, -currBitmap.getHeight() / 2);
                matrix.postTranslate(axisX, currBitmap.getHeight() / 2 + i * averageHeight);
                canvas.drawBitmap(currBitmap, matrix, paint);

                camera.save();
                camera.rotateY(rotateDegree - 90);
                camera.getMatrix(matrix);
                camera.restore();

                matrix.preTranslate(-nextBitmap.getWidth(), -nextBitmap.getHeight() / 2);
                matrix.postTranslate(axisX, nextBitmap.getHeight() / 2 + i * averageHeight);
                canvas.drawBitmap(nextBitmap, matrix, paint);
            }
            canvas.restore();
        }
    }

    private void drawRollWhole3D(Canvas canvas, boolean draw2D) {
        Bitmap currWholeBitmap = bitmapList.get(currIndex);
        Bitmap nextWholeBitmap = bitmapList.get(nextIndex);
        canvas.save();

        if (direction == 1) {
            camera.save();
            if (draw2D)
                camera.rotateX(0);
            else
                camera.rotateX(-rotateDegree);
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(-viewWidth / 2, 0);
            matrix.postTranslate(viewWidth / 2, axisY);//下面的view绕着自己上边转，转完之后显示要往下平移 (rotateDegree/90)*viewHeight
            canvas.drawBitmap(currWholeBitmap, matrix, paint);

            camera.save();
            if (draw2D)
                camera.rotateX(0);
            else
                camera.rotateX((90 - rotateDegree));
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(-viewWidth / 2, -viewHeight);
            matrix.postTranslate(viewWidth / 2, axisY);//上面的view绕着自己下边转，转完之后显示要往上平移 (rotateDegree/90)*viewHeight
            canvas.drawBitmap(nextWholeBitmap, matrix, paint);

        } else {
            camera.save();
            if (draw2D)
                camera.rotateY(0);
            else
                camera.rotateY(rotateDegree);
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(0, -viewHeight / 2);
            matrix.postTranslate(axisX, viewHeight / 2);

            canvas.drawBitmap(currWholeBitmap, matrix, paint);

            camera.save();
            if (draw2D)
                camera.rotateY(0);
            else
                camera.rotateY(rotateDegree - 90);
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(-viewWidth, -viewHeight / 2);
            matrix.postTranslate(axisX, viewHeight / 2);
            canvas.drawBitmap(nextWholeBitmap, matrix, paint);
        }
        canvas.restore();
    }

    public void toNext(){
        if (rolling){
            return;
        }
        if (rollMode == RollMode.RollInTurn){
            valueAnimator = ValueAnimator.ofFloat(0,90 + (partNumber - 1) * 30);
        }else if (rollMode == RollMode.Jalousie){
            valueAnimator = ValueAnimator.ofFloat(0,180);
        }else {
            valueAnimator = ValueAnimator.ofFloat(0,90);
        }
        rolling = true;
        valueAnimator.setDuration(rollDuration);
        valueAnimator.addUpdateListener(updateListener);
        valueAnimator.addListener(toNextAnimListener);
        valueAnimator.start();
    }

    public void toPre(){
        if (rolling){
            return;
        }

        int startRotate = 0;

        if (rollMode == RollMode.RollInTurn){
            startRotate = 90 + (partNumber - 1) * 30;
        }else if (rollMode == RollMode.Jalousie){
            startRotate = 180;
        }else {
            startRotate = 90;
        }

        swrapIndex(true);
        setRotateDegree(startRotate);

        rolling = true;
        valueAnimator = ValueAnimator.ofFloat(startRotate,0);
        valueAnimator.setDuration(rollDuration);
        valueAnimator.addUpdateListener(updateListener);
        valueAnimator.addListener(toPreAnimListener);
        valueAnimator.start();
    }

    private void swrapIndex(boolean toPre) {
        int temp;
        if (toPre){
            temp = currIndex;
            currIndex = preIndex;
            preIndex = nextIndex;
            nextIndex = temp;
        }else{
            temp = currIndex;
            currIndex = nextIndex;
            nextIndex = preIndex;
            preIndex = temp;
        }
    }

    private ValueAnimator.AnimatorUpdateListener updateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float value = (float) valueAnimator.getAnimatedValue();
            setRotateDegree(value);
        }
    };

    private AnimatorListenerAdapter toNextAnimListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            currIndex++;
            if (currIndex > bitmapList.size() - 1){
                currIndex = 0;
            }
            initIndex();
            setRotateDegree(0);
            rolling = false;
        }
    };

    private AnimatorListenerAdapter toPreAnimListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            swrapIndex(false);//index位置恢复正常
            currIndex--;
            if (currIndex < 0){
                currIndex = bitmapList.size() -1;
            }
            rolling = false;
            initIndex();
            setRotateDegree(0);
        }
    };

}
