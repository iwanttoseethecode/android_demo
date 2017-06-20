package com.example.luoling.android_dome.chapter5;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by luoling on 2016/12/1.
 */

public class FiveChessView extends View {

    private static final int SIZE = 120;//棋子的尺寸
    private static final int OFFSET =10;//发光点的偏移大小
    private Paint paint;
    public FiveChessView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = this.getMeasuredWidth();
        int height = this.getMeasuredHeight();
        int row = height/SIZE;
        int cols = width/SIZE;
        //画棋盘
        drawChessBoard(canvas,row,cols);
        //画棋子
        drawChess(canvas,4,4,ChessType.BLACK);
        drawChess(canvas,4,5,ChessType.BLACK);
        drawChess(canvas,5,4,ChessType.WHITE);
        drawChess(canvas,3,5,ChessType.WHITE);
    }

    /*
    * 画棋盘
    * */
    private void drawChessBoard(Canvas canvas,int rows,int cols){
        paint.setColor(Color.GRAY);
        paint.setShadowLayer(0,0,0,Color.GRAY);
        for (int i=0;i<rows+1;i++){
            canvas.drawLine(0,i*SIZE,cols*SIZE,i*SIZE,paint);
        }
        for (int i=0;i<cols+1;i++){
            canvas.drawLine(i*SIZE,0,i*SIZE,rows*SIZE,paint);
        }
    }

    /*
    * 画棋子
    * */
    private void drawChess(Canvas canvas,int x,int y,ChessType chessType){
        //定义棋子颜色
        int colorOuter = (chessType == ChessType.BLACK ? Color.BLACK :Color.GRAY );
        int colorinter = Color.WHITE;
        // 定义渐变，发光点向右下角偏移OFFSET
        RadialGradient rg = new RadialGradient(x*SIZE+OFFSET,y*SIZE+OFFSET,SIZE/1.5f,colorinter,colorOuter, Shader.TileMode.CLAMP);
        paint.setShader(rg);
        //画棋子
        setLayerType(View.LAYER_TYPE_SOFTWARE,paint);
        paint.setShadowLayer(6,-5,-5,Color.parseColor("#aacccccc"));//给棋子加阴影
        canvas.drawCircle(x*SIZE,y*SIZE,SIZE/2,paint);
    }

    enum ChessType{
        BLACK,WHITE;
    }
}
