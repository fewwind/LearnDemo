package com.RCPlatformSDK.instagram;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class InstagramLoginManager {

    private InstagramSession mSession;
    //private InstagramDialog mDialog;
    //private OAuthAuthenticationListener mListener;
    private ProgressDialog mProgress;
    private HashMap<String, String> userInfo = new HashMap<String, String>();
    public String mAuthUrl;
    private String mTokenUrl;
    private String mAccessToken;
    private Context mCtx;

    private String mClientId;
    private String mClientSecret;

    public static int WHAT_FINALIZE = 0;
    public static int WHAT_ERROR = 1;
    private static int WHAT_FETCH_INFO = 2;

    public static String mCallbackUrl = "";
    private static final String AUTH_URL = "https://api.instagram.com/oauth/authorize/";
    private static final String TOKEN_URL = "https://api.instagram.com/oauth/access_token";
    private static final String API_URL = "https://api.instagram.com/v1";

    private static final String TAG = "InstagramAPI";

    public static final String TAG_DATA = "data";
    public static final String TAG_ID = "id";
    public static final String TAG_PROFILE_PICTURE = "profile_picture";
    public static final String TAG_USERNAME = "username";
    public static final String TAG_BIO = "bio";
    public static final String TAG_WEBSITE = "website";
    public static final String TAG_COUNTS = "counts";
    public static final String TAG_FOLLOWS = "follows";
    public static final String TAG_FOLLOWED_BY = "followed_by";
    public static final String TAG_MEDIA = "media";
    public static final String TAG_FULL_NAME = "full_name";
    public static final String TAG_META = "meta";
    public static final String TAG_CODE = "code";

    public static final String TAG_TOKEN = "accessToken";

    public static final String LOGIN_RESULT = "login_result";

    public static final int REQUESTCODE = 23333;

    public InstagramLoginManager(Context context, String clientId, String clientSecret,
                                 String callbackUrl) {

        mClientId = clientId;
        mClientSecret = clientSecret;
        mCtx = context;
        mSession = new InstagramSession(context);
        mAccessToken = mSession.getAccessToken();

        mCallbackUrl = callbackUrl;
        mTokenUrl = TOKEN_URL + "?client_id=" + clientId + "&client_secret="
                + clientSecret + "&redirect_uri=" + mCallbackUrl
                + "&grant_type=authorization_code";
        mAuthUrl = AUTH_URL
                + "?client_id="
                + clientId
                + "&redirect_uri="
                + mCallbackUrl
                + "&response_type=code&display=touch&scope=likes+comments+relationships";

    }

    public HashMap<String, String> getUserInfo(final String code){
        if(getAccessToken(code) == WHAT_FINALIZE && fetchUserName() == WHAT_FINALIZE){
            return userInfo;
        }else{
            return null;
        }
    }



    private int getAccessToken(final String code) {
        int what = WHAT_FINALIZE;
        try {
            URL url = new URL(TOKEN_URL);
            // URL url = new URL(mTokenUrl + "&code=" + code);
            Log.i(TAG, "Opening Token URL " + url.toString());
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            // urlConnection.connect();
            OutputStreamWriter writer = new OutputStreamWriter(
                    urlConnection.getOutputStream());
            writer.write("client_id=" + mClientId + "&client_secret="
                    + mClientSecret + "&grant_type=authorization_code"
                    + "&redirect_uri=" + mCallbackUrl + "&code=" + code);
            writer.flush();
            String response = Utils.streamToString(urlConnection
                    .getInputStream());
            Log.i(TAG, "response " + response);
            JSONObject jsonObj = (JSONObject) new JSONTokener(response)
                    .nextValue();

            mAccessToken = jsonObj.getString("access_token");
            userInfo.put(TAG_TOKEN, mAccessToken);

            Log.i(TAG, "Got access token: " + mAccessToken);

            String id = jsonObj.getJSONObject("user").getString("id");
            String user = jsonObj.getJSONObject("user").getString(
                    "username");
            String name = jsonObj.getJSONObject("user").getString(
                    "full_name");

            mSession.storeAccessToken(mAccessToken, id, user, name);

            Log.e(TAG, "id: "+ id + "  user: "+user + " name: "+name+" mAccessToken: "+mAccessToken);

        } catch (Exception ex) {
            what = WHAT_ERROR;
            ex.printStackTrace();
            Log.e(TAG, "enter Exception token ");
        }

        return what;
    }

    private int fetchUserName() {
        int what = WHAT_FINALIZE;
        try {
            URL url = new URL(API_URL + "/users/" + mSession.getId()
                    + "/?access_token=" + mAccessToken);

            Log.d(TAG, "Opening URL " + url.toString());
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
            String response = Utils.streamToString(urlConnection
                    .getInputStream());
            System.out.println(response);
            JSONObject jsonObj = (JSONObject) new JSONTokener(response)
                    .nextValue();

            // String name = jsonObj.getJSONObject("data").getString(
            // "full_name");
            // String bio =
            // jsonObj.getJSONObject("data").getString("bio");
            // Log.i(TAG, "Got name: " + name + ", bio [" + bio + "]");
            JSONObject data_obj = jsonObj.getJSONObject(TAG_DATA);
            userInfo.put(TAG_ID, data_obj.getString(TAG_ID));

            userInfo.put(TAG_PROFILE_PICTURE,
                    data_obj.getString(TAG_PROFILE_PICTURE));

            userInfo.put(TAG_USERNAME, data_obj.getString(TAG_USERNAME));

            userInfo.put(TAG_BIO, data_obj.getString(TAG_BIO));

            userInfo.put(TAG_WEBSITE, data_obj.getString(TAG_WEBSITE));

            JSONObject counts_obj = data_obj.getJSONObject(TAG_COUNTS);

            userInfo.put(TAG_FOLLOWS, counts_obj.getString(TAG_FOLLOWS));

            userInfo.put(TAG_FOLLOWED_BY,
                    counts_obj.getString(TAG_FOLLOWED_BY));

            userInfo.put(TAG_MEDIA, counts_obj.getString(TAG_MEDIA));

            userInfo.put(TAG_FULL_NAME,
                    data_obj.getString(TAG_FULL_NAME));

            JSONObject meta_obj = jsonObj.getJSONObject(TAG_META);

            userInfo.put(TAG_CODE, meta_obj.getString(TAG_CODE));

            Log.e(TAG, "userInfo: "+ userInfo.toString());
        } catch (Exception ex) {
            what = WHAT_ERROR;
            ex.printStackTrace();
            Log.e(TAG, "enter Exception userinfo ");
        }

        return what;
    }

//
//    public HashMap<String, String> getUserInfo() {
//        return userInfo;
//    }
//
//    public boolean hasAccessToken() {
//        return (mAccessToken == null) ? false : true;
//    }
//
//    public void setListener(OAuthAuthenticationListener listener) {
//        mListener = listener;
//    }
//
//    public String getUserName() {
//        return mSession.getUsername();
//    }
//
//    public String getId() {
//        return mSession.getId();
//    }
//
//    public String getName() {
//        return mSession.getName();
//    }
//
//    public String getTOken() {
//        return mSession.getAccessToken();
//    }
//
//
//    public void authorize() {
//        Intent intent = new Intent();
//        intent.setClass(mCtx, InstagramLoginActivity.class);
//        intent.putExtra(InstagramLoginActivity.AUTH_URL, mAuthUrl);
//        mCtx.startActivity(intent);
//    }
//
//
//    public void resetAccessToken() {
//        if (mAccessToken != null) {
//            mSession.resetAccessToken();
//            mAccessToken = null;
//        }
//    }
//
//    public interface OAuthAuthenticationListener {
//        public abstract void onSuccess();
//
//        public abstract void onFail(String error);
//    }


}