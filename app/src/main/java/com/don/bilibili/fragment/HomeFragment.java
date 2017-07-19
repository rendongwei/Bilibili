package com.don.bilibili.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.don.bilibili.R;
import com.don.bilibili.annotation.Id;
import com.don.bilibili.annotation.OnClick;
import com.don.bilibili.fragment.base.BindFragment;

public class HomeFragment extends BindFragment implements View.OnClickListener{

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

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void init() {

    }

    @Override
    public void onClick(View view) {

    }
}
