package com.lineprogressbutton.fewwind.myapplication.activity;

import android.os.Bundle;
import butterknife.Bind;
import com.lineprogressbutton.fewwind.myapplication.R;
import com.lineprogressbutton.fewwind.myapplication.base.BaseActivity;
import com.lineprogressbutton.fewwind.myapplication.view.DraggerHelperView;

public class ViewDragHelperActivity extends BaseActivity {

    @Bind(R.id.id_drag_helper_view)
    DraggerHelperView idDragHelperView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override protected int initLayoutId() {
        return R.layout.activity_view_drag_helper;
    }
}
