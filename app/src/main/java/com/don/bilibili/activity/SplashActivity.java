package com.don.bilibili.activity;

import com.don.bilibili.R;
import com.don.bilibili.activity.base.BindActivity;
import com.don.bilibili.annotation.FullScreen;

@FullScreen
public class SplashActivity extends BindActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void init() {

    }
}
