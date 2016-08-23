package com.RCPlatformSDK.ThridPartLoginClient;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * 为第三方登录类 规定一个实现规范(主要包括要实现哪些接口、数据类的定义等)
 *
 * @author Yangjitao
 */
public class ClientStandard {

    /**
     * 登录结果信息类（用户的相关信息）
     */
    public static class LoginInfo implements Serializable {
        /**
         * 是否登录成功
         */
        public boolean isLoginSuccess;

        /**
         * 用户ID-用于标示同一用户
         */
        public String userID;

        /**
         * 登录成功后得到的服务器token
         */
        public String accessToken;

        /**
         * 用户的基本信息
         */
        public String userName;
        public Bitmap userImage;
        public String userImageURL;

        /** 用户图像本地存储路径*/
        private String userImageLocalPath;

        /** 将数据信息格式化成json格式(将图片保存到本地，只把路径加入到josn中)*/
        public JSONObject formatField2Json(Context context){

            if(null != userImage) {
                String dataSaveFolder = context.getApplicationContext().getExternalFilesDir("loginInfo").toString();
                String userPhotoName = userID + ".jpg";
                writeBitmap(dataSaveFolder, userPhotoName, userImage);
                userImageLocalPath = (dataSaveFolder.endsWith("/") ? dataSaveFolder : (dataSaveFolder + "/")) + userPhotoName;
            }

            JSONObject json = new JSONObject();
            try {
                json.put("1", isLoginSuccess);
                json.put("2", userID);
                json.put("3", accessToken);
                json.put("4", userName);
                json.put("5", userImageLocalPath);
                json.put("6", userImageURL);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return json;
        }

        /** 为每个字段赋值*/
        public void resetFeild(JSONObject json){
            try {
                isLoginSuccess = json.getBoolean("1");
                userID = json.getString("2");
                accessToken = json.getString("3");
                userName = json.getString("4");
                userImageLocalPath = json.getString("5");
                userImageURL = json.getString("6");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Bitmap bitmap = BitmapFactory.decodeFile(userImageLocalPath);
            userImage = bitmap;
        }

        /** 将bitmap保存到本地*/
        private void writeBitmap(String dir,String name,Bitmap bitmap){
            if(bitmap==null){
                return;
            }

            File dirFile = new File(dir);
            if(!dirFile.exists()){
                dirFile.mkdirs();
            }

            File img = new File(dir,name);
            Bitmap.CompressFormat format  =null;
            if(name.endsWith(".jpg")){
                format = Bitmap.CompressFormat.JPEG;
            }else{
                format = Bitmap.CompressFormat.PNG;
            }
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(img);
                bitmap.compress(format, 100, fos);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    bitmap=null;
                    if (fos != null)
                        fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 第三方登录类需要实现的接口
     */
    public interface LoginClient {
        /**
         * 弹出登录界面
         */
        public void showLoginWindow();

        /**
         * 用户回传登录结果
         */
        public void setOnLoginResultListener(OnLoginResultListener onLoginResultListener);

        /**
         * 登出
         */
        public void loginOut();

        /** */
        @Deprecated
        public boolean hasAccessToken();

        /**
         * 需要在activity类的onActivityResult 函数中调用该函数
         */
        public void onActivityResult(int requestCode, int resultCode, Intent data);

        /**
         * 更新context
         */
        public void updateContext(Context context);

        /**
         * 需要在activity类的onDestroy 函数中调用
         */
        public void closeClient();
    }

    /**
     * 登录信息结果回调接口
     */
    public interface OnLoginResultListener {
        public void onLoginResultListener(LoginInfo loginInfo);
    }
}