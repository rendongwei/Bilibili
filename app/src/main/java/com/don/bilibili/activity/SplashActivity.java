package com.don.bilibili.activity;

import android.os.Handler;

import com.don.bilibili.R;
import com.don.bilibili.activity.base.BindActivity;
import com.don.bilibili.annotation.FullScreen;

@FullScreen
public class SplashActivity extends BindActivity {

    Handler mHandler = new Handler();

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void init() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                intentTo(HomeActivity.class);
                finish();
            }
        },2000);
    }
}
