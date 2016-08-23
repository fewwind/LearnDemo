package com.rcplatform.doubleexposure;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.RCPlatformSDK.ThridPartLoginClient.ClientManager;
import com.RCPlatformSDK.ThridPartLoginClient.ClientStandard;
import com.google.android.gms.common.GooglePlayServicesUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements ClientStandard.OnLoginResultListener  {



    /**
     * 第三方登录控制类
     */
    private ClientStandard.LoginClient loginClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ClientManager.getInstance(this).loginInit();
    }

    @Override
    public void onLoginResultListener(ClientStandard.LoginInfo loginInfo) {
        Log.e("tag",loginInfo.isLoginSuccess+"---登录结果"+loginInfo.userName);
        loginClient.loginOut();
    }

    @OnClick(R.id.ins)
    public void Ins(){
        loginClient = ClientManager.getInstance(this).getInstagramLoginClient();
        loginClient.setOnLoginResultListener(this);
        loginClient.showLoginWindow();
    }

    @OnClick(R.id.google)
    public void google(){
        int statusCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if(statusCode != 0){
            Toast.makeText(this, "No GooglePlayServices Found!", Toast.LENGTH_LONG).show();
            finish();
        }else{
            loginClient = ClientManager.getInstance(this).getGooglePlusLoginClient();
            loginClient.setOnLoginResultListener(this);
            loginClient.showLoginWindow();
        }
    }

    @OnClick(R.id.facebook)
    public void facebook(){
        loginClient = ClientManager.getInstance(this).getFacebookLoginClient();
        loginClient.setOnLoginResultListener(this);
        loginClient.showLoginWindow();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        //把结果传递给第三方登录类
        loginClient.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != loginClient) {
            loginClient.closeClient();
        }
    }
}
