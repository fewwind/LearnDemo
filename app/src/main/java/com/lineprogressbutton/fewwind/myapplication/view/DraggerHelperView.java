package com.lineprogressbutton.fewwind.myapplication.view;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Created by admin on 2016/8/16.
 */
public class DraggerHelperView extends ViewGroup {
    GridView gridView;
    //默认间距
    private static final int DEFAULT_COLUMS_PADDING = 20;
    //列数
    private int mColums = 3;

    private static final String TAG = DraggerHelperView.class.getSimpleName();

    private ViewDragHelper mDragger;

    public DraggerHelperView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDragger = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {

            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }


            /**
             * clampViewPositionHorizontal,clampViewPositionVertical
             * 可以在该方法中对child移动的边界进行控制，
             * left , top 分别为即将移动到的位置，
             * 比如横向的情况下，
             * 我希望只在ViewGroup的内部移动，
             * 即：paddingleft<=滑动范围<=ViewGroup.
             * getWidth()-paddingright-child.getWidth。
             */

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {

                int leftBound = getPaddingLeft();
                int rightBound = getWidth() - child.getWidth() - leftBound;

                int newLeft = Math.min(Math.max(left, leftBound), rightBound);
                return newLeft;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {

                int topBound = getPaddingTop();
                int bottomBound = getHeight() - child.getHeight() - topBound;
                int newTop = Math.min(Math.max(top, topBound), bottomBound);

                return newTop;
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return getMeasuredWidth() - child.getMeasuredWidth();
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight() - child.getMeasuredHeight();
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                //从childView 的 TAG 中取出原始坐标
                Point position = (Point) releasedChild.getTag();
                mDragger.settleCapturedViewAt(position.x, position.y);
                invalidate();
            }
        });
    }

    public int getmColums() {
        return mColums;
    }

    public void setmColums(int mColums) {

        if (mColums > getChildCount() || mColums == 0)
            throw new IllegalArgumentException("input data illegal");

        this.mColums = mColums;
        requestLayout();
    }


    @Override
    public void computeScroll() {
        if (mDragger.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragger.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragger.processTouchEvent(event);
        return false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);

        int measuredModeW = MeasureSpec.getMode(widthMeasureSpec);
        int measuredModeH = MeasureSpec.getMode(heightMeasureSpec);
        int cCount = getChildCount();

        Log.e(TAG, "measuredWidth=" + measuredWidth + "measuredHeight=" + measuredHeight);

        switch (measuredModeW) {
            case MeasureSpec.EXACTLY:
                Log.e(TAG, "EXACTLY=W");
                break;
            case MeasureSpec.AT_MOST:
                Log.e(TAG, "AT_MOST=W");
                break;
            case MeasureSpec.UNSPECIFIED:
                Log.e(TAG, "UNSPECIFIED=W");
                break;
        }

        switch (measuredModeH) {
            case MeasureSpec.EXACTLY:
                Log.e(TAG, "EXACTLY=H");
                break;
            case MeasureSpec.AT_MOST:
                Log.e(TAG, "AT_MOST=H");
                break;
            case MeasureSpec.UNSPECIFIED:
                Log.e(TAG, "UNSPECIFIED=H");
                break;
        }


        //自动排序
//        for (int i = 0; i < cCount; i++) {
//            View childView = getChildAt(i);
//            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
//            widgetWidth = childView.getPaddingLeft() +
//                    childView.getMeasuredWidth() + childView.getPaddingRight();
//
//            if (width + widgetWidth + DEFAULT_COLUMS_PADDING > measuredWidth) {
//                mColums = i;
//                break;
//            }
//            width += widgetWidth + DEFAULT_COLUMS_PADDING;
//        }

        int height = 0;
        int width = 0;

        int widgetHeight = 0;
        int widgetWidth = 0;

        int maxWidth = 0;

        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            Log.e(TAG, "childWidth=" + childView.getMeasuredHeight());
            widgetHeight = childView.getMeasuredHeight() + childView.getPaddingTop() + childView.getPaddingBottom();
            widgetWidth = childView.getPaddingLeft() +
                    childView.getMeasuredWidth() + childView.getPaddingRight();

            if (i % mColums == 0) {
                height += widgetHeight + DEFAULT_COLUMS_PADDING;
                maxWidth = Math.max(maxWidth, width);
                Log.e(TAG, "maxWidth=" + maxWidth);
                width = widgetWidth + DEFAULT_COLUMS_PADDING;
            } else {
                width += widgetWidth + DEFAULT_COLUMS_PADDING;
            }

        }
        height += DEFAULT_COLUMS_PADDING;
        width = maxWidth + DEFAULT_COLUMS_PADDING;

        Log.e(TAG, "parentWidth=" + width + "/parentHeight=" + height);

        setMeasuredDimension((measuredModeW == MeasureSpec.EXACTLY) ? measuredWidth : width,
                (measuredModeH == MeasureSpec.EXACTLY) ? measuredHeight : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int cCount = getChildCount();
        //最后使用的l t r b
        int layoutLeft = 0;
        int layoutRight = 0;
        int layoutTop = 0;
        int layoutBottom = 0;
        //下一个子视图的l
        int lineLeft = 0;
        //下一行子视图的t
        int lineTop = DEFAULT_COLUMS_PADDING;
        //子视图具体所占大小
        int widgetHeight = 0;
        int widgetWidth = 0;
        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);

            widgetHeight = childView.getMeasuredHeight() + childView.getPaddingTop() + childView.getPaddingBottom();
            widgetWidth = childView.getPaddingLeft() +
                    childView.getMeasuredWidth() + childView.getPaddingRight();
            if (i % mColums == 0) {
                //左边
                lineLeft = 0;
                //累加一下行高
                layoutTop += lineTop;
                layoutLeft = DEFAULT_COLUMS_PADDING;
            } else {
                //右边
                layoutLeft = DEFAULT_COLUMS_PADDING + lineLeft;
            }

            layoutRight = layoutLeft + widgetWidth;
            lineTop = Math.max(lineTop, widgetHeight + DEFAULT_COLUMS_PADDING);

            lineLeft = layoutRight;

            layoutBottom = layoutTop + widgetHeight;

            childView.setTag(new Point(layoutLeft, layoutTop));

            childView.layout(layoutLeft, layoutTop, layoutRight, layoutBottom);
        }


    }
}
