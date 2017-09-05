package com.don.bilibili.fragment.recommend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.don.bilibili.R;
import com.don.bilibili.annotation.Id;
import com.don.bilibili.fragment.base.BindFragment;
import com.don.bilibili.http.HttpManager;
import com.don.bilibili.service.SignService;
import com.don.bilibili.utils.Util;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentFragment extends BindFragment {

    @Id(id = R.id.recommend_comment_lv_display)
    private RecyclerView mLvDisplay;

    private String mAid;
    private String[] mTs;
    private int mPage = 1;
    private boolean mIsLoading = false;

    public static CommentFragment newInstance(String aid) {
        CommentFragment fragment = new CommentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("aid", aid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_recommend_comment;
    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void init() {
        mAid = getArguments().getString("aid");
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
        intent.putExtra("method", "https://api.bilibili.com/x/v2/reply?");
        bundle.putString("appkey", "1d8b6e7d45233436");
        bundle.putString("build", "508000");
        bundle.putString("mobi_app", "android");
        if (mPage != 1) {
            bundle.putString("nohot", "1");
        }
        bundle.putString("oid", mAid);
        bundle.putString("plat", "2");
        bundle.putString("platform", "android");
        bundle.putString("pn", mPage + "");
        bundle.putString("ps", "20");
        bundle.putString("sort", "0");
        bundle.putString("ts", mTs[0]);
        bundle.putString("type", "1");
        intent.putExtra("param", bundle);
        mContext.startService(intent);
    }

    public void getComment(String sign) {
        if (mLvDisplay == null) {
            return;
        }
        Call<JSONObject> call = HttpManager.getInstance().getApiSevice().getUrl(sign);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.body().optInt("code", -1) == 0) {

                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {

            }
        });
    }
}
