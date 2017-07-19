package com.don.bilibili.activity.base;

import com.don.bilibili.utils.BindUtil;

public abstract class BindActivity extends BaseActivity {
    @Override
    protected void bindViews() {
        BindUtil.bind(mActivity);
    }
}
