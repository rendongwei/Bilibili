package com.don.bilibili.fragment.home;

import android.graphics.Rect;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.don.bilibili.Json.Json;
import com.don.bilibili.R;
import com.don.bilibili.adapter.HomeEpisodeAdapter;
import com.don.bilibili.annotation.Id;
import com.don.bilibili.annotation.OnClick;
import com.don.bilibili.fragment.base.BindFragment;
import com.don.bilibili.http.HttpManager;
import com.don.bilibili.model.HomeEpisode;
import com.don.bilibili.model.HomeEpisodeFoot;
import com.don.bilibili.utils.DisplayUtil;
import com.don.bilibili.utils.EmptyUtil;
import com.don.bilibili.utils.Util;
import com.don.bilibili.view.DividerItemDecoration;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EpisodeFragment extends BindFragment implements
        OnClickListener {

    @Id(id = R.id.episode_layout_refresh)
    private SwipeRefreshLayout mLayoutRefresh;
    @Id(id = R.id.episode_layout_bangumi)
    @OnClick
    private LinearLayout mLayoutBangumi;
    @Id(id = R.id.episode_layout_domestic)
    @OnClick
    private LinearLayout mLayoutDomestic;
    @Id(id = R.id.episode_layout_timeline)
    @OnClick
    private LinearLayout mLayoutTimeline;
    @Id(id = R.id.episode_layout_index)
    @OnClick
    private LinearLayout mLayoutIndex;
    @Id(id = R.id.episode_v_login)
    @OnClick
    private View mVLogin;
    @Id(id = R.id.episode_layout_container)
    private LinearLayout mLayoutContainer;
    @Id(id = R.id.episode_layout_bangumi_more)
    @OnClick
    private LinearLayout mLayoutBangumiMore;
    @Id(id = R.id.episode_lv_bangumi)
    private RecyclerView mLvBangumi;
    @Id(id = R.id.episode_layout_domestic_more)
    @OnClick
    private LinearLayout mLayoutDomesticMore;
    @Id(id = R.id.episode_lv_domestic)
    private RecyclerView mLvDomestic;
    @Id(id = R.id.episode_layout_error)
    private LinearLayout mLayoutError;

    private List<HomeEpisode> mBangumis = new ArrayList<HomeEpisode>();
    private List<HomeEpisodeFoot> mBangumiFoots = new ArrayList<HomeEpisodeFoot>();
    private List<HomeEpisode> mDomestics = new ArrayList<HomeEpisode>();
    private List<HomeEpisodeFoot> mDomesticFoots = new ArrayList<HomeEpisodeFoot>();


    @Override
    protected int getContentView() {
        return R.layout.fragment_home_episode;
    }

    @Override
    protected void bindListener() {
        mLayoutRefresh.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {
                getEpisode();
            }
        });
    }

    @Override
    protected void init() {
        Util.initRefresh(mLayoutRefresh);
        initRecyclerView();
        getEpisode();
    }

    @Override
    public void onClick(View v) {

    }

    private void initRecyclerView() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(new DividerItemDecoration.OnDividerItemDecorationListener() {
            @Override
            public Rect getOffsets(int position) {
                int width = DisplayUtil.dip2px(mContext, 15);
                int halfWidth = width / 2;
                int left = 0;
                int top = 0;
                int right = 0;
                int bottom = 0;
                switch (position) {
                    case 0:
                        top = width / 2;
                        right = halfWidth;
                        break;
                    case 1:
                        top = width / 2;
                        left = halfWidth / 2;
                        right = halfWidth / 2;
                        break;
                    case 2:
                        top = width / 2;
                        left = halfWidth;
                        break;

                    default:
                        top = width / 2;
                        break;
                }
                return new Rect(left, top, right, bottom);
            }
        });
        mLvBangumi.setNestedScrollingEnabled(false);
        mLvDomestic.setNestedScrollingEnabled(false);
        GridLayoutManager bangumiLayoutManager = new GridLayoutManager(
                mContext, 3);
        bangumiLayoutManager.setAutoMeasureEnabled(true);
        bangumiLayoutManager.setSmoothScrollbarEnabled(true);
        bangumiLayoutManager.setSpanSizeLookup(new SpanSizeLookup() {

            @Override
            public int getSpanSize(int position) {
                return position > 2 ? 3 : 1;
            }
        });
        mLvBangumi.setLayoutManager(bangumiLayoutManager);
        mLvBangumi.addItemDecoration(dividerItemDecoration);

        GridLayoutManager domesticLayoutManager = new GridLayoutManager(
                mContext, 3);
        domesticLayoutManager.setAutoMeasureEnabled(true);
        domesticLayoutManager.setSmoothScrollbarEnabled(true);
        domesticLayoutManager.setSpanSizeLookup(new SpanSizeLookup() {

            @Override
            public int getSpanSize(int position) {
                return position > 2 ? 3 : 1;
            }
        });

        mLvDomestic.setLayoutManager(domesticLayoutManager);
        mLvDomestic.addItemDecoration(dividerItemDecoration);
    }

    private void setData() {
        mLayoutRefresh.setRefreshing(false);
        if (EmptyUtil.isEmpty(mBangumis) && EmptyUtil.isEmpty(mBangumiFoots)
                && EmptyUtil.isEmpty(mDomestics)
                && EmptyUtil.isEmpty(mDomesticFoots)) {
            mLayoutContainer.setVisibility(View.GONE);
            mLayoutError.setVisibility(View.VISIBLE);
        } else {
            mLayoutContainer.setVisibility(View.VISIBLE);
            mLayoutError.setVisibility(View.GONE);
            mLvBangumi.setAdapter(new HomeEpisodeAdapter(mContext, mBangumis,
                    mBangumiFoots));
            mLvDomestic.setAdapter(new HomeEpisodeAdapter(mContext, mDomestics,
                    mDomesticFoots));
        }
    }

    private void getEpisode() {
        Call<JSONObject> call = HttpManager.getInstance().getApiSevice().getUrl("https://bangumi.bilibili.com/appindex/follow_index_page?appkey=1d8b6e7d45233436&build=506000&mobi_app=android&platform=android&ts=1498446292&sign=6dbbd7807e3f763c0298d35aff4ae6e3");
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.body().optInt("code", -1) == 0) {
                    JSONObject object = response.body().optJSONObject("result");
                    mBangumis = Json.parseJsonArray(
                            HomeEpisode.class, object
                                    .optJSONObject("recommend_jp")
                                    .optJSONArray("recommend"));
                    mBangumiFoots = Json.parseJsonArray(
                            HomeEpisodeFoot.class, object
                                    .optJSONObject("recommend_jp")
                                    .optJSONArray("foot"));
                    mDomestics = Json.parseJsonArray(
                            HomeEpisode.class, object
                                    .optJSONObject("recommend_cn")
                                    .optJSONArray("recommend"));
                    mDomesticFoots = Json.parseJsonArray(
                            HomeEpisodeFoot.class, object
                                    .optJSONObject("recommend_cn")
                                    .optJSONArray("foot"));
                }
                setData();
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                setData();
            }
        });
    }
}
