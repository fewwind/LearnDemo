package com.lineprogressbutton.fewwind.myapplication.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.OnClick;
import com.lineprogressbutton.fewwind.myapplication.R;
import com.lineprogressbutton.fewwind.myapplication.base.BaseActivity;
import com.lineprogressbutton.fewwind.myapplication.bean.GankBeautyResult;
import com.lineprogressbutton.fewwind.myapplication.bean.TrainStationInfo;
import com.lineprogressbutton.fewwind.myapplication.net.NetWork;
import com.lineprogressbutton.fewwind.myapplication.util.Blur;
import com.lineprogressbutton.fewwind.myapplication.util.FastBlurUtil;
import com.orhanobut.logger.Logger;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxActivity extends BaseActivity {

    Bitmap bitmap;
    Bitmap bitmap2;
    Bitmap bitmap3;

    @Bind(R.id.id_iv_blur)
    ImageView mIvBlur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        learnRxjava();

    }


    @Override protected int initLayoutId() {
        return R.layout.activity_rx;
    }


    @OnClick(R.id.id_fast_blur1) void onClick1(Button btn) {
        Long time = System.currentTimeMillis();
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon3);
        Observable.just(1, 2, 3, 4, 5, 6, 7).flatMap(new Func1<Integer, Observable<Integer>>() {
            @Override public Observable<Integer> call(Integer integer) {
                return Observable.just(integer);
            }
        }).subscribe(new Action1<Integer>() {
            @Override public void call(Integer integer) {
                Logger.d("flatMap--无序的操作--" + integer);
            }
        });
        Observable.just(1, 2, 3, 4, 5, 6, 7).concatMap(new Func1<Integer, Observable<Integer>>() {
            @Override public Observable<Integer> call(Integer integer) {
                return Observable.just(integer);
            }
        }).subscribe(new Action1<Integer>() {
            @Override public void call(Integer integer) {
                Logger.e("concatMap--有序的操作--" + integer);
            }
        });
    }


    @OnClick(R.id.id_fast_blur2) void onClick2(Button btn) {
        Long time = System.currentTimeMillis();
        bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.icon3);
        Observable.just(time).flatMap(new Func1<Long, Observable<Long>>() {
            @Override public Observable<Long> call(Long s) {
                for (int i = 0; i < 10; i++) {

                    //////////////////////////////////////////////////////////////
                    FastBlurUtil.doBlur(bitmap, 36, false);

                    Blur.fastblur(RxActivity.this, bitmap2, 36);
                }
                return Observable.just(System.currentTimeMillis() - s);
            }
        })
            .compose(this.<Long>applyAsySchedulers()).subscribe(
            new Action1<Long>() {
                @Override public void call(Long s) {
                    Logger.e("Time--" + s);
                }
            });

    }


    @OnClick(R.id.id_fast_blur3) void onClick3(Button btn) {
        Long time = System.currentTimeMillis();
        bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.icon3);
        //Bitmap bitmap = FastBlurUtil.blur(RxActivity.this, bitmap3);
        Bitmap bitmap = FastBlurUtil.blurScale(RxActivity.this, bitmap3);
        mIvBlur.setImageBitmap(bitmap);
        Observable.just(time).flatMap(new Func1<Long, Observable<Long>>() {
            @Override public Observable<Long> call(Long s) {
                for (int i = 0; i < 100; i++) {

                    //FastBlurUtil.blur(RxActivity.this,bitmap3);
                }
                return Observable.just(System.currentTimeMillis() - s);
            }
        })
            .compose(this.<Long>applyAsySchedulers()).subscribe(
            new Action1<Long>() {
                @Override public void call(Long s) {
                    Logger.e("Time--" + s);
                }
            });

        NetWork.getTrainApi(RxActivity.this)
            .getStationInfo("2016-09-24", "BJP", "CSQ", "ADULT")
            .compose(this.<TrainStationInfo>applyAsySchedulers())
            .subscribe(
                new Subscriber<TrainStationInfo>() {
                    @Override public void onCompleted() {

                    }


                    @Override public void onError(Throwable e) {
                        Logger.e("失败--"+e.toString());
                    }


                    @Override public void onNext(TrainStationInfo trainStationInfo) {
                        Logger.i("成功--"+trainStationInfo.toString());
                    }
                });

        NetWork.getGankApi().getBeauties(6,2)
            .compose(this.<GankBeautyResult>applyAsySchedulers())
            .subscribe(new Subscriber<GankBeautyResult>() {
            @Override public void onCompleted() {

            }


            @Override public void onError(Throwable e) {
                Logger.e("失败--"+e.toString());
            }


            @Override public void onNext(GankBeautyResult gankBeautyResult) {
                Logger.i("成功--"+gankBeautyResult.toString());
            }
        });
    }


    private void learnRxjava() {

        Observable.timer(2, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Logger.d("2s 后执行操作");
            }
        });

        Subscription subscribe = Observable.interval(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    //                        Logger.i("每隔一秒执行操作");
                }
            });

        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("Aloha");
                subscriber.onCompleted();
                Logger.e("Hello Hi");
            }
        });

        Observable.just(R.drawable.icon1).map(new Func1<Integer, Bitmap>() {
            @Override
            public Bitmap call(Integer s) {

                Logger.e("开始睡觉--");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Logger.e("睡了两秒 ");
                return BitmapFactory.decodeResource(getResources(), s);
            }
        }).compose(this.<Bitmap>applyAsySchedulers())
            .subscribe(new Action1<Bitmap>() {
                @Override
                public void call(Bitmap bitmap) {
                    Logger.e("bitmap--" + bitmap.getWidth());
                }
            });

        Observable.just(R.drawable.icon1, R.drawable.bg).map(new Func1<Integer, Bitmap>() {
            @Override
            public Bitmap call(Integer integer) {
                return BitmapFactory.decodeResource(getResources(), integer);
            }
        })
            .subscribe(new Action1<Bitmap>() {
                @Override
                public void call(Bitmap bitmap) {
                    Logger.e("just---bitmap--" + bitmap.getWidth());
                }
            });

    }


    public <T> Observable.Transformer<T, T> applyAsySchedulers() {
        return new Observable.Transformer<T, T>() {

            @Override public Observable<T> call(Observable<T> tObservable) {
                return tObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    public <T> T getObject(T t) {

        // 用 compose 操作符 重用 schedulers 线程变换
        Observable.just("").compose(this.<String>applyAsySchedulers()).subscribe(
            new Action1<String>() {
                @Override public void call(String s) {

                }
            });
        return t;
    }

}
