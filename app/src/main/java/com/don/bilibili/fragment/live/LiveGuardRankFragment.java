package com.don.bilibili.fragment.live;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;

import com.don.bilibili.Json.Json;
import com.don.bilibili.R;
import com.don.bilibili.adapter.LiveGuardRankAdapter;
import com.don.bilibili.annotation.Id;
import com.don.bilibili.fragment.base.BindFragment;
import com.don.bilibili.http.HttpManager;
import com.don.bilibili.model.LiveGuardRank;
import com.don.bilibili.service.SignService;
import com.don.bilibili.utils.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveGuardRankFragment extends BindFragment implements
        OnClickListener {

    @Id(id = R.id.live_guard_rank_lv_display)
    private RecyclerView mLvDisplay;

    private int mId;
    private List<LiveGuardRank> mGuardRanks = new ArrayList<LiveGuardRank>();
    private LiveGuardRankAdapter mAdapter;

    private String[] mTs;

    public static LiveGuardRankFragment newInstance(int id) {
        LiveGuardRankFragment fragment = new LiveGuardRankFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_live_guard_rank;
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

    @Override
    public void onClick(View v) {

    }

    private void initRecyclerView() {
        mLvDisplay.setNestedScrollingEnabled(false);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 3);
        layoutManager.setAutoMeasureEnabled(true);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setSpanSizeLookup(new SpanSizeLookup() {

            @Override
            public int getSpanSize(int position) {
                return position < 3 ? 1 : 3;
            }
        });
        mLvDisplay.setLayoutManager(layoutManager);
    }

    private void getSign() {
        mTs = Util.getTs();
        Bundle bundle = new Bundle();
        Intent intent = new Intent(mContext, SignService.class);
        intent.putExtra("method", "http://api.live.bilibili.com/AppRoom/guardRank?");
        bundle.putString("_device", "android");
        bundle.putString("_hwid", "9edc79b18c3cf6f9");
        bundle.putString("access_key", "bda109fc53f39041fab6cbe2bd043ec1");
        bundle.putString("appkey", "1d8b6e7d45233436");
        bundle.putString("build", "508000");
        bundle.putString("mobi_app", "android");
        bundle.putString("page", "1");
        bundle.putString("page_size", "15");
        bundle.putString("platform", "android");
        bundle.putString("ruid", mId + "");
        bundle.putString("src", "bili");
        bundle.putString("trace_id", mTs[1]);
        bundle.putString("ts", mTs[0]);
        bundle.putString("version", "5.8.0.508000");
        intent.putExtra("param", bundle);
        mContext.startService(intent);
    }

    public void getLiveGuardRank(String sign) {
        Call<JSONObject> call = HttpManager.getInstance().getApiSevice().getUrl(sign);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.body().optInt("code", -1) == 0) {
                    JSONArray array = response.body().optJSONArray("data");
                    mGuardRanks = Json.parseJsonArray(
                            LiveGuardRank.class, array);
                    mAdapter = new LiveGuardRankAdapter(mContext,
                            mGuardRanks);
                    mLvDisplay.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {

            }
        });
    }
}
