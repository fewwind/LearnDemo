package com.lineprogressbutton.fewwind.myapplication.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.lineprogressbutton.fewwind.myapplication.R;

/**
 * Created by admin on 2016/8/19.
 */
public class TestImageView extends ImageView {

    Bitmap shape ;
    Bitmap shapeSize ;

    public TestImageView(Context context) {
        this(context,null);
    }

    public TestImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TestImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        shape = BitmapFactory.decodeResource(context.getResources(), R.drawable.rectangle_two);
        Matrix matrix = new Matrix();
//        matrix.
    }


    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        setImageBitmap(shape);


    }
}
