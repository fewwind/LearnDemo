package com.lineprogressbutton.fewwind.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;
import com.orhanobut.logger.Logger;

/**
 * Created by admin on 2016/8/29.
 */
public class LeiDaView extends View {

    private Paint mPaint;
    private Paint mPaintShader;
    private Paint mPaintCircle;
    SweepGradient shader;
    private Matrix mMatrix;

    private int mWidth;
    private int mHeight;

    private int mPaintWidth = 20;
    private int degress = 0;


    public LeiDaView(Context context) {
        this(context, null);
    }


    public LeiDaView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }


    public LeiDaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(mPaintWidth);
        mPaint.setStyle(Paint.Style.STROKE);

        mPaintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintCircle.setColor(Color.BLUE);
        mPaintCircle.setStrokeWidth(20);
        mPaintCircle.setStyle(Paint.Style.FILL);

        mPaintShader = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintShader.setStyle(Paint.Style.FILL);

        mMatrix = new Matrix();
    }


    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        shader = new SweepGradient(mWidth / 2, mHeight / 2, Color.TRANSPARENT,
            getResources().getColor(android.R.color.holo_red_light));
        mPaintShader.setShader(shader);
    }


    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < 4; i++) {
            canvas.drawCircle(mWidth / 2, mHeight / 2,
                mHeight / 2 - mPaintWidth / 2 - (mHeight / 2 / 5) * i, mPaint);
        }

        canvas.drawLine(0, mHeight / 2, mWidth, mHeight / 2, mPaint);
        canvas.drawLine(mWidth / 2, 0, mWidth / 2, mHeight, mPaint);


        /**
         *直接调用画布旋转也可以达到效果
         */
        //canvas.save();
        //canvas.rotate(degress,mWidth/2,mHeight/2);
        canvas.concat(mMatrix);

        //canvas.setMatrix(mMatrix);

        canvas.drawCircle(mWidth / 2, mHeight / 2, mHeight / 2, mPaintShader);
        //canvas.restore();


        if (System.currentTimeMillis()-time>1000){

            canvas.drawCircle((float) (mWidth*(Math.random())), (float) (mWidth*(Math.random())),20,mPaintCircle);
            Logger.d("画个圆圈");
            time = System.currentTimeMillis();
        }

    }


    long time = System.currentTimeMillis();
    public void setPaintWidth(int width) {
        this.mPaintWidth = width;
    }


    @Override protected void onFinishInflate() {
        super.onFinishInflate();

        post(mRotaRunnable);
    }


    Runnable mRotaRunnable = new Runnable() {
        @Override public void run() {
            degress++;

            mMatrix.setRotate(degress,mWidth/2,mHeight/2);

            invalidate();
            degress = degress == 360 ? 0 :degress;
            postDelayed(mRotaRunnable, 10);

        }
    };
}
