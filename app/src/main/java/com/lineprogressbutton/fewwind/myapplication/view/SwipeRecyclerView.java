package com.lineprogressbutton.fewwind.myapplication.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * Created by admin on 2016/8/18.
 */
public class SwipeRecyclerView extends RecyclerView {

    private TextView tvTop;
    private TextView tvDelete;

    private LinearLayout mItemLayout;

    private int mMaxLength;

    private int mLastX;
    private int mLastY;

    private int mPostion;
    private boolean isDragging;
    private boolean isItemMoving;
    private boolean isStartScroll;

    //左滑菜单状态   0：关闭 1：将要关闭 2：将要打开 3：打开
    private int mMenuState;
    private static int MENU_CLOSED = 0;
    private static int MENU_WILL_CLOSED = 1;
    private static int MENU_OPEN = 2;
    private static int MENU_WILL_OPEN = 3;

    private Scroller mScroller;

    private OnItemActionListener mListener;

    public SwipeRecyclerView(Context context) {
        this(context, null);
    }

    public SwipeRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mScroller = new Scroller(context, new LinearInterpolator());

    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                
                int x = (int) e.getX();
                int y = (int) e.getY();
                View view = findChildViewUnder(x,y);
                ViewHolder childViewHolder = getChildViewHolder(view);

                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:

                break;
        }


        return super.onTouchEvent(e);
    }

    public interface OnItemActionListener {

    }
}
