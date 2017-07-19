package com.don.bilibili.fragment.base;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
    protected Activity mActivity;
    protected Fragment mFragment;
    protected Context mContext;
    protected View mView;

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        mContext = activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
        mContext = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragment = this;
        mView = inflater.inflate(getContentView(), container, false);
        bindViews();
        bindListener();
        init();
        return mView;
    }

    /**
     * 获取布局resId
     *
     * @return resId
     */
    protected abstract int getContentView();

    /**
     * 绑定控件
     */
    protected abstract void bindViews();

    /**
     * 绑定事件
     */
    protected abstract void bindListener();

    /**
     * 初始化操作
     */
    protected abstract void init();

    public View findViewById(int id) {
        return mView.findViewById(id);
    }

    public View getView() {
        return mView;
    }
}
