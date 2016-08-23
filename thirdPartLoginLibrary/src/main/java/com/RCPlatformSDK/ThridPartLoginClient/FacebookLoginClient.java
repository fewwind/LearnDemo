package com.RCPlatformSDK.ThridPartLoginClient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.RCPlatformSDK.ThridPartLoginClient.ClientStandard.LoginClient;
import com.RCPlatformSDK.ThridPartLoginClient.ClientStandard.LoginInfo;
import com.RCPlatformSDK.ThridPartLoginClient.ClientStandard.OnLoginResultListener;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * facebook 登录相关代码（具体代码意义，参看facebook的开发者网站）
 *
 * @author Yangjitao
 */
public class FacebookLoginClient implements LoginClient {
    private final String TAG = FacebookLoginClient.class.getSimpleName();

    private CallbackManager callbackManager;

    private OnLoginResultListener onLoginResultListener;

    private ProfileTracker profileTracker;

    private Context context;

    /**
     * 登录状态
     */
    private final int LOGIN_NORMAL = 0;
    private final int LOGIN_ERROR_CANCEL = 1;
    private final int LOGIN_ERROR_ERR = 2;

    public FacebookLoginClient(Context context) {
        this.context = context;
        loginInit(context);
    }

    @Override
    public void updateContext(Context context) {
        this.context = context;
    }

    @Override
    public void showLoginWindow() {
        // TODO Auto-generated method stub
        //loginInit(context);

        // 监听用户信息改变
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                Log.e(TAG, "enter onCurrentProfileChanged");
                handleSignInResult(LOGIN_NORMAL);

            }
        };

        //弹出登录框
        LoginManager.getInstance().logInWithReadPermissions((Activity) context, Arrays.asList("public_profile", "email"));
    }

    @Override
    public void setOnLoginResultListener(OnLoginResultListener onLoginResultListener) {
        // TODO Auto-generated method stub
        this.onLoginResultListener = onLoginResultListener;
    }

    @Override
    public void loginOut() {
        // TODO Auto-generated method stub
        LoginManager.getInstance().logOut();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void closeClient() {
        profileTracker.stopTracking();
    }


    @Override
    public boolean hasAccessToken() {
        // TODO Auto-generated method stub
        return null != AccessToken.getCurrentAccessToken();
    }

    /**
     * 登录成功后，读取用户基本信息
     */
    private void handleSignInResult(int loginStatus) {
        boolean enableButtons = AccessToken.getCurrentAccessToken() != null;
        Profile profile = Profile.getCurrentProfile();
        LoginInfo loginInfo = new LoginInfo();

        if (enableButtons && profile != null) {
           // getUserInfo();

            loginInfo.isLoginSuccess = true;
            Uri rui = profile.getProfilePictureUri(200, 200);
            if (null != rui) {
                loginInfo.userImageURL = rui.toString();
            }
            loginInfo.userName = profile.getName();
            loginInfo.userID = profile.getId();
            loginInfo.accessToken = AccessToken.getCurrentAccessToken().getToken();

            if (null != onLoginResultListener) {
                onLoginResultListener.onLoginResultListener(loginInfo);
            }

        } else if (loginStatus == LOGIN_ERROR_ERR || loginStatus == LOGIN_ERROR_CANCEL) {

            if (null != onLoginResultListener) {
                loginInfo.isLoginSuccess = false;
                onLoginResultListener.onLoginResultListener(loginInfo);
            }
        }

    }

    /**
     * facebook SDK初始化
     */
    private void loginInit(Context context) {
        FacebookSdk.sdkInitialize(context.getApplicationContext());

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.e(TAG, "FacebookCallback onSuccess");
                        handleSignInResult(LOGIN_NORMAL);
                    }


                    @Override
                    public void onCancel() {
                        Log.e(TAG, "FacebookCallback onCancel: ");
                        handleSignInResult(LOGIN_ERROR_CANCEL);
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        exception.printStackTrace();
                        Log.e(TAG, "FacebookCallback Error: ");
                        handleSignInResult(LOGIN_ERROR_ERR);
                    }

                });
    }

    private void getUserInfo(){
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("LoginActivity", response.toString());

                        // Application code
                        try {
                            String email = object.getString("email");

                           // String gender = object.getString("gender");

                           // String email = object.getString("age_range");
                            Log.e(TAG, "graphRequest: "+ object.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday,age_range");
        request.setParameters(parameters);
        request.executeAsync();
    }

}