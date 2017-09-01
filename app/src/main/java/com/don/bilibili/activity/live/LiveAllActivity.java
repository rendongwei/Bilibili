package com.don.bilibili.activity.live;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.don.bilibili.R;
import com.don.bilibili.activity.base.TranslucentStatusBarActivity;
import com.don.bilibili.adapter.TabAdapter;
import com.don.bilibili.annotation.Id;
import com.don.bilibili.annotation.OnClick;
import com.don.bilibili.fragment.live.LiveAllFragment;

import java.util.ArrayList;
import java.util.List;

public class LiveAllActivity extends TranslucentStatusBarActivity implements View.OnClickListener {

    @Id(id = R.id.live_all_layout_back)
    @OnClick
    private LinearLayout mLayoutBack;
    @Id(id = R.id.live_all_layout_search)
    @OnClick
    private LinearLayout mLayoutSearch;
    @Id(id = R.id.live_all_layout_tab)
    private TabLayout mLayoutTab;
    @Id(id = R.id.live_all_vp_display)
    private ViewPager mVpDisplay;

    @Override
    protected int getContentView() {
        return R.layout.activity_live_all;
    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void init() {
        List<Fragment> mFragments = new ArrayList<Fragment>();

        mFragments.add(LiveAllFragment.newInstance(0));
        mFragments.add(LiveAllFragment.newInstance(1));
        mFragments.add(LiveAllFragment.newInstance(2));
        mFragments.add(LiveAllFragment.newInstance(3));

        mVpDisplay.setAdapter(new TabAdapter(getSupportFragmentManager(),
                mFragments, "推荐直播", "最热直播", "最新开播", "视频轮播"));
        mLayoutTab.setupWithViewPager(mVpDisplay);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.live_all_layout_back:
                finish();
                break;

            case R.id.live_all_layout_search:

                break;
        }
    }
}
