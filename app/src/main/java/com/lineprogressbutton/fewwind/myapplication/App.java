package com.lineprogressbutton.fewwind.myapplication;

import android.app.Application;
import android.content.Intent;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by admin on 2016/7/12.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

//        Thread.setDefaultUncaughtExceptionHandler(new MyUnCaughtExceptionHandler());

        Logger.init("tag").setLogLevel(LogLevel.FULL);
    }

    class MyUnCaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            ex.printStackTrace();
            // do some work here
            Intent intent = new Intent(App.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            App.this.startActivity(intent);

            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

}
