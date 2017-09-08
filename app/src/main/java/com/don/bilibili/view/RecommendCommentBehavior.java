package com.don.bilibili.view;

import android.support.design.widget.CoordinatorLayout;
import android.view.View;

public class RecommendCommentBehavior extends CoordinatorLayout.Behavior<View>{

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        System.out.println("layoutDependsOn");
        return super.layoutDependsOn(parent, child, dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        System.out.println("onDependentViewChanged");
        return super.onDependentViewChanged(parent, child, dependency);
    }
}
