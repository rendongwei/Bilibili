package com.don.bilibili.activity.live;

import android.graphics.Rect;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.don.bilibili.Json.Json;
import com.don.bilibili.R;
import com.don.bilibili.activity.base.TranslucentStatusBarActivity;
import com.don.bilibili.adapter.LiveAreaAdapter;
import com.don.bilibili.adapter.LiveAreaTagAdapter;
import com.don.bilibili.annotation.Id;
import com.don.bilibili.annotation.OnClick;
import com.don.bilibili.cache.DataManager;
import com.don.bilibili.http.HttpManager;
import com.don.bilibili.model.HomeLiveCategoryLive;
import com.don.bilibili.model.HomeLiveCategoryLivePartition;
import com.don.bilibili.utils.DisplayUtil;
import com.don.bilibili.utils.EmptyUtil;
import com.don.bilibili.utils.Util;
import com.don.bilibili.view.DividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveAreaActivity extends TranslucentStatusBarActivity implements View.OnClickListener {

    @Id(id = R.id.live_area_layout_back)
    @OnClick
    private LinearLayout mLayoutBack;
    @Id(id = R.id.live_area_tv_title)
    private TextView mTvTitle;
    @Id(id = R.id.live_area_layout_search)
    @OnClick
    private LinearLayout mLayoutSearch;
    @Id(id = R.id.live_area_layout_tag)
    private RelativeLayout mLayoutTag;
    @Id(id = R.id.live_area_lv_tag)
    private RecyclerView mLvTag;
    @Id(id = R.id.live_area_layout_tag_more)
    @OnClick
    private LinearLayout mLayoutTagMore;
    @Id(id = R.id.live_area_layout_refresh)
    private SwipeRefreshLayout mLayoutRefresh;
    @Id(id = R.id.live_area_layout_scroll)
    private NestedScrollView mLayoutScroll;
    @Id(id = R.id.live_area_layout_container)
    private LinearLayout mLayoutContainer;
    @Id(id = R.id.live_area_lv_display)
    private RecyclerView mLvDisplay;
    @Id(id = R.id.live_area_layout_error)
    private LinearLayout mLayoutError;

    private HomeLiveCategoryLivePartition mPartition;
    private List<HomeLiveCategoryLive> mLives = new ArrayList<HomeLiveCategoryLive>();
    private LiveAreaAdapter mAdapter;
    private int mPage = 1;
    private String mTag = "";
    private boolean mIsLoading = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_live_area;
    }

    @Override
    protected void bindListener() {
        mLayoutRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                getArea(true);
            }
        });
        mLayoutScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX,
                                       int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v
                        .getMeasuredHeight())) {
                    getArea(false);
                }
            }
        });
    }

    @Override
    protected void init() {
        mPartition = getIntent().getParcelableExtra("partition");
        mTvTitle.setText(mPartition.getName());
        Util.initRefresh(mLayoutRefresh);
        initRecyclerView();
        mLayoutRefresh.post(new Runnable() {
            @Override
            public void run() {
                mLayoutRefresh.setRefreshing(true);
            }
        });
        getTag();
        getArea(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.live_area_layout_back:
                finish();
                break;

            case R.id.live_area_layout_search:

                break;
        }
    }

    private void initRecyclerView() {
        mLvTag.setNestedScrollingEnabled(false);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        manager.setAutoMeasureEnabled(true);
        manager.setSmoothScrollbarEnabled(true);
        mLvTag.setLayoutManager(manager);
        mLvTag.addItemDecoration(new DividerItemDecoration(new DividerItemDecoration.OnDividerItemDecorationListener() {
            @Override
            public Rect getOffsets(int position) {
                int width = DisplayUtil.dip2px(mContext, 15);
                int halfWidth = width / 2;
                int left = halfWidth;
                int top = 0;
                int right = halfWidth;
                int bottom = 0;
                return new Rect(left, top, right, bottom);
            }
        }));

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
                mAdapter = new LiveAreaAdapter(mContext, mLives);
                mLvDisplay.setAdapter(mAdapter);
            } else {
                int itemCount = lives.size();
                int positionStart = mLives.size();
                mAdapter.notifyItemRangeInserted(positionStart, itemCount);
            }
        }
    }

    public void changeTag(String tag) {
        mTag = tag;
        mLayoutRefresh.setRefreshing(true);
        getArea(true);
    }

    private void getTag() {
        Map<String, List<String>> cache = DataManager.getInstance().getLiveAreaTagCache();
        if (EmptyUtil.isEmpty(cache)) {
            Call<JSONObject> call = HttpManager.getInstance().getApiSevice().getUrl("http://api.live.bilibili.com/AppIndex/tags?_device=android&_hwid=TXkfLxcmRXdGfkgqVipLeU18SCocfz9MKF9uWD9DJR1oDHlIfkp6Q3NDekJ1Qw&appkey=1d8b6e7d45233436&build=509001&mobi_app=android&platform=android&src=bili&trace_id=20170712132700022&ts=1499837242&version=5.9.1.509001&sign=88e089e2d99b1f3c9d023e300ffbcd8e");
            call.enqueue(new Callback<JSONObject>() {
                @Override
                public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                    if (response.body().optInt("code", -1) == 0) {
                        JSONObject object = response.body()
                                .optJSONObject("data");
                        if (object != null) {
                            Map<String, List<String>> cache = new HashMap<String, List<String>>();
                            Iterator<String> iterator = object.keys();
                            while (iterator.hasNext()) {
                                String key = iterator.next();
                                List<String> value = new ArrayList<String>();
                                JSONArray array = object
                                        .optJSONArray(key);
                                for (int i = 0; i < array.length(); i++) {
                                    value.add(array.optString(i));
                                }
                                cache.put(key, value);
                            }
                            DataManager.getInstance().setLiveAreaTagCache(cache);
                        }
                    }
                    Map<String, List<String>> cache = DataManager.getInstance().getLiveAreaTagCache();
                    if (cache.containsKey(mPartition.getId() + "")) {
                        mLayoutTag.setVisibility(View.VISIBLE);
                        List<String> tags = new ArrayList<String>(cache.get(mPartition.getId() + ""));
                        tags.add(0, "全部");
                        mLvTag.setAdapter(new LiveAreaTagAdapter(
                                mContext, tags, true, mTag));
                    }
                }

                @Override
                public void onFailure(Call<JSONObject> call, Throwable t) {
                }
            });
        } else {
            if (cache.containsKey(mPartition
                    .getId() + "")) {
                mLayoutTag.setVisibility(View.VISIBLE);
                List<String> tags = new ArrayList<String>(cache
                        .get(mPartition.getId() + ""));
                tags.add(0, "全部");
                mLvTag.setAdapter(new LiveAreaTagAdapter(mContext, tags, true,
                        mTag));
            }
        }
    }

    private void getArea(final boolean isPull) {
        if (isPull) {
            mPage = 1;
        }
        if (mIsLoading) {
            return;
        }
        String url = "http://api.live.bilibili.com/mobile/rooms?_device=android&_hwid=TXkfLxcmRXdGfkgqVipLeU18SCocfz9MKF9uWD9DJR1oDHlIfkp6Q3NDekJ1Qw&appkey=1d8b6e7d45233436&area_id="
                + mPartition.getId()
                + "&build=509001&mobi_app=android&page="
                + mPage
                + "&platform=android&sort="
                + "recommend"
                + "&src=bili&trace_id=20170712132700022&ts=1499837242&version=5.9.1.509001&sign=728568d0225f65b4ff4fafcd78574634";
        if (!EmptyUtil.isEmpty(mTag)) {
            try {
                url += "&tag=" + URLEncoder.encode(mTag, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        Call<JSONObject> call = HttpManager.getInstance().getApiSevice().getUrl(url);
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
