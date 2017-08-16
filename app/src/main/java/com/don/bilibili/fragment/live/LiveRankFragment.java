package com.don.bilibili.fragment.live;

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

	private LiveRankSevenDayFragment mSevenDayFragment;
	private LiveRankLoveFragment mLoveFragment;
	private LiveRankFavoriteFragment mFavoriteFragment;

	@Override
	protected int getContentView() {
		return R.layout.fragment_live_rank;
	}

	@Override
	protected void bindListener() {

	}

	@Override
	protected void init() {
		List<Fragment> mFragments = new ArrayList<Fragment>();
		mSevenDayFragment = new LiveRankSevenDayFragment();
		mLoveFragment = new LiveRankLoveFragment();
		mFavoriteFragment = new LiveRankFavoriteFragment();
		mFragments.add(mSevenDayFragment);
		mFragments.add(mLoveFragment);
		mFragments.add(mFavoriteFragment);
		mVpDisplay.setAdapter(new TabAdapter(getChildFragmentManager(),
				mFragments,"七日榜", "有爱榜", "粉丝榜" ));
		mLayoutTab.setupWithViewPager(mVpDisplay);
	}

	@Override
	public void onClick(View v) {

	}
}
