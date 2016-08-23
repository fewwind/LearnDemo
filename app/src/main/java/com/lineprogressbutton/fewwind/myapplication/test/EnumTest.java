package com.lineprogressbutton.fewwind.myapplication.test;

/**
 * Created by admin on 2016/8/15.
 */
public class EnumTest {

    public int id;

    public FirstEnum firstEnum;

    public EnumTest(int id, FirstEnum firstEnum) {
        this.id = id;
        this.firstEnum = firstEnum;
    }

    public enum FirstEnum {
        EMBED,      //内置
        ZIP,        //zip包
        APK_PLUGIN      //apk plugin
    }


}
