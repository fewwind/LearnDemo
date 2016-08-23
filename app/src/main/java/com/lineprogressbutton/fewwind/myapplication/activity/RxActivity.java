package com.lineprogressbutton.fewwind.myapplication.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.lineprogressbutton.fewwind.myapplication.R;
import com.orhanobut.logger.Logger;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);

        learnRxjava();
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
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

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


    public <T> T getObject(T t){

        return t;
    }

}
