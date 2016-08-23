package com.lineprogressbutton.fewwind.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by admin on 2016/7/6.
 */
class drawCanvas extends View {
    public drawCanvas(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 取得Resource 图片的Bitmap
        Bitmap vBitmap = BitmapFactory.decodeResource(this.getResources()
                , R.drawable.icon1
        );

        // 建立Paint 物件
        Paint vPaint = new Paint();
        vPaint.setStyle(Paint.Style.STROKE);   //空心
        vPaint.setAlpha(75);   //

        canvas.drawBitmap(vBitmap, 50, 100, null);  //无透明
        canvas.drawBitmap(vBitmap, 50, 200, vPaint);  //有透明
    }
}