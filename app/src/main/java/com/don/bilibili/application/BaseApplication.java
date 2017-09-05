package com.don.bilibili.application;

import android.app.Application;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        CrashHandler.getInstance().init(this);
//        CrashHandler.getInstance().setSaveLogs(true);
    }
}
