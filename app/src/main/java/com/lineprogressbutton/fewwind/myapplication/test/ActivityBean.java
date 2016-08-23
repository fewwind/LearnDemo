package com.lineprogressbutton.fewwind.myapplication.test;

import java.io.Serializable;

/**
 * Created by admin on 2016/8/16.
 */
public class ActivityBean implements Serializable {


    public Class mClass;
    public int id;

    public String mTitle;

    public ActivityBean(int id, String title, Class clazz) {
        this.mClass = clazz;
        this.id = id;
        this.mTitle = title;
    }


    @Override public String toString() {
        return "ActivityBean{" +
            "mClass=" + mClass +
            ", id=" + id +
            ", mTitle='" + mTitle + '\'' +
            '}';
    }
}
