package com.lineprogressbutton.fewwind.myapplication.view;

/**
 * Created by admin on 2016/8/25.
 */
public class TestView {

//    public class HopLoadingView extends View {
//        private Paint mPaint;
//
//        private int widthSpecSize;
//        private int heightSpecSize;
//        private int radius = 20;//小圆球半径
//        private int HLength = 100;//六边形半径
//        private int maxMoveH = 60;//向外移送最长距离
//        private double moveH = 0;//向外移动增量
//        private int theCircle = -1;//那个圆球在作用
//
//        private ValueAnimator animator;
//
//        public HopLoadingView(Context context) {
//            super(context);
//        }
//
//        public HopLoadingView(Context context, AttributeSet attrs) {
//            super(context, attrs);
//
//        }
//
//        public HopLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
//            super(context, attrs, defStyleAttr);
//        }
//
//        @Override
//        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//            widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
//            heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
//        }
//
//        @Override
//        protected void onDraw(Canvas canvas) {
//            super.onDraw(canvas);
//            mPaint = new Paint();
//            mPaint.setColor(0xFFE6413A);
//            mPaint.setAntiAlias(true);
//            for (int i = 1; i <= 6; i++) {
//                if (theCircle % 6 + 1 == i)
//                    canvas.drawCircle(widthSpecSize / 2 + (float) (Math.cos(Math.PI * i / 3) * (HLength + moveH)), heightSpecSize / 2 - (float) (Math.sin(Math.PI * i / 3) * (HLength + moveH)), radius, mPaint);
//                else
//                    canvas.drawCircle(widthSpecSize / 2 + (float) Math.cos(Math.PI * i / 3) * HLength, heightSpecSize / 2 - (float) Math.sin(Math.PI * i / 3) * HLength, radius, mPaint);
//            }
//        }
//
//        public void start() {
//            if (animator != null)
//                animator.cancel();
//            animator = ValueAnimator.ofFloat(0f, (float) Math.PI * 6);
//            animator.setDuration(3000);
//            animator.setRepeatMode(ValueAnimator.RESTART);
//            animator.setRepeatCount(ValueAnimator.INFINITE);
//            animator.setInterpolator(new LinearInterpolator());
//            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    moveH = Math.abs(Math.sin((Float) animation.getAnimatedValue())) * maxMoveH;
//                    theCircle = (int) ((Float) animation.getAnimatedValue() / Math.PI) + 1;
//                    nvalidate();
//                }
//            });
//            animator.start();
//        }
//    }
}
