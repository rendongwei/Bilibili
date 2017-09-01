package com.don.bilibili.fragment.live;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;

import com.don.bilibili.R;
import com.don.bilibili.adapter.TabAdapter;
import com.don.bilibili.annotation.Id;
import com.don.bilibili.fragment.base.BindFragment;

import java.util.ArrayList;
import java.util.List;

public class LiveRankFragment extends BindFragment implements OnClickListener {

    @Id(id = R.id.live_rank_layout_tab)
    private TabLayout mLayoutTab;
    @Id(id = R.id.live_rank_vp_display)
    private ViewPager mVpDisplay;

    private int mId;

    private LiveRankSevenDayFragment mSevenDayFragment;
    private LiveRankLoveFragment mLoveFragment;
    private LiveRankFavoriteFragment mFavoriteFragment;

    public static LiveRankFragment newInstance(int id) {
        LiveRankFragment fragment = new LiveRankFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_live_rank;
    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void init() {
        mId = getArguments().getInt("id");
        List<Fragment> mFragments = new ArrayList<Fragment>();
        mSevenDayFragment = LiveRankSevenDayFragment.newInstance(mId);
        mLoveFragment = LiveRankLoveFragment.newInstance(mId);
        mFavoriteFragment = LiveRankFavoriteFragment.newInstance(mId);
        mFragments.add(mSevenDayFragment);
        mFragments.add(mLoveFragment);
        mFragments.add(mFavoriteFragment);
        mVpDisplay.setOffscreenPageLimit(3);
        mVpDisplay.setAdapter(new TabAdapter(getChildFragmentManager(),
                mFragments, "七日榜", "有爱榜", "粉丝榜"));
        mLayoutTab.setupWithViewPager(mVpDisplay);
        mVpDisplay.setCurrentItem(0);
    }

    @Override
    public void onClick(View v) {

    }

    public void getSignCallBack(String method, String sign) {
        if ("http://api.live.bilibili.com/AppRoom/medalRankList?".equals(method) && mFavoriteFragment != null) {
            mFavoriteFragment.getLiveRankFavorite(sign);
        }
        if ("http://api.live.bilibili.com/AppRoom/opTop?".equals(method) && mLoveFragment != null) {
            mLoveFragment.getLiveRankLove(sign);
        }
        if ("http://api.live.bilibili.com/AppRoom/getGiftTop?".equals(method) && mSevenDayFragment != null) {
            mSevenDayFragment.getLiveRankSevenDay(sign);
        }
    }
}
