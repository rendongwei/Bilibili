package com.don.bilibili.fragment.recommend;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.don.bilibili.R;
import com.don.bilibili.adapter.RecommendCommentAdapter;
import com.don.bilibili.annotation.Id;
import com.don.bilibili.fragment.base.BindFragment;
import com.don.bilibili.http.HttpManager;
import com.don.bilibili.model.RecommendComment;
import com.don.bilibili.model.RecommendCommentItem;
import com.don.bilibili.service.SignService;
import com.don.bilibili.utils.DisplayUtil;
import com.don.bilibili.utils.Util;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentFragment extends BindFragment {

    @Id(id = R.id.recommend_comment_layout_container)
    private LinearLayout mLayoutContainer;
    @Id(id = R.id.recommend_comment_layout_scroll)
    private NestedScrollView mLayoutScroll;
    @Id(id = R.id.recommend_comment_lv_display)
    private RecyclerView mLvDisplay;

    private int mMaxHeight;

    private String mAid;
    private String[] mTs;
    private int mPage = 1;
    private boolean mIsLoading = false;
    private List<RecommendCommentItem> mComments = new ArrayList<>();
    private int mHotCount;
    private int mTitleCount;
    private RecommendCommentAdapter mAdapter;

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
        mLayoutContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mMaxHeight = mLayoutContainer.getMeasuredHeight();
            }
        });
        mLayoutScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX,
                                       int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v
                        .getMeasuredHeight())) {
                    getSign();
                }
            }
        });
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

    public void appLayoutOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (mLayoutScroll != null) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mLayoutScroll.getLayoutParams();
            params.height = mMaxHeight - DisplayUtil.dip2px(mContext, 40) - (appBarLayout.getTotalScrollRange() - Math.abs(verticalOffset));
            mLayoutScroll.setLayoutParams(params);
        }
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
                    RecommendComment comment = new RecommendComment();
                    comment.parse(response.body().optJSONObject("data"));
                    if (mPage == 1) {
                        mTitleCount = comment.getHots().size() + comment.getUpper().getTop().size();
                        mHotCount = comment.getHots().size();
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
                        mAdapter = new RecommendCommentAdapter(mContext, mComments, mHotCount, mTitleCount);
                        mLvDisplay.setAdapter(mAdapter);
                    } else {
                        int itemCount = comment.getReplies().size();
                        int positionStart = mComments.size();
                        mAdapter.notifyItemRangeInserted(positionStart, itemCount);
                    }
                    mPage++;
                }
                mIsLoading = false;
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                mIsLoading = false;
            }
        });
    }
}
