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
import com.don.bilibili.model.HomeLiveBanner;
import com.don.bilibili.model.HomeLiveCategory;
import com.don.bilibili.model.HomeLiveCategoryBanner;
import com.don.bilibili.model.HomeLiveCategoryLive;
import com.don.bilibili.R;
import com.don.bilibili.adapter.HomeLiveCategoryAdapter;
import com.don.bilibili.adapter.HomeLiveRecommendAdapter;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
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
    private HomeLiveRecommendAdapter mCategoryRecommendAdapter;

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
        mLayoutRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                getCommon();
            }
        });
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
                int width = DisplayUtil.dip2px(mContext, 15);
                int halfWidth = width / 2;
                int left = 0;
                int top = 0;
                int right = 0;
                int bottom = 0;
                if (position == 0) {
                    left = width;
                    right = width;
                } else if (position < 7) {
                    bottom = width;
                    if (position % 2 == 1) {
                        left = width;
                        right = halfWidth;
                    } else if (position % 2 == 0) {
                        left = halfWidth;
                        right = width;
                    }
                } else if (position == 7) {
                    left = width;
                    right = width;
                    bottom = width;
                } else if (position < 14) {
                    bottom = position > 11 ? 0 : width;
                    if (position % 2 == 0) {
                        left = width;
                        right = halfWidth;
                    } else if (position % 2 == 1) {
                        left = halfWidth;
                        right = width;
                    }
                } else {
                    left = width;
                    right = width;
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
                int width = DisplayUtil.dip2px(mContext, 15);
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

            if (mCategoryRecommend != null) {
                mCategoryRecommendAdapter = new HomeLiveRecommendAdapter(
                        mContext, this, mCategoryRecommend,
                        mCategoryRecommendBanner);
                mLvRecommend.setAdapter(mCategoryRecommendAdapter);
            }

            if (!EmptyUtil.isEmpty(mCategories)) {
                mCategoryAdapter = new HomeLiveCategoryAdapter(mContext, this, mCategories);
                mLvCategory.setAdapter(mCategoryAdapter);
            }
        }
        mLayoutRefresh.setRefreshing(false);
    }

    private void getCommon() {
        Call<JSONObject> call = HttpManager.getInstance().getApiSevice().getUrl("http://api.live.bilibili.com/AppNewIndex/common?_device=android&access_key=58da0ccf15c44446a027990e5b90eab6&appkey=1d8b6e7d45233436&build=506000&mobi_app=android&platform=android&scale=xxhdpi&ts=1497417266&sign=78cb52d64155cc90ed303d2d3c8be9cb");
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
                getRecommend();
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                getRecommend();
            }
        });
    }

    private void getRecommend() {
        Call<JSONObject> call = HttpManager.getInstance().getApiSevice().getUrl("http://api.live.bilibili.com/AppNewIndex/recommend?_device=android&access_key=58da0ccf15c44446a027990e5b90eab6&appkey=1d8b6e7d45233436&build=506000&mobi_app=android&platform=android&scale=xxhdpi&ts=1497418171&sign=f079efd3c40deab99f7cf65de485ce82");
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.body().optInt("code", -1) == 0) {
                    JSONObject object = response.body().optJSONObject("data");
                    if (object != null) {
                        JSONObject o = object
                                .optJSONObject("recommend_data");
                        if (o != null) {
                            mCategoryRecommend = new HomeLiveCategory();
                            mCategoryRecommend.parse(o);
                            JSONArray bannerArray = o
                                    .optJSONArray("banner_data");
                            if (bannerArray != null
                                    && bannerArray.length() > 0) {
                                mCategoryRecommendBanner = new HomeLiveCategoryBanner();
                                JSONObject bannerObject = bannerArray
                                        .optJSONObject(0);
                                if (bannerObject.optInt("is_clip", -1) == -1) {
                                    HomeLiveCategoryLive live = new HomeLiveCategoryLive();
                                    live.parse(bannerObject);
                                    mCategoryRecommendBanner
                                            .setLive(live);
                                } else {
                                    mCategoryRecommendBanner
                                            .parse(bannerObject);
                                }
                            }
                        }
                    }
                }
                setData();
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                setData();
            }
        });
    }

    public void getRecommendRefresh() {
        Call<JSONObject> call = HttpManager.getInstance().getApiSevice().getUrl("http://api.live.bilibili.com/AppIndex/recommendRefresh?_device=android&appkey=1d8b6e7d45233436&build=506000&mobi_app=android&platform=android&scale=xxhdpi&ts=1497574830&sign=8789ca8936efd1f21d3d4cd8c303b9f3");
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.body().optInt("code", -1) == 0) {
                    JSONObject object = response.body().optJSONObject("data");
                    if (object != null) {
                        List<HomeLiveCategoryLive> lives = Json
                                .parseJsonArray(
                                        HomeLiveCategoryLive.class,
                                        object.optJSONArray("lives"));
                        mCategoryRecommend.getLives().clear();
                        mCategoryRecommend.getLives().addAll(lives);
                        JSONArray bannerArray = object
                                .optJSONArray("banner_data");
                        if (bannerArray != null
                                && bannerArray.length() > 0) {
                            mCategoryRecommendBanner = new HomeLiveCategoryBanner();
                            JSONObject bannerObject = bannerArray
                                    .optJSONObject(0);
                            if (bannerObject.optInt("is_clip", -1) == -1) {
                                HomeLiveCategoryLive live = new HomeLiveCategoryLive();
                                live.parse(bannerObject);
                                mCategoryRecommendBanner.setLive(live);
                            } else {
                                mCategoryRecommendBanner
                                        .parse(bannerObject);
                            }
                            mCategoryRecommendAdapter
                                    .setBanner(mCategoryRecommendBanner);
                        }
                        mCategoryRecommendAdapter.setRefreshing(false,
                                true);
                        return;
                    }
                }
                mCategoryRecommendAdapter.setRefreshing(false, false);
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                mCategoryRecommendAdapter.setRefreshing(false, false);
            }
        });
    }

    public void getCategoryRefresh(final String area, final int listPosition) {
        String[] urls = new String[] {
                "http://api.live.bilibili.com/AppIndex/dynamic?_device=android&appkey=1d8b6e7d45233436&area=draw&build=506000&mobi_app=android&platform=android&ts=1497601395&sign=c6745f5d696ae56b8f7ef34ad70bf0e6",
                "http://api.live.bilibili.com/AppIndex/dynamic?_device=android&appkey=1d8b6e7d45233436&area=ent-life&build=506000&mobi_app=android&platform=android&ts=1497834030&sign=ee2cc1f66aa8542e143b78bbb1f4c4b7",
                "http://api.live.bilibili.com/AppIndex/dynamic?_device=android&appkey=1d8b6e7d45233436&area=sing-dance&build=506000&mobi_app=android&platform=android&ts=1497834281&sign=c3efe076b074d3d6fc7cddb2a08568a8",
                "http://api.live.bilibili.com/AppIndex/dynamic?_device=android&appkey=1d8b6e7d45233436&area=mobile-game&build=506000&mobi_app=android&platform=android&ts=1497834322&sign=a0bfc83eee1d0986739583a72c686651",
                "http://api.live.bilibili.com/AppIndex/dynamic?_device=android&appkey=1d8b6e7d45233436&area=single&build=506000&mobi_app=android&platform=android&ts=1497834367&sign=47d03855e86bc5e74fcdff91d27a0af7",
                "http://api.live.bilibili.com/AppIndex/dynamic?_device=android&appkey=1d8b6e7d45233436&area=online&build=506000&mobi_app=android&platform=android&ts=1497834906&sign=23ad7bb16722a25751865eeee3a64a12",
                "http://api.live.bilibili.com/AppIndex/dynamic?_device=android&appkey=1d8b6e7d45233436&area=e-sports&build=506000&mobi_app=android&platform=android&ts=1497834932&sign=69667f6782a996511bbed21024bf9427",
                "http://api.live.bilibili.com/AppIndex/dynamic?_device=android&appkey=1d8b6e7d45233436&area=otaku&build=506000&mobi_app=android&platform=android&ts=1497834969&sign=5dcce13365c4daf79f09d9b018554bdc",
                "http://api.live.bilibili.com/AppIndex/dynamic?_device=android&appkey=1d8b6e7d45233436&area=movie&build=506000&mobi_app=android&platform=android&ts=1497834995&sign=db4d9d6cc62eb0d17502c140e8fc7ad2" };
        String url = "";
        for (int i = 0; i < urls.length; i++) {
            if (urls[i].contains(area)) {
                url = urls[i];
            }
        }
        Call<JSONObject> call = HttpManager.getInstance().getApiSevice().getUrl(url);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.body().optInt("code", -1) == 0) {
                    List<HomeLiveCategoryLive> lives = Json
                            .parseJsonArray(HomeLiveCategoryLive.class,
                                    response.body().optJSONArray("data"));
                    for (HomeLiveCategory category : mCategories) {
                        if (category.getPartition().getArea()
                                .equals(area)) {
                            category.getLives().clear();
                            category.getLives().addAll(lives);
                            Collections.shuffle(category.getLives());
                            break;
                        }
                    }
                    mCategoryAdapter.setRefreshing(area, listPosition,
                            false, true);
                    return;
                }
                mCategoryAdapter.setRefreshing(area, listPosition,
                        false, false);
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                mCategoryAdapter.setRefreshing(area, listPosition,
                        false, false);
            }
        });
    }
}
