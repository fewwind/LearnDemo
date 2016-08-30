package com.lineprogressbutton.fewwind.myapplication.view;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by admin on 2016/8/24.
 */
public class SimpleBehavior extends CoordinatorLayout.Behavior<TextView> {

    public SimpleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TextView child, View dependency) {
        return dependency instanceof Button;
    }


    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, TextView child, View dependency) {

        child.setX(dependency.getX()+200);
        child.setY(dependency.getY()+200);
        child.setText((int)dependency.getX()+"---"+(int)dependency.getY());
        return true;
    }
}
