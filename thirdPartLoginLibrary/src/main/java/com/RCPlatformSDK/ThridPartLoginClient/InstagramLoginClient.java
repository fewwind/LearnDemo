package com.RCPlatformSDK.ThridPartLoginClient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.RCPlatformSDK.ThridPartLoginClient.ClientStandard.LoginClient;
import com.RCPlatformSDK.ThridPartLoginClient.ClientStandard.LoginInfo;
import com.RCPlatformSDK.ThridPartLoginClient.ClientStandard.OnLoginResultListener;
import com.RCPlatformSDK.instagram.InstagramLoginActivity;
import com.RCPlatformSDK.instagram.InstagramLoginManager;

import java.util.HashMap;

public class InstagramLoginClient implements LoginClient {
    private final String TAG = InstagramLoginClient.class.getSimpleName();
    private Context context;

    private OnLoginResultListener onLoginResultListener;

    public InstagramLoginClient(Context context) {
        this.context = context;
    }


    @Override
    public void updateContext(Context context) {
        this.context = context;
    }

    @Override
    public void closeClient() {

    }


    @Override
    public void showLoginWindow() {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        intent.setClass(context, InstagramLoginActivity.class);
        ((Activity)context).startActivityForResult(intent, InstagramLoginManager.REQUESTCODE);
    }

    @Override
    public void setOnLoginResultListener(OnLoginResultListener onLoginResultListener) {
        // TODO Auto-generated method stub
        this.onLoginResultListener = onLoginResultListener;
    }

    @Override
    public void loginOut() {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean hasAccessToken() {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if(requestCode == InstagramLoginManager.REQUESTCODE){
            if(data == null) return;
            HashMap<String, String> userInfo = (HashMap<String, String>)data.getSerializableExtra(InstagramLoginManager.LOGIN_RESULT);
            LoginInfo loginInfo = new LoginInfo();

            if(null != userInfo && userInfo.size() != 0) {
                loginInfo.isLoginSuccess = true;
                loginInfo.userImageURL = userInfo.get(InstagramLoginManager.TAG_PROFILE_PICTURE);
                loginInfo.userName = userInfo.get(InstagramLoginManager.TAG_USERNAME);

                loginInfo.accessToken = userInfo.get(InstagramLoginManager.TAG_TOKEN);
                loginInfo.userID = userInfo.get(InstagramLoginManager.TAG_ID);
            }

            if (null != onLoginResultListener) {
                onLoginResultListener.onLoginResultListener(loginInfo);
            }

        }
    }

}