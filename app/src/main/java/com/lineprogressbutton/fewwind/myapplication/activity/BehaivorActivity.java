package com.lineprogressbutton.fewwind.myapplication.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import butterknife.Bind;
import com.lineprogressbutton.fewwind.myapplication.R;
import com.lineprogressbutton.fewwind.myapplication.base.BaseActivity;
import com.orhanobut.logger.Logger;
import java.lang.reflect.Field;

public class BehaivorActivity extends BaseActivity {

    @Bind(R.id.id_behavior_btn)
    Button mBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        mBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_MOVE:

                        v.setX(event.getRawX() - v.getWidth() / 2);
                        v.setY(event.getRawY() - (v.getHeight() / 2 + 75));
                        break;
                }

                return true;
            }
        });

        reflectLearn();
    }


    private void reflectLearn() {
        try {
            Class<?> mClass = Class.forName("com.lineprogressbutton.fewwind.myapplication.test.Main");
            Field[] fields = mClass.getFields();
            for (int i = 0; i < fields.length; i++) {
                String name = fields[i].getName();
                Logger.i("field--"+name);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Logger.e("shibai--"+e.toString());
        }

    }


    @Override protected int initLayoutId() {
        return R.layout.activity_behaivor;

    }

}
