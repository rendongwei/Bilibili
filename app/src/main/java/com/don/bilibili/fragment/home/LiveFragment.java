package com.don.bilibili.fragment.home;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.don.bilibili.R;
import com.don.bilibili.annotation.Id;
import com.don.bilibili.annotation.OnClick;
import com.don.bilibili.fragment.base.BindFragment;
import com.don.bilibili.utils.Util;
import com.don.bilibili.view.AutoScrollViewPager;

public class LiveFragment extends BindFragment implements View.OnClickListener{

    @Id(id = R.id.live_layout_refresh)
    private SwipeRefreshLayout mLayoutRefresh;
    @Id(id = R.id.live_layout_container)
    private LinearLayout mLayoutContainer;
    @Id(id = R.id.live_layout_banner)
    private RelativeLayout mLayoutBanner;
    @Id(id = R.id.live_vp_banner)
    private AutoScrollViewPager mVpBanner;
    @Id(id = R.id.live_layout_banner_point)
    private LinearLayout mLayoutBannerPoint;
    @Id(id = R.id.live_layout_follow_anchor)
    @OnClick
    private LinearLayout mLayoutFollowAnchor;
    @Id(id = R.id.live_layout_live_center)
    @OnClick
    private LinearLayout mLayoutLiveCenter;
    @Id(id = R.id.live_layout_clip_video)
    @OnClick
    private LinearLayout mLayoutClipVideo;
    @Id(id = R.id.live_layout_search_room)
    @OnClick
    private LinearLayout mLayoutSearchRoom;
    @Id(id = R.id.live_layout_all_category)
    @OnClick
    private LinearLayout mLayoutAllCategory;
    @Id(id = R.id.live_lv_recommend)
    private RecyclerView mLvRecommend;
    @Id(id = R.id.live_lv_category)
    private RecyclerView mLvCategory;
    @Id(id = R.id.live_tv_more)
    @OnClick
    private TextView mTvMore;
    @Id(id = R.id.live_layout_error)
    private LinearLayout mLayoutError;

    public LiveFragment(){

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home_live;
    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void init() {
        Util.initRefresh(mLayoutRefresh);
        mLvRecommend.setNestedScrollingEnabled(false);
        mLvRecommend.setLayoutManager(Util.getGridLayoutManager(mContext, 2, new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 || position == 7 || position == 14 ? 2 : 1;
            }
        }));

        mLvCategory.setNestedScrollingEnabled(false);
        mLvCategory.setLayoutManager(Util.getGridLayoutManager(mContext, 2, new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position % 6 == 0 || (position + 1) % 6 == 0 ? 2 : 1;
            }
        }));
    }

    @Override
    public void onClick(View view) {

    }
}
