package com.don.bilibili.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.don.bilibili.R;
import com.don.bilibili.activity.HomeActivity;
import com.don.bilibili.adapter.TabAdapter;
import com.don.bilibili.annotation.Id;
import com.don.bilibili.annotation.OnClick;
import com.don.bilibili.fragment.base.BindFragment;
import com.don.bilibili.fragment.home.LiveFragment;
import com.don.bilibili.utils.DisplayUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BindFragment implements View.OnClickListener {

    @Id(id = R.id.home_layout_top_menu)
    @OnClick
    private LinearLayout mLayoutTopMenu;
    @Id(id = R.id.home_layout_top_bml)
    @OnClick
    private LinearLayout mLayoutTopBml;
    @Id(id = R.id.home_layout_top_game_center)
    @OnClick
    private LinearLayout mLayoutTopGameCenter;
    @Id(id = R.id.home_layout_top_download)
    @OnClick
    private LinearLayout mLayoutTopDownload;
    @Id(id = R.id.home_layout_top_search)
    @OnClick
    private LinearLayout mLayoutTopSearch;
    @Id(id = R.id.home_layout_tab)
    private TabLayout mLayoutTab;
    @Id(id = R.id.home_vp_display)
    private ViewPager mVpDisplay;

    @Id(id = R.id.home_layout_recommend_head)
    private LinearLayout mLayoutRecommendHead;
    @Id(id = R.id.home_tv_recommend_head_comprehensive)
    @OnClick
    private TextView mTvRecommendHeadComprehensive;
    @Id(id = R.id.home_layout_recommend_head_rank)
    @OnClick
    private LinearLayout mLayoutRecommendHeadRank;
    @Id(id = R.id.home_iv_recommend_head_rank)
    private ImageView mIvRecommendHeadRank;
    @Id(id = R.id.home_layout_recommend_head_attention)
    @OnClick
    private LinearLayout mLayoutRecommendHeadAttention;
    @Id(id = R.id.home_iv_recommend_head_attention)
    private ImageView mIvRecommendHeadAttention;

    private LiveFragment mLiveFragment;

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void bindListener() {
        mVpDisplay.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                mLayoutRecommendHead.setVisibility(arg0 == 1 ? View.VISIBLE
                        : View.GONE);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    @Override
    protected void init() {
        List<Fragment> mFragments = new ArrayList<Fragment>();
        mLiveFragment = new LiveFragment();
        mFragments.add(mLiveFragment);
//        mVpDisplay.setOffscreenPageLimit(6);
        mVpDisplay.setAdapter(new TabAdapter(getChildFragmentManager(),
                mFragments,"直播", "推荐", "追番", "分区", "动态", "发现"));
        mLayoutTab.setupWithViewPager(mVpDisplay);
//        mVpDisplay.setCurrentItem(1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_layout_top_menu:
                ((HomeActivity) mActivity).showMenu();
                break;

            case R.id.home_layout_recommend_head:

                break;

            case R.id.home_layout_recommend_head_rank:

                break;

            case R.id.home_layout_recommend_head_attention:

                break;
        }
    }

    private void setIndicator() {
        Class<?> tabLayout = mLayoutTab.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            return;
        }

        tabStrip.setAccessible(true);
        LinearLayout ll_tab = null;
        try {
            ll_tab = (LinearLayout) tabStrip.get(mLayoutTab);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = DisplayUtil.dip2px(mContext, 15);
        int right = DisplayUtil.dip2px(mContext, 15);

        for (int i = 0; i < ll_tab.getChildCount(); i++) {
            View child = ll_tab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }
}
