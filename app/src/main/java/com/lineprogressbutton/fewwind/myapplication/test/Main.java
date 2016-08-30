package com.lineprogressbutton.fewwind.myapplication.test;

import com.orhanobut.logger.Logger;

/**
 * Created by admin on 2016/8/30.
 */
public class Main {

    private String name = "reflectTest";
    public String sex = "men";

    public void test(Object object){
        Logger.d("OBject");
    }
    public void test(String s){
        Logger.i("String");
    }

}
