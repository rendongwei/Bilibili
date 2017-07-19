package com.don.bilibili.activity;

import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.don.bilibili.HomeFragment;
import com.don.bilibili.R;
import com.don.bilibili.activity.base.TranslucentStatusBarActivity;
import com.don.bilibili.annotation.Id;
import com.don.bilibili.fragment.base.FragmentManager;
import com.don.bilibili.utils.DisplayUtil;

public class HomeActivity extends TranslucentStatusBarActivity {
    @Id(id = R.id.home_layout_drawer)
    private DrawerLayout mDrawerLayout;
    @Id(id = R.id.home_layout_menu)
    private LinearLayout mLayoutMenu;

    private FragmentManager mFragmentManager;

    @Override
    protected int getContentView() {
        return R.layout.activity_home;
    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void init() {
        mFragmentManager = new FragmentManager(mActivity);

        ViewGroup.LayoutParams params = mLayoutMenu
                .getLayoutParams();
        params.width = DisplayUtil.getWidth(mContext) / 4 * 3;
        mLayoutMenu.setLayoutParams(params);

        mFragmentManager.showFragment(R.id.home_layout_content,
                HomeFragment.class);
    }

    public void showMenu() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }
}
