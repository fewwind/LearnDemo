package com.lineprogressbutton.fewwind.myapplication.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import com.lineprogressbutton.fewwind.myapplication.R;
import com.lineprogressbutton.fewwind.myapplication.base.BaseActivity;
import com.orhanobut.logger.Logger;
import java.util.List;

/**
 * Created by admin on 2016/8/24.
 */
public class FragmentStateTest extends BaseActivity {

    private int count = 6;
    private String name = "feng";


    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isHaveCarmera();

    }


    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("key1", count);
        outState.putString("key2", name);

        Logger.e("saveInstanceState");
    }


    @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Logger.e("RestoreInstanceState");
        int count = savedInstanceState.getInt("key1");
        String name = savedInstanceState.getString("key2");
        Logger.d(name + count);
    }


    @Override protected int initLayoutId() {
        return R.layout.activity_frag_test;
    }

    public void isHaveCarmera(){
        PackageManager manager = this.getPackageManager();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> resolveInfos = manager.queryIntentActivities(intent,
            PackageManager.MATCH_DEFAULT_ONLY);
        Logger.i("相机的数量---"+resolveInfos.size());

    }
}
