package com.lineprogressbutton.fewwind.myapplication.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.lineprogressbutton.fewwind.myapplication.MainActivity;
import com.lineprogressbutton.fewwind.myapplication.R;
import com.lineprogressbutton.fewwind.myapplication.SecondActivity;
import com.lineprogressbutton.fewwind.myapplication.base.BaseActivity;
import com.lineprogressbutton.fewwind.myapplication.test.ActivityBean;
import com.lineprogressbutton.fewwind.myapplication.view.TestImageView;
import com.orhanobut.logger.Logger;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

public class SplashActivity extends BaseActivity {

    @Bind(R.id.id_recycler)
    RecyclerView mRecycler;

    List<ActivityBean> mLists = new ArrayList<>();
    int[] colorArray = { android.R.color.holo_green_light, android.R.color.holo_orange_light,
        android.R.color.holo_blue_bright, };

    private CommonAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        initDatas();
        mRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        mRecycler.setAdapter(mAdapter =
            new CommonAdapter<ActivityBean>(this, android.R.layout.activity_list_item, mLists) {

                @Override
                protected void convert(ViewHolder holder, final ActivityBean activityBean, int position) {
                    holder.setText(android.R.id.text1, activityBean.mTitle);

                    TextView tv = holder.getView(android.R.id.text1);
                    tv.setGravity(Gravity.CENTER);
                    tv.setLayoutParams(new LinearLayout.LayoutParams(-1,
                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50f,
                            getResources().getDisplayMetrics())));
                    tv.setBackgroundColor(
                        getResources().getColor(colorArray[new Random().nextInt(3)]));
                    holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivityBase(SplashActivity.this, activityBean.mClass);
                        }
                    });
                }
            });

    }


    @Override protected void onStart() {
        super.onStart();

        mRecycler.postDelayed(new Runnable() {
            @Override public void run() {


                //View childAt = mRecycler.getChildAt(0);  得到是空的
                View childAt = mRecycler.getLayoutManager().getChildAt(1);


                if (childAt==null){
                    Logger.e("拿不到 是空的");

                } else{
                    Logger.i(childAt.getX()+"--x坐标==y坐标--"+childAt.getY());
                    TextView tv = (TextView) childAt.findViewById(android.R.id.text1);
                    tv.setText("2秒之后我变了");

                    new MaterialShowcaseView.Builder(SplashActivity.this)
                        .setTarget(childAt)
                        .setDismissText("GOT IT")
                        .setContentText("This is some amazing feature you should know about")
                        .setDelay(100) // optional but starting animations immediately in onCreate can make them choppy
                        //.singleUse(SHOWCASE_ID) // provide a unique ID used to ensure it is only shown once
                        .show();

                }
            }
        },2000);

    }


    private void initDatas() {
        mLists.add(new ActivityBean(0, "主页", MainActivity.class));
        mLists.add(new ActivityBean(0, "倒影", SecondActivity.class));
        mLists.add(new ActivityBean(1, "拖拽", ViewDragHelperActivity.class));
        mLists.add(new ActivityBean(0, "RxTest", RxActivity.class));
        mLists.add(new ActivityBean(0, "动画", AnimActivity.class));

        Observable.from(mLists).filter(new Func1<ActivityBean, Boolean>() {
            @Override
            public Boolean call(ActivityBean activityBean) {
                return activityBean.id == 0;
            }
        }).subscribe(new Action1<ActivityBean>() {
            @Override
            public void call(ActivityBean activityBean) {
                //                Logger.d("发射"+activityBean.mTitle);
            }
        });

        Observable.just("tag").map(new Func1<String, Boolean>() {
            @Override public Boolean call(String s) {
                return s.equals("tag");
            }
        }).map(new Func1<Boolean, List<ActivityBean>>() {
            @Override public List<ActivityBean> call(Boolean aBoolean) {
                List<ActivityBean> datas = new ArrayList<ActivityBean>();

                if (aBoolean) {
                    datas.add(new ActivityBean(0, "tag", TestImageView.class));
                }
                return datas;
            }
        }).subscribe(new Action1<List<ActivityBean>>() {
            @Override public void call(List<ActivityBean> activityBeen) {
                mLists.addAll(activityBeen);
                //Logger.e(mLists.toString());
            }
        });

        Observable.from(mLists).flatMap(new Func1<ActivityBean, Observable<Integer>>() {
            @Override public Observable<Integer> call(ActivityBean activityBean) {
                return Observable.just(activityBean.id);
            }
        }).map(new Func1<Integer, String>() {
            @Override public String call(Integer integer) {
                return integer == 0 ? "我是0" : "我不是0";
            }
        }).subscribe(new Action1<String>() {
            @Override public void call(String value) {
                //Logger.d("falt Map——" + value);
            }
        });
    }

    public void learnGit(){

    }
}
