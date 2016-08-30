// (c)2016 Flipboard Inc, All Rights Reserved.

package com.lineprogressbutton.fewwind.myapplication.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GankBeautyResult {
    public boolean error;
    public @SerializedName("results") List<GankBeauty> beauties;


    @Override public String toString() {
        return "GankBeautyResult{" +
            "error=" + error +
            ", beauties=" + beauties +
            '}';
    }
}
