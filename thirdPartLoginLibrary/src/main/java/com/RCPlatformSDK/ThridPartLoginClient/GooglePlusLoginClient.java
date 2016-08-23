package com.RCPlatformSDK.ThridPartLoginClient;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.RCPlatformSDK.ThridPartLoginClient.ClientStandard.LoginClient;
import com.RCPlatformSDK.ThridPartLoginClient.ClientStandard.LoginInfo;
import com.RCPlatformSDK.ThridPartLoginClient.ClientStandard.OnLoginResultListener;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.tag.instagramdemo.R;


/**
 * google+ 登录类
 * 注意事项：由于使用enableAutoManage,调用该类的activity需要继承fragmentactivity类。并在该activity生命周期内，该类只能创建一次
 * @author Yangjitao
 */
public class GooglePlusLoginClient implements LoginClient, GoogleApiClient.OnConnectionFailedListener {
    private final String TAG = GooglePlusLoginClient.class.getSimpleName();

    private GoogleApiClient mGoogleApiClient;

    private OnLoginResultListener onLoginResultListener;

    private Context activity;

    private static final int RC_SIGN_IN = 9001;

    public GooglePlusLoginClient(Context activity) {
        this.activity = activity;
        googleApiInit(activity);
    }


    @Override
    public void updateContext(Context context) {
        activity = context;
    }

    @Override
    public void closeClient() {

    }


    @Override
    public void showLoginWindow() {
        // TODO Auto-generated method stub
        //googleApiInit(activity);

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        ((FragmentActivity) activity).startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void setOnLoginResultListener(OnLoginResultListener onLoginResultListener) {
        // TODO Auto-generated method stub
        this.onLoginResultListener = onLoginResultListener;
    }


    @Override
    public void loginOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                    }
                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.e(TAG, "enter onActivityResult");
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi
                    .getSignInResultFromIntent(data);

            Log.e(TAG, "enter RC_SIGN_IN");
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.e(TAG, "login isSuccess: " + result.isSuccess());
        GoogleSignInAccount acct = result.getSignInAccount();

        LoginInfo loginInfo = new LoginInfo();
        if (acct != null) {
            loginInfo.isLoginSuccess = result.isSuccess();
            loginInfo.userName = acct.getDisplayName();
            loginInfo.userID = acct.getId();
            loginInfo.accessToken = acct.getIdToken();

            if (null != acct.getPhotoUrl()) {
                loginInfo.userImageURL = acct.getPhotoUrl().toString();
            }

            String acctText = acct.getDisplayName() + " id" + acct.getId() + " getIdToken  " + acct.getIdToken() + " getPhotoUrl  " + acct.getPhotoUrl();
            Log.e(TAG, acctText);
        }

        if (null != onLoginResultListener) {
            onLoginResultListener.onLoginResultListener(loginInfo);
        }
    }


    @Override
    public boolean hasAccessToken() {
        // TODO Auto-generated method stub
        return mGoogleApiClient.isConnected();
    }


    private void googleApiInit(Context activity) {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)

                .requestIdToken(activity.getString(R.string.server_client_id))
                .requestProfile()
                .requestEmail()
                .build();

        Log.e(TAG, "mGoogleApiClient create!");
        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .enableAutoManage((FragmentActivity) activity /* FragmentActivity */, this /*OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult arg0) {
        // TODO Auto-generated method stub
        Log.e(TAG, "login failed: " + arg0.getErrorMessage());
    }

}