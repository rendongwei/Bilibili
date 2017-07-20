package com.don.bilibili.utils;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;

import com.don.bilibili.R;

public class Util {
    public static void initRefresh(SwipeRefreshLayout layout) {
        layout.setProgressBackgroundColor(R.color.white);
        layout.setColorSchemeResources(R.color.pink);
        layout.setSize(SwipeRefreshLayout.DEFAULT);
        layout.setProgressViewEndTarget(true,
                DisplayUtil.dip2px(layout.getContext(), 60));
    }

    public static GridLayoutManager getGridLayoutManager(Context context,int spanCount,GridLayoutManager.SpanSizeLookup spanSizeLookup){
        GridLayoutManager manager = new GridLayoutManager(context,spanCount);
        manager.setAutoMeasureEnabled(true);
        manager.setSmoothScrollbarEnabled(true);
        if (spanSizeLookup != null) manager.setSpanSizeLookup(spanSizeLookup);
        return  manager;
    }
}
