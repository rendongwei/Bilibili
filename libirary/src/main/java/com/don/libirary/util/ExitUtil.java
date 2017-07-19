package com.don.libirary.util;

import android.app.Activity;
import android.os.Build;
import android.os.Process;

import java.util.ArrayList;
import java.util.List;

public class ExitUtil {

    private static volatile ExitUtil mExit;
    private List<Activity> mActivities = null;

    private ExitUtil() {
        mActivities = new ArrayList<Activity>();
    }

    public static synchronized ExitUtil getInstance() {
        if (mExit == null) {
            mExit = new ExitUtil();
        }
        return mExit;
    }

    public void addActivity(Activity activity) {
        if (activity == null) {
            return;
        }
        mActivities.add(activity);
    }

    public void removeActivity(Activity activity) {
        if (activity == null) {
            return;
        }
        mActivities.remove(activity);
    }

    public void exit() {
        for (Activity activity : mActivities) {
            if (activity == null) {
                continue;
            }
            if (Build.VERSION.SDK_INT >= 17) {
                if (activity.isDestroyed()) {
                    continue;
                }
            }
            activity.finish();
        }
        Process.killProcess(Process.myUid());
        Process.killProcess(Process.myPid());
        Process.killProcess(Process.myTid());
        System.exit(0);
    }
}
