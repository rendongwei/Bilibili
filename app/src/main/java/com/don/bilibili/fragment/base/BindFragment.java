package com.don.bilibili.fragment.base;


import com.don.bilibili.utils.BindUtil;

public abstract class BindFragment extends BaseFragment {

    @Override
    protected void bindViews() {
        BindUtil.bind(mFragment);
    }

}
