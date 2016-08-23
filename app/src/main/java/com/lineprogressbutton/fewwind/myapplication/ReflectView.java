package com.lineprogressbutton.fewwind.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by admin on 2016/7/18.
 */
public class ReflectView extends View {

    private Bitmap mBitmapSrc;
    private Bitmap mBitmapRef;
    private PorterDuffXfermode mfermode;

    private Paint mPaint;

    int screenW = Resources.getSystem().getDisplayMetrics().widthPixels;
    int screenH = Resources.getSystem().getDisplayMetrics().heightPixels;

    int x, y;//位图起点左边

    public ReflectView(Context context) {
        this(context, null);
    }

    public ReflectView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mBitmapSrc = BitmapFactory.decodeResource(getResources(), R.drawable.bg);

        Matrix matrix = new Matrix();
        matrix.setScale(1f, -1f);

        Rect rect = new Rect(0, 0, mBitmapSrc.getWidth(), mBitmapSrc.getHeight());
        mBitmapRef = Bitmap.createBitmap(mBitmapSrc, 0, 0, mBitmapSrc.getWidth(), mBitmapSrc.getHeight(), matrix, true);

        post(new Runnable() {
            @Override
            public void run() {
                x = (screenW - mBitmapSrc.getWidth()) / 2;
                y = (screenH - mBitmapSrc.getHeight()) / 2;
                y = (getMeasuredHeight() - mBitmapSrc.getHeight()) / 2;

                Log.e("tag", y + "");
                mfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
                ;
                mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

//                mPaint.setShader(new LinearGradient(x, y + mSrcBitmap.getHeight(), x,
//                        y + mSrcBitmap.getHeight() + mSrcBitmap.getHeight() / 4, 0xAA000000, Color.TRANSPARENT, Shader.TileMode.CLAMP));
                mPaint.setShader(new LinearGradient(x, y + mBitmapSrc.getHeight(), x ,
                        y + mBitmapSrc.getHeight() + mBitmapSrc.getHeight() / 4, 0xAA000000, Color.TRANSPARENT, Shader.TileMode.CLAMP));
            }
        });

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        mPaint.setColor(Color.BLACK);
        canvas.drawBitmap(mBitmapSrc, x, y, null);

        int sc = canvas.saveLayer(x, y + mBitmapSrc.getHeight(), x + mBitmapRef.getWidth(), y + mBitmapSrc.getHeight() * 2, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(mBitmapRef, x, y + mBitmapSrc.getHeight(), null);
        mPaint.setXfermode(mfermode);

        Rect rect = new Rect(x, y + mBitmapSrc.getHeight(), mBitmapSrc.getWidth() + x, mBitmapSrc.getHeight() * 2 + y);
        canvas.drawRect(rect, mPaint);
        mPaint.setXfermode(null);

        canvas.restoreToCount(sc);

    }
}
