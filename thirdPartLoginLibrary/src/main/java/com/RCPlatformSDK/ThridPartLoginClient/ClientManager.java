package com.RCPlatformSDK.ThridPartLoginClient;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClientManager {
    private static Context mContext;

    private static FacebookLoginClient facebookLoginClient;
    private static GooglePlusLoginClient googlePlusLoginClient;
    private static InstagramLoginClient instagramLoginClient;

    //private ClientStandard.LoginInfo loginInfo;

    private static ClientManager clientManager;

    /** 登录状态 true:已登录 false: 未登录*/
    private boolean loginState = false;

    /** 登录信息接收者列表*/
    private ArrayList<LoginObserver> receiver = new ArrayList<>();

    public static ClientManager getInstance(Context context) {
        mContext = context;
        if (null == clientManager) {
            synchronized (ClientManager.class) {
                if (null == clientManager) {
                    clientManager = new ClientManager();
//                    facebookLoginClient = new FacebookLoginClient(context);
//                    googlePlusLoginClient = new GooglePlusLoginClient(context);
//                    instagramLoginClient = new InstagramLoginClient(context);

                }
            }
        }

//        else{
//            facebookLoginClient.updateContext(context);
//            googlePlusLoginClient.updateContext(context);
//            instagramLoginClient.updateContext(context);
//        }
        return clientManager;
    }

    /** 各个平台登录sdk初始化（一般在onCreate方法中调用）
     */
	public void loginInit(){
        facebookLoginClient = new FacebookLoginClient(mContext);
        googlePlusLoginClient = new GooglePlusLoginClient(mContext);
        instagramLoginClient = new InstagramLoginClient(mContext);
	}

    @Deprecated
    public boolean getLoginState(){
        return loginState;
    }

    @Deprecated
    public void setLoginState(boolean loginState){
        this.loginState = loginState;
    }

    /**
     * 注册登录监听
     * @param loginListener 登录监听借口
     */
    public void registerLoginListener(LoginObserver loginListener){
        receiver.add(loginListener);
    }

    /**
     * 取消登录监听
     * @param loginListener 登录监听借口
     */
    public void unRegisterLoginListener(LoginObserver loginListener){
        receiver.remove(loginListener);
    }

    public void sendLoginInfo(ClientStandard.LoginInfo loginInfo){
        for(LoginObserver item :receiver){
            item.onLoginDone(loginInfo);
        }
    }

    /**
     * 保存登录信息
     * @param loginInfo 登录信息
     */
    public void setLoginInfo(ClientStandard.LoginInfo loginInfo) {

        if(null == loginInfo || false == loginInfo.isLoginSuccess){
            SharePreferenceHelper.deleteLoginUserInfo(mContext);
        }else{
            SharePreferenceHelper.setLoginUserInfo(mContext, loginInfo.formatField2Json(mContext).toString());
        }
    }

    /**
     * 获取登录信息
     * @return 登录信息
     */
    public ClientStandard.LoginInfo getLoginInfo() {

        ClientStandard.LoginInfo loginInfo = new ClientStandard.LoginInfo();

        String infoStr = SharePreferenceHelper.getLoginUserInfo(mContext);
        if (null == infoStr) return loginInfo;

        //将本地保存的信息读到内存
        try {
            loginInfo.resetFeild(new JSONObject(infoStr));
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            return loginInfo;
        }

    }

    public FacebookLoginClient getFacebookLoginClient() {
        return facebookLoginClient;
    }

    public GooglePlusLoginClient getGooglePlusLoginClient() {
        return googlePlusLoginClient;
    }

    public InstagramLoginClient getInstagramLoginClient() {
        return instagramLoginClient;
    }

    /** 登录消息观察*/
    public interface LoginObserver{
        void onLoginDone(ClientStandard.LoginInfo loginInfo);
    }
}