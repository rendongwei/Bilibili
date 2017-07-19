package com.don.libirary.activity;

import com.don.libirary.util.BindUtil;

public abstract class BindActivity extends BaseActivity {
    @Override
    protected void bindViews() {
        BindUtil.bind(mActivity);
    }
}
