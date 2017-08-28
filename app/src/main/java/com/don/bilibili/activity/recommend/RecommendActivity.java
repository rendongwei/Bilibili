package com.don.bilibili.activity.recommend;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.don.bilibili.R;
import com.don.bilibili.activity.base.TranslucentStatusBarActivity;
import com.don.bilibili.annotation.Id;
import com.don.bilibili.annotation.OnClick;
import com.don.bilibili.http.HttpManager;
import com.don.bilibili.image.ImageManager;
import com.don.bilibili.model.HomeRecommend;
import com.don.bilibili.model.RecommendDetail;
import com.don.bilibili.service.SignService;
import com.don.bilibili.utils.Util;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecommendActivity extends TranslucentStatusBarActivity implements View.OnClickListener {

    @Id(id = R.id.recommend_layout_appbar)
    private AppBarLayout mLayoutAppBar;
    @Id(id = R.id.recommend_tv_title)
    private TextView mTvTitle;
    @Id(id = R.id.recommend_layout_title_play)
    private LinearLayout mLayoutTitlePlay;
    @Id(id = R.id.recommend_iv_image)
    private ImageView mIvImage;
    @Id(id = R.id.recommend_v_ripple)
    private View mVRipple;
    @Id(id = R.id.recommend_fab_play)
    @OnClick
    private FloatingActionButton mFabPlay;

    private HomeRecommend mRecommend;
    private String[] mTs;
    private RecommendDetail mRecommendDetail;

    @Override
    protected int getContentView() {
        return R.layout.activity_recommend;
    }

    @Override
    protected void bindListener() {
        mLayoutAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    mLayoutTitlePlay.setVisibility(View.VISIBLE);
                    mTvTitle.setVisibility(View.GONE);
                } else {
                    mLayoutTitlePlay.setVisibility(View.GONE);
                    mTvTitle.setVisibility(View.VISIBLE);
                }
            }
        });
        setOnGetSignCallBack(new OnGetSignCallBack() {
            @Override
            public void callback(String method, String sign) {
                if ("https://app.bilibili.com/x/v2/view?".equals(method)) {
                    getView(sign);
                }
            }
        });
    }

    @Override
    protected void init() {
        mRecommend = getIntent().getParcelableExtra("recommend");
        mTvTitle.setText("AV" + mRecommend.getAv().getParam());
        getSign();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recommend_fab_play:

                break;
        }
    }


    private void getSign() {
        mTs = Util.getTs();
        Bundle bundle = new Bundle();
        Intent intent = new Intent(mContext, SignService.class);
        intent.putExtra("method", "https://app.bilibili.com/x/v2/view?");
        bundle.putString("aid", mRecommend.getAv().getParam() + "");
        bundle.putString("appkey", "1d8b6e7d45233436");
        bundle.putString("build", "508000");
        bundle.putString("mobi_app", "android");
        bundle.putString("from", "7");
        bundle.putString("plat", "0");
        bundle.putString("platform", "android");
        bundle.putString("ts", mTs[0]);
        intent.putExtra("param", bundle);
        mContext.startService(intent);
    }

    private void getView(String sign) {
        Call<JSONObject> call = HttpManager.getInstance().getApiSevice().getUrl(sign);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.body().optInt("code", -1) == 0) {
                    JSONObject data = response.body().optJSONObject("data");
                    if (data != null) {
                        mRecommendDetail = new RecommendDetail();
                        mRecommendDetail.parse(data);
                        ImageManager.getInstance(mContext).showImage(mIvImage, mRecommendDetail.getPic());
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {

            }
        });
    }
}
