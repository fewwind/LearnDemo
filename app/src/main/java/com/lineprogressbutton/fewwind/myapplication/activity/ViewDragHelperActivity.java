package com.lineprogressbutton.fewwind.myapplication.activity;

import android.os.Bundle;

import com.lineprogressbutton.fewwind.myapplication.R;
import com.lineprogressbutton.fewwind.myapplication.base.BaseActivity;
import com.lineprogressbutton.fewwind.myapplication.view.DraggerHelperView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ViewDragHelperActivity extends BaseActivity {


    @Bind(R.id.id_drag_helper_view)
    DraggerHelperView idDragHelperView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_drag_helper);

        ButterKnife.bind(this);

    }
}
