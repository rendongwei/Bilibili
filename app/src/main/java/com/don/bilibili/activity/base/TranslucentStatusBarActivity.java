package com.don.bilibili.activity.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.don.bilibili.R;
import com.don.bilibili.annotation.TranslucentStatusBar;
import com.don.bilibili.constants.Action;

@TranslucentStatusBar(color = R.color.pink)
public abstract class TranslucentStatusBarActivity extends BindActivity {

    private OnGetSignCallBack mCallBack;

    @Override
    protected void onResume() {
        super.onResume();
        if (mRecieiver != null) {
            IntentFilter filter = new IntentFilter(Action.SIGN_ACITON);
            registerReceiver(mRecieiver, filter);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mRecieiver != null) {
            unregisterReceiver(mRecieiver);
        }
    }

    BroadcastReceiver mRecieiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String method = intent.getStringExtra("method");
            String sign = intent.getStringExtra("sign");
            if (mCallBack != null) {
                mCallBack.callback(method, sign);
            }
        }
    };

    public interface OnGetSignCallBack {
        public abstract void callback(String method, String sign);
    }

    public void setOnGetSignCallBack(OnGetSignCallBack callBack) {
        mCallBack = callBack;
    }
}
