package com.don.bilibili.fragment.base;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.don.bilibili.constants.Action;
import com.don.bilibili.utils.BindUtil;

public abstract class BindFragment extends BaseFragment {

    private OnGetSignCallBack mCallBack;

    @Override
    protected void bindViews() {
        BindUtil.bind(mFragment);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mRecieiver != null) {
            IntentFilter filter = new IntentFilter(Action.SIGN_ACITON);
            mActivity.registerReceiver(mRecieiver, filter);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mRecieiver != null) {
            mActivity.unregisterReceiver(mRecieiver);
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
