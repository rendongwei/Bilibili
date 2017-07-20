package com.don.bilibili.fragment.home;


import android.graphics.Rect;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.don.bilibili.Json.Json;
import com.don.bilibili.Model.HomeLiveBanner;
import com.don.bilibili.Model.HomeLiveCategory;
import com.don.bilibili.Model.HomeLiveCategoryBanner;
import com.don.bilibili.R;
import com.don.bilibili.adapter.HomeLiveCategoryAdapter;
import com.don.bilibili.adapter.LiveBannerAdapter;
import com.don.bilibili.annotation.Id;
import com.don.bilibili.annotation.OnClick;
import com.don.bilibili.fragment.base.BindFragment;
import com.don.bilibili.http.HttpManager;
import com.don.bilibili.utils.DisplayUtil;
import com.don.bilibili.utils.EmptyUtil;
import com.don.bilibili.utils.Util;
import com.don.bilibili.view.AutoScrollViewPager;
import com.don.bilibili.view.DividerItemDecoration;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveFragment extends BindFragment implements View.OnClickListener {

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

    private List<HomeLiveBanner> mBanners = new ArrayList<HomeLiveBanner>();
    private LiveBannerAdapter mBannerAdapter;
    private HomeLiveCategory mCategoryRecommend;
    private HomeLiveCategoryBanner mCategoryRecommendBanner;
//    private HomeLiveRecommendAdapter mCategoryRecommendAdapter;

    private List<HomeLiveCategory> mCategories = new ArrayList<HomeLiveCategory>();
    private HomeLiveCategoryAdapter mCategoryAdapter;

    public LiveFragment() {

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
        initRecyclerView();
        mLayoutRefresh.setRefreshing(true);
        getCommon();
    }

    @Override
    public void onClick(View view) {

    }

    private void initRecyclerView() {
        mLvRecommend.setNestedScrollingEnabled(false);
        mLvRecommend.setLayoutManager(Util.getGridLayoutManager(mContext, 2, new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 || position == 7 || position == 14 ? 2 : 1;
            }
        }));
        mLvRecommend.addItemDecoration(new DividerItemDecoration(new DividerItemDecoration.OnDividerItemDecorationListener() {
            @Override
            public Rect getOffsets(int position) {
                int width = DisplayUtil.getWidth(mContext);
                int halfWidth = width / 2;
                int left = 0;
                int top = 0;
                int right = 0;
                int bottom = 0;
                if (position % 6 == 0 || (position + 1) % 6 == 0) {
                    left = width;
                    right = width;
                } else {
                    bottom = (position + 1) % 6 == 4 || (position + 1) % 6 == 5 ? 0
                            : width;
                    if (position % 2 == 1) {
                        left = width;
                        right = halfWidth;
                    } else if (position % 2 == 0) {
                        left = halfWidth;
                        right = width;
                    }
                }
                return new Rect(left, top, right, bottom);
            }
        }));

        mLvCategory.setNestedScrollingEnabled(false);
        mLvCategory.setLayoutManager(Util.getGridLayoutManager(mContext, 2, new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position % 6 == 0 || (position + 1) % 6 == 0 ? 2 : 1;
            }
        }));
        mLvCategory.addItemDecoration(new DividerItemDecoration(new DividerItemDecoration.OnDividerItemDecorationListener() {
            @Override
            public Rect getOffsets(int position) {
                int width = DisplayUtil.getWidth(mContext);
                int halfWidth = width / 2;
                int left = 0;
                int top = 0;
                int right = 0;
                int bottom = 0;
                if (position % 6 == 0 || (position + 1) % 6 == 0) {
                    left = width;
                    right = width;
                } else {
                    bottom = (position + 1) % 6 == 4 || (position + 1) % 6 == 5 ? 0
                            : width;
                    if (position % 2 == 1) {
                        left = width;
                        right = halfWidth;
                    } else if (position % 2 == 0) {
                        left = halfWidth;
                        right = width;
                    }
                }
                return new Rect(left, top, right, bottom);
            }
        }));
    }

    private void initBanner() {
        initPoint();
        initPoint(0);
        mBannerAdapter = new LiveBannerAdapter(mContext, mBanners);
        mVpBanner.setAdapter(mBannerAdapter);
        int position = 0;
        position += 1000 * 3;
        mVpBanner.setInterval(3000);
        mVpBanner.setCurrentItem(position, false);
        mVpBanner.startAutoScroll(3000);
    }

    private void initPoint() {
        mLayoutBannerPoint.removeAllViews();
        for (int i = 0; i < mBanners.size(); i++) {
            ImageView imageView = new ImageView(mContext);
            int margin = DisplayUtil.dip2px(mContext, 2);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(margin, margin, margin, margin);
            mLayoutBannerPoint.addView(imageView, params);
        }
    }

    private void initPoint(int position) {
        for (int i = 0; i < mLayoutBannerPoint.getChildCount(); i++) {
            View v = mLayoutBannerPoint.getChildAt(i);
            if (v instanceof ImageView) {
                ImageView point = (ImageView) v;
                if (position == i) {
                    point.setImageResource(R.drawable.ic_banner_ponit_selected);
                } else {
                    point.setImageResource(R.drawable.ic_banner_ponit_normal);
                }
            }
        }
    }

    private void setData() {
        if (EmptyUtil.isEmpty(mBanners) && mCategoryRecommend == null
                && EmptyUtil.isEmpty(mCategories)) {
            mLayoutContainer.setVisibility(View.GONE);
            mLayoutError.setVisibility(View.VISIBLE);
        } else {
            mLayoutContainer.setVisibility(View.VISIBLE);
            mLayoutError.setVisibility(View.GONE);

            if (EmptyUtil.isEmpty(mBanners)) {
                mLayoutBanner.setVisibility(View.GONE);
            } else {
                mLayoutBanner.setVisibility(View.VISIBLE);
                initBanner();
            }

//            if (mCategoryRecommend != null) {
//                mCategoryRecommendAdapter = new HomeLiveRecommendAdapter(
//                        mContext, this, mCategoryRecommend,
//                        mCategoryRecommendBanner);
//                mLvRecommend.setAdapter(mCategoryRecommendAdapter);
//            }
//
            if (!EmptyUtil.isEmpty(mCategories)) {
                mCategoryAdapter = new HomeLiveCategoryAdapter(mContext, mCategories);
                mLvCategory.setAdapter(mCategoryAdapter);
            }
        }
        mLayoutRefresh.setRefreshing(false);
    }

    private void getCommon() {
        Call<JSONObject> call = HttpManager.getInstance().getApiSevice().getCommon("android", "1d8b6e7d45233436", "506000", "android", "android", "xxhdpi", "1497417266", "78cb52d64155cc90ed303d2d3c8be9cb");
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.body().optInt("code", -1) == 0) {
                    JSONObject object = response.body().optJSONObject("data");
                    if (object != null) {
                        mBanners = Json.parseJsonArray(
                                HomeLiveBanner.class,
                                object.optJSONArray("banner"));
                        mCategories = Json.parseJsonArray(
                                HomeLiveCategory.class,
                                object.optJSONArray("partitions"));
                    }
                }
                setData();
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
            }
        });
    }
}
