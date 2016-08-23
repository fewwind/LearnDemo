/*
* Copyright (C) 2014 RC Platform (HK) Inc., 
*/
/**
 * @Title: SharePrefUtil.java
 * @Package com.rcplatform.instaaccess.utils
 * @Description: TODO(用一句话描述该文件做什么)
 * @author linjimin Email：linjimin@rcplatformhk.com
 * @date 2015-10-10 下午3:16:14
 * @version : 1.0
 */
package com.RCPlatformSDK.ThridPartLoginClient;

import android.content.Context;
import android.content.SharedPreferences;


public class SharePreferenceHelper {

    private static SharedPreferences getPref(Context context) {
        return context.getSharedPreferences("login_user_info_sharePre", Context.MODE_PRIVATE);
    }

    public static void setString(Context context, String key, String value) {
        SharedPreferences pref = getPref(context);
        pref.edit().putString(key, value).apply();
    }

    public static String getString(Context context, String key) {
        return getPref(context).getString(key, null);
    }

    public static boolean contains(Context context, String key){
        return getPref(context).contains(key);
    }

    public static void delete(Context context, String key){
        SharedPreferences pref = getPref(context);
        pref.edit().remove(key).apply();
    }



    /** 记录登录用户的信息*/
    public static final String LOGIN_USER_INFO = "login_user_photo";

    public static void setLoginUserInfo(Context context, String loginInfo){
        setString(context, LOGIN_USER_INFO, loginInfo);
    }

    public static String getLoginUserInfo(Context context){
        return getString(context, LOGIN_USER_INFO);
    }

    public static void deleteLoginUserInfo(Context context){
         delete(context, LOGIN_USER_INFO);
    }


}

