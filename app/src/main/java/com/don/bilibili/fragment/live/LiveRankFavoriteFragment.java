package com.don.bilibili.fragment.live;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.don.bilibili.Json.Json;
import com.don.bilibili.R;
import com.don.bilibili.adapter.LiveRankFavoriteAdapter;
import com.don.bilibili.annotation.Id;
import com.don.bilibili.fragment.base.BindFragment;
import com.don.bilibili.http.HttpManager;
import com.don.bilibili.model.LiveRankFavorite;
import com.don.bilibili.service.SignService;
import com.don.bilibili.utils.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveRankFavoriteFragment extends BindFragment {

    @Id(id = R.id.live_rank_child_lv_display)
    private RecyclerView mLvDisplay;

    private int mId;

    private List<LiveRankFavorite> mFavorites = new ArrayList<LiveRankFavorite>();
    private LiveRankFavoriteAdapter mAdapter;

    private String[] mTs;

    public static LiveRankFavoriteFragment newInstance(int id) {
        LiveRankFavoriteFragment fragment = new LiveRankFavoriteFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_live_rank_child;
    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void init() {
        mId = getArguments().getInt("id");
        initRecyclerView();
        getSign();
    }

    private void initRecyclerView() {
        mLvDisplay.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setAutoMeasureEnabled(true);
        layoutManager.setSmoothScrollbarEnabled(true);
        mLvDisplay.setLayoutManager(layoutManager);
    }

    private void getSign() {
        mTs = Util.getTs();
        Bundle bundle = new Bundle();
        Intent intent = new Intent(mContext, SignService.class);
        intent.putExtra("method", "http://api.live.bilibili.com/AppRoom/medalRankList?");
        bundle.putString("_device", "android");
        bundle.putString("_hwid", "9edc79b18c3cf6f9");
        bundle.putString("access_key", "bda109fc53f39041fab6cbe2bd043ec1");
        bundle.putString("appkey", "1d8b6e7d45233436");
        bundle.putString("build", "508000");
        bundle.putString("mobi_app", "android");
        bundle.putString("platform", "android");
        bundle.putString("room_id", mId + "");
        bundle.putString("src", "bili");
        bundle.putString("trace_id", mTs[1]);
        bundle.putString("ts", mTs[0]);
        bundle.putString("version", "5.8.0.508000");
        intent.putExtra("param", bundle);
        mContext.startService(intent);
    }

    public void getLiveRankFavorite(String sign) {
        Call<JSONObject> call = HttpManager.getInstance().getApiSevice().getUrl(sign);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.body().optInt("code", -1) == 0) {
                    JSONObject object = response.body().optJSONObject("data");
                    if (object != null) {
                        JSONArray array = object.optJSONArray("list");
                        mFavorites = Json.parseJsonArray(
                                LiveRankFavorite.class, array);
                        mAdapter = new LiveRankFavoriteAdapter(
                                mContext, mFavorites);
                        mLvDisplay.setAdapter(mAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {

            }
        });
    }
}
