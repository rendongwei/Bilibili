package com.don.bilibili.activity.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.don.bilibili.annotation.FullScreen;
import com.don.bilibili.annotation.ScreenOrientation;
import com.don.bilibili.annotation.TranslucentStatusBar;
import com.don.bilibili.utils.DisplayUtil;
import com.don.bilibili.utils.EmptyUtil;
import com.don.bilibili.utils.ExitUtil;
import com.don.bilibili.utils.StatusBarUtil;


public abstract class BaseActivity extends AppCompatActivity {

    protected AppCompatActivity mActivity;
    protected Context mContext;
    private View mContentView;
    private View mStatusBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mActivity = BaseActivity.this;
        mContext = BaseActivity.this;
        mContentView = View.inflate(mContext, getContentView(), null);
        ExitUtil.getInstance().addActivity(mActivity);
        setFullScreen();
        setScreenOrientation();
        setStatusBarTranslucent();
        setContentView(mContentView);
        bindViews();
        bindListener();
        init();
    }

    @Override
    protected void onDestroy() {
        ExitUtil.getInstance().removeActivity(mActivity);
        super.onDestroy();
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


    /**
     * 处理全屏
     */
    private void setFullScreen() {
        boolean isFullScreen = getClass().isAnnotationPresent(FullScreen.class);
        if (isFullScreen) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    /**
     * 处理屏幕方向
     */
    private void setScreenOrientation() {
        int orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        if (getClass().isAnnotationPresent(
                ScreenOrientation.class)) {
            ScreenOrientation screenOrientation = getClass()
                    .getAnnotation(ScreenOrientation.class);
            orientation = screenOrientation.orientation();
        }
        setRequestedOrientation(orientation);
    }

    /**
     * 处理透明状态栏
     */
    private void setStatusBarTranslucent() {
        boolean isFullScreen = getClass().isAnnotationPresent(FullScreen.class);
        boolean isTranslucent = getClass().isAnnotationPresent(TranslucentStatusBar.class);
        boolean isArrowTranslucent = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (!isFullScreen && isTranslucent && isArrowTranslucent) {
            StatusBarUtil.initBar(mActivity);
            LinearLayout layout = new LinearLayout(mContext);
            layout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            layout.setOrientation(LinearLayout.VERTICAL);
            mStatusBar = new View(mContext);
            layout.addView(mStatusBar, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mStatusBar
                    .getLayoutParams();
            int statusBarHeight = StatusBarUtil.getStatusBarHeight(mContext);
            params.height = statusBarHeight == 0 ? DisplayUtil.dip2px(mContext,
                    25) : statusBarHeight;
            mStatusBar.setLayoutParams(params);
            TranslucentStatusBar translucentStatusBar = BaseActivity.this
                    .getClass().getAnnotation(TranslucentStatusBar.class);
            if (translucentStatusBar.color() != -1) {
                mStatusBar.setBackgroundResource(translucentStatusBar.color());
            } else if (!EmptyUtil.isEmpty(translucentStatusBar.sColor())) {
                mStatusBar.setBackgroundColor(Color
                        .parseColor(translucentStatusBar.sColor()));
            } else {
                mStatusBar.setBackgroundColor(Color.BLACK);
            }
            View v = View.inflate(mContext, getContentView(), null);
            layout.addView(v, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            layout.setBackgroundDrawable(v.getBackground());
            mContentView = layout;
        }
    }

    protected void changeStatusBarColor(int color) {
        if (mStatusBar == null) {
            return;
        }
        mStatusBar.setBackgroundResource(color);
    }

    protected void changeStatusBarColor(String color) {
        if (mStatusBar == null) {
            return;
        }
        mStatusBar.setBackgroundColor(Color.parseColor(color));
    }

    protected void changeStatusBarVisibility(int visibility) {
        if (mStatusBar == null) {
            return;
        }
        mStatusBar.setVisibility(visibility);
    }

    protected void intentTo(Class<?> cls) {
        startActivity(new Intent(mContext, cls));
    }
}
