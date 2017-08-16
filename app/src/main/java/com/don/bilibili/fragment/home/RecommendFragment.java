package com.don.bilibili.fragment.home;

import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.NestedScrollView.OnScrollChangeListener;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.don.bilibili.R;
import com.don.bilibili.adapter.HomeRecommendAdapter;
import com.don.bilibili.annotation.Id;
import com.don.bilibili.fragment.base.BindFragment;
import com.don.bilibili.http.HttpManager;
import com.don.bilibili.model.HomeRecommend;
import com.don.bilibili.model.HomeRecommendAv;
import com.don.bilibili.utils.EmptyUtil;
import com.don.bilibili.utils.Util;
import com.don.bilibili.view.LineDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecommendFragment extends BindFragment {

    @Id(id = R.id.recommend_layout_refresh)
    private SwipeRefreshLayout mLayoutRefresh;
    @Id(id = R.id.recommend_layout_scroll)
    private NestedScrollView mLayoutScroll;
    @Id(id = R.id.recommend_layout_container)
    private LinearLayout mLayoutContainer;
    @Id(id = R.id.recommend_lv_display)
    private RecyclerView mLvDisplay;
    @Id(id = R.id.recommend_layout_error)
    private LinearLayout mLayoutError;

    private List<HomeRecommend> mRecommends = new ArrayList<HomeRecommend>();
    private HomeRecommendAdapter mAdapter;
    private boolean mIsLoading = false;

    @Override
    protected int getContentView() {
        return R.layout.fragment_home_recommend;
    }

    @Override
    protected void bindListener() {
        mLayoutRefresh.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {
                getRecommend(true);
            }
        });
        mLayoutScroll.setOnScrollChangeListener(new OnScrollChangeListener() {

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX,
                                       int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v
                        .getMeasuredHeight())) {
                    getRecommend(false);
                }
            }
        });
    }

    @Override
    protected void init() {
        Util.initRefresh(mLayoutRefresh);
        initRecyclerView();
        mLayoutRefresh.post(new Runnable() {
            @Override
            public void run() {
                mLayoutRefresh.setRefreshing(true);
            }
        });
        getRecommend(true);
    }

    private void initRecyclerView() {
        mLvDisplay.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setAutoMeasureEnabled(true);
        layoutManager.setSmoothScrollbarEnabled(true);
        mLvDisplay.setLayoutManager(layoutManager);
        mLvDisplay.addItemDecoration(new LineDividerItemDecoration(mContext,
                LinearLayoutManager.VERTICAL));
    }

    private void setData(boolean isPull, List<HomeRecommend> recommends) {
        mIsLoading = false;
        mLayoutRefresh.setRefreshing(false);
        if (isPull) {
            mRecommends.clear();
        }
        if (!EmptyUtil.isEmpty(recommends)) {
            mRecommends.addAll(recommends);
        }
        if (EmptyUtil.isEmpty(mRecommends)) {
            mLayoutContainer.setVisibility(View.GONE);
            mLayoutError.setVisibility(View.VISIBLE);
        } else {
            mLayoutContainer.setVisibility(View.VISIBLE);
            mLayoutError.setVisibility(View.GONE);
            if (mAdapter == null || isPull) {
                mAdapter = new HomeRecommendAdapter(mContext, mRecommends);
                mLvDisplay.setAdapter(mAdapter);
            } else {
                int itemCount = recommends.size();
                int positionStart = mRecommends.size();
                mAdapter.notifyItemRangeInserted(positionStart, itemCount);
            }
        }
    }

    private void getRecommend(final boolean isPull) {
        if (mIsLoading) {
            return;
        }
        Call<JSONObject> call = HttpManager.getInstance().getApiSevice().getUrl("https://app.bilibili.com/x/feed/index?appkey=1d8b6e7d45233436&build=506000&idx=1497856523&login_event=0&mobi_app=android&network=wifi&open_event=&platform=android&pull=" + isPull + "&style=1&ts=1497856515&sign=72c939f235bbb3d9d9d09edb0505a540");
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.body().optInt("code", -1) == 0) {
                    JSONArray array = response.body().optJSONArray("data");
                    if (array != null) {
                        List<HomeRecommend> list = new ArrayList<HomeRecommend>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.optJSONObject(i);
                            String type = object.optString("goto");
                            HomeRecommend recommend = new HomeRecommend();
                            if ("av".equals(type)) {
                                HomeRecommendAv av = new HomeRecommendAv();
                                av.parse(object);
                                recommend.setType(HomeRecommend.Type.AV);
                                recommend.setAv(av);
                                list.add(recommend);
                            }
                        }
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
