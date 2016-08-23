package com.RCPlatformSDK.instagram;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tag.instagramdemo.R;

import java.util.HashMap;


public class InstagramLoginActivity extends Activity {

    private String mUrl;
    private ProgressDialog mSpinner;
    private ProgressDialog getDataWaitDialog;
    private WebView mWebView;

    private static final String TAG = "Instagram-WebView";

    private InstagramLoginManager loginManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_instagramlogin);

        loginManager = new InstagramLoginManager(this, ApplicationData.CLIENT_ID,
                ApplicationData.CLIENT_SECRET, ApplicationData.CALLBACK_URL);

        mUrl = loginManager.mAuthUrl;

        mSpinner = new ProgressDialog(this);
        mSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mSpinner.setMessage("Loading...");
        mSpinner.setCanceledOnTouchOutside(false);
        setUpWebView();

        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
    }

    private void setUpWebView() {
        mWebView = (WebView) findViewById(R.id.webView1);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setWebViewClient(new OAuthWebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(mUrl);
    }

    private class OAuthWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d(TAG, "Redirecting URL " + url);
            if (url.startsWith(InstagramLoginManager.mCallbackUrl)) {
                String urls[] = url.split("=");

                Log.e(TAG, "enter shouldOverrideUrlLoading");
                requestUserInfoFromServer(urls[1]);
                return true;
            }
            return false;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            Log.d(TAG, "Page error: " + description);
            if(InstagramLoginActivity.this.isFinishing()) return;

            super.onReceivedError(view, errorCode, description, failingUrl);
            transferData2PreActivity(null);
            mSpinner.dismiss();
            finish();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d(TAG, "Loading URL: " + url);
            if(InstagramLoginActivity.this.isFinishing()) return;

            super.onPageStarted(view, url, favicon);
            mSpinner.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if(InstagramLoginActivity.this.isFinishing()) return;

            String title = mWebView.getTitle();
            if (title != null && title.length() > 0) {
                //mTitle.setText(title);
            }
            Log.d(TAG, "onPageFinished URL: " + url);
            mSpinner.dismiss();
        }

    }

    private void requestUserInfoFromServer(final String address){
        getDataWaitDialog = new ProgressDialog(this);
        getDataWaitDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDataWaitDialog.setMessage("please wait a few minutes");
        getDataWaitDialog.setCanceledOnTouchOutside(false);
        getDataWaitDialog.show();

        new Thread(){
            @Override
            public void run() {
                final HashMap<String, String> userInfo =  loginManager.getUserInfo(address);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getDataWaitDialog.dismiss();
                        transferData2PreActivity(userInfo);
                        finish();
                    }
                });

            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        transferData2PreActivity(null);
        finish();
        super.onBackPressed();
    }

    private void transferData2PreActivity(HashMap<String, String> userInfo){
        userInfo = (null == userInfo) ? new HashMap<String, String>() : userInfo;
        Intent intent = new Intent();
        intent.putExtra(InstagramLoginManager.LOGIN_RESULT, userInfo);
        setResult(RESULT_OK, intent);
    }

}