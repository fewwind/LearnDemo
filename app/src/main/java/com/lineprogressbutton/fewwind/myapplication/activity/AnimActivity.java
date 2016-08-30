package com.lineprogressbutton.fewwind.myapplication.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.lineprogressbutton.fewwind.myapplication.R;
import com.lineprogressbutton.fewwind.myapplication.base.BaseActivity;
import com.lineprogressbutton.fewwind.myapplication.view.LeiDaView;
import com.orhanobut.logger.Logger;

public class AnimActivity extends BaseActivity {

    @Bind(R.id.id_anim_iv)
    ImageView mIv1;
    @Bind(R.id.id_anim_ball)
    ImageView mIvBall;
    @Bind(R.id.id_leida)
    LeiDaView mLeiDa;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showBallAnim();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }


    @Override protected int initLayoutId() {
        return R.layout.activity_anim;
    }


    @OnClick(R.id.id_anim_iv)
    public void onClickIv1(final View v) {
 /*       ObjectAnimator.ofFloat(v,"rotationY",0.0f,180.0f)
                .setDuration(500)
                .start();*/

/*        ObjectAnimator anim = ObjectAnimator.ofFloat(v, "ysf", 1.0F, 0.0F)
                .setDuration(500);

        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();

                v.setScaleX(value);
                v.setScaleY(value);
                v.setAlpha(value);
            }
        });*/

        //缩放并伴随透明动画
/*
        PropertyValuesHolder property1 = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.0f, 1.0f);
        PropertyValuesHolder property2 = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.0f, 1.0f);
        PropertyValuesHolder property3 = PropertyValuesHolder.ofFloat("alpha", 1.0f, 0.0f, 1.0f);
        ObjectAnimator.ofPropertyValuesHolder(v, property1, property2, property3).setDuration(500).start();*/

        //缩放并伴随透明动画
/*        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(v,"scaleX",1,0).setDuration(2000)
        ,ObjectAnimator.ofFloat(v,"scaleY",1,0).setDuration(2000));

        //设置缩放的轴心
//        v.setPivotX(v.getWidth()/2);
//        v.setPivotY(v.getHeight()/2);
        v.setPivotX(0);
        v.setPivotY(0);
        animatorSet.start();*/

        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator anim1 =ObjectAnimator.ofFloat(v,"translationY",-(v.getHeight()+mIvBall.getHeight())).setDuration(2000);
//        ObjectAnimator anim2 =ObjectAnimator.ofFloat(v,"translationY",-(800-v.getHeight())).setDuration(2000);
        ObjectAnimator anim2 =ObjectAnimator.ofFloat(v,"translationY",0).setDuration(2000);

        animatorSet.play(anim1);
        animatorSet.play(anim2).after(anim1);
        animatorSet.start();

        // translationY 指的是view 相对于父窗体的左上角移动的距离，》0则是向下移动，

/*
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(fab, "translationY",(1920-fab.getBottom()) +fab.getHeight());
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(fab, "translationY", 0);
        animatorSet.play(anim1);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.play(anim2).after(anim1);
        animatorSet.setDuration(200);
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Logger.e("getBottom--"+fab.getBottom()+"--height--"+fab.getTop()+"++"+fab.getTranslationY());

            }
        });
*/

//        v.animate().translationY(800).setDuration(1000);

//        v.animate().scaleX(0.4f).setDuration(2000);
//        v.animate().scaleY(0.4f).setDuration(2000);


    }

    public void showBallAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1920 - mIvBall.getHeight());
        valueAnimator.setTarget(mIvBall);
        valueAnimator.setDuration(500);
        valueAnimator.start();

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
//                mIvBall.setTranslationY((Float) animation.getAnimatedValue());
                mIvBall.setY((Float) animation.getAnimatedValue());
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Logger.d("动画结束");

                ViewGroup parent = (ViewGroup) mIvBall.getParent();
                if (parent != null) {
                    //parent.removeView(mIv1);
                    mIv1.setVisibility(mIv1.isShown() ? View.GONE : View.VISIBLE);
                }
            }
        });
    }
}
