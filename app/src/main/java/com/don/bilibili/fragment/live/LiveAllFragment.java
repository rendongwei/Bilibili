package com.don.bilibili.fragment.live;

import android.graphics.Rect;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.don.bilibili.Json.Json;
import com.don.bilibili.model.HomeLiveCategoryLive;
import com.don.bilibili.R;
import com.don.bilibili.adapter.LiveAllAdapter;
import com.don.bilibili.annotation.Id;
import com.don.bilibili.fragment.base.BindFragment;
import com.don.bilibili.http.HttpManager;
import com.don.bilibili.utils.DisplayUtil;
import com.don.bilibili.utils.EmptyUtil;
import com.don.bilibili.utils.Util;
import com.don.bilibili.view.DividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveAllFragment extends BindFragment {

    @Id(id = R.id.live_all_layout_refresh)
    private SwipeRefreshLayout mLayoutRefresh;
    @Id(id = R.id.live_all_layout_scroll)
    private NestedScrollView mLayoutScroll;
    @Id(id = R.id.live_all_layout_container)
    private LinearLayout mLayoutContainer;
    @Id(id = R.id.live_all_lv_display)
    private RecyclerView mLvDisplay;
    @Id(id = R.id.live_all_layout_error)
    private LinearLayout mLayoutError;

    private int mType;
    private List<HomeLiveCategoryLive> mLives = new ArrayList<HomeLiveCategoryLive>();
    private LiveAllAdapter mAdapter;
    private int mPage = 1;
    private boolean mIsLoading = false;

    public LiveAllFragment(int type) {
        super();
        mType = type;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_live_all;
    }

    @Override
    protected void bindListener() {
        mLayoutRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                getAll(true);
            }
        });
        mLayoutScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX,
                                       int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v
                        .getMeasuredHeight())) {
                    getAll(false);
                }
            }
        });
    }

    @Override
    protected void init() {
        Util.initRefresh(mLayoutRefresh);
        initRecyclerView();
        mLayoutRefresh.setRefreshing(true);
        getAll(true);
    }

    private void initRecyclerView() {
        mLvDisplay.setNestedScrollingEnabled(false);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
        layoutManager.setAutoMeasureEnabled(true);
        layoutManager.setSmoothScrollbarEnabled(true);
        mLvDisplay.setLayoutManager(layoutManager);
        mLvDisplay.addItemDecoration(new DividerItemDecoration(new DividerItemDecoration.OnDividerItemDecorationListener() {
            @Override
            public Rect getOffsets(int position) {
                int width = DisplayUtil.dip2px(mContext, 15);
                int halfWidth = width / 2;
                int left = 0;
                int top = 0;
                int right = 0;
                int bottom = 0;
                bottom = width;
                if (position == 0 || position == 1) {
                    top = width;
                }
                if (position % 2 == 1) {
                    left = halfWidth;
                    right = width;
                } else if (position % 2 == 0) {
                    left = width;
                    right = halfWidth;
                }
                return new Rect(left, top, right, bottom);
            }
        }));
    }

    private void setData(boolean isPull, List<HomeLiveCategoryLive> lives) {
        mIsLoading = false;
        mLayoutRefresh.setRefreshing(false);
        if (isPull) {
            mLives.clear();
        }
        if (!EmptyUtil.isEmpty(lives)) {
            mPage++;
            mLives.addAll(lives);
        }
        if (EmptyUtil.isEmpty(mLives)) {
            mLayoutContainer.setVisibility(View.GONE);
            mLayoutError.setVisibility(View.VISIBLE);
        } else {
            mLayoutContainer.setVisibility(View.VISIBLE);
            mLayoutError.setVisibility(View.GONE);
            if (mAdapter == null || isPull) {
                mAdapter = new LiveAllAdapter(mContext, mLives, mType);
                mLvDisplay.setAdapter(mAdapter);
            } else {
                int itemCount = lives.size();
                int positionStart = mLives.size();
                mAdapter.notifyItemRangeInserted(positionStart, itemCount);
            }
        }
    }

    private void getAll(final boolean isPull) {
        if (isPull) {
            mPage = 1;
        }
        if (mIsLoading) {
            return;
        }
        String content = "";
        switch (mType) {
            case 0:
                content = "suggestion";
                break;

            case 1:
                content = "hottest";
                break;

            case 2:
                content = "latest";
                break;

            case 3:
                content = "roundroom";
                break;
        }
        Call<JSONObject> call = HttpManager.getInstance().getApiSevice().getLiveAll("android", "TXkfLxcmRXdGfkgqVipLeU18SCocfz9MKF9uWD9DJR1oDHlIfkp6Q3NDekJ1Qw", "1d8b6e7d45233436", 0, "509001", "android", mPage, "android", content, "bili", "20170712132700022", "1499837242", "5.9.0.509000", "5ba2da452fa456d589cacdb1ce3d50dc");
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.body().optInt("code", -1) == 0) {
                    JSONArray array = response.body().optJSONArray("data");
                    if (array != null) {
                        List<HomeLiveCategoryLive> list = new ArrayList<HomeLiveCategoryLive>();
                        list = Json.parseJsonArray(
                                HomeLiveCategoryLive.class, array);
                        setData(isPull, list);
                        return;
                    }
                }
                setData(isPull, null);
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                setData(isPull, null);
            }
        });
    }
}
