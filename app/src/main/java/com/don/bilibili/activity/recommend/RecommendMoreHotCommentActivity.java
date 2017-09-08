package com.don.bilibili.activity.recommend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.don.bilibili.R;
import com.don.bilibili.activity.base.TranslucentStatusBarActivity;
import com.don.bilibili.adapter.RecommendMoreHotCommentAdapter;
import com.don.bilibili.annotation.Id;
import com.don.bilibili.annotation.OnClick;
import com.don.bilibili.http.HttpManager;
import com.don.bilibili.model.RecommendComment;
import com.don.bilibili.model.RecommendCommentItem;
import com.don.bilibili.service.SignService;
import com.don.bilibili.utils.Util;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecommendMoreHotCommentActivity extends TranslucentStatusBarActivity implements View.OnClickListener {

    @Id(id = R.id.recommend_more_hot_comment_layout_back)
    @OnClick
    private LinearLayout mLayoutBack;
    @Id(id = R.id.recommend_more_hot_comment_tv_title)
    private TextView mTvTitle;
    @Id(id = R.id.recommend_more_hot_comment_layout_refresh)
    private SwipeRefreshLayout mLayoutRefresh;
    @Id(id = R.id.recommend_more_hot_comment_lv_display)
    private RecyclerView mLvDisplay;

    private String mAid;
    private String[] mTs;
    private int mPage = 1;
    private boolean mIsLoading = false;
    private List<RecommendCommentItem> mComments = new ArrayList<>();
    private RecommendMoreHotCommentAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_recommend_more_hot_comment;
    }

    @Override
    protected void bindListener() {
        mLayoutRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                getSign();
            }
        });
        mLvDisplay.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(1)) {
                    getSign();
                }
            }
        });
        setOnGetSignCallBack(new OnGetSignCallBack() {
            @Override
            public void callback(String method, String sign) {
                getComment(sign);
            }
        });
    }

    @Override
    protected void init() {
        mAid = getIntent().getStringExtra("aid");
        mTvTitle.setText(mAid);
        Util.initRefresh(mLayoutRefresh);
        initRecyclerView();
        mLayoutRefresh.setRefreshing(true);
        getSign();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recommend_more_hot_comment_layout_back:
                finish();
                break;
        }
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
        if (mIsLoading) {
            return;
        }
        mIsLoading = true;
        mTs = Util.getTs();
        Bundle bundle = new Bundle();
        Intent intent = new Intent(mContext, SignService.class);
        intent.putExtra("method", "https://api.bilibili.com/x/v2/reply?");
        bundle.putString("appkey", "1d8b6e7d45233436");
        bundle.putString("build", "508000");
        bundle.putString("mobi_app", "android");
        bundle.putString("nohot", "1");
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
        Call<JSONObject> call = HttpManager.getInstance().getApiSevice().getUrl(sign);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.body().optInt("code", -1) == 0) {
                    RecommendComment comment = new RecommendComment();
                    comment.parse(response.body().optJSONObject("data"));
                    if (mPage == 1) {
                        for (RecommendComment.Comment c : comment.getUpper().getTop()) {
                            RecommendCommentItem item = new RecommendCommentItem(false, true, c);
                            mComments.add(item);
                        }
                        for (RecommendComment.Comment c : comment.getHots()) {
                            RecommendCommentItem item = new RecommendCommentItem(true, false, c);
                            mComments.add(item);
                        }
                    }
                    for (RecommendComment.Comment c : comment.getReplies()) {
                        RecommendCommentItem item = new RecommendCommentItem(false, false, c);
                        mComments.add(item);
                    }
                    if (mPage == 1) {
                        mAdapter = new RecommendMoreHotCommentAdapter(mContext, mComments, mAid);
                        mLvDisplay.setAdapter(mAdapter);
                    } else {
                        int itemCount = comment.getReplies().size();
                        int positionStart = mComments.size();
                        mAdapter.notifyItemRangeInserted(positionStart, itemCount);
                    }
                    mPage++;
                }
                mLayoutRefresh.setRefreshing(false);
                mIsLoading = false;
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                mLayoutRefresh.setRefreshing(false);
                mIsLoading = false;
            }
        });
    }
}
