package com.don.bilibili.activity.recommend;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
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
import com.don.bilibili.utils.DisplayUtil;
import com.don.bilibili.utils.Util;
import com.don.bilibili.view.DiffuseView;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecommendActivity extends TranslucentStatusBarActivity implements View.OnClickListener {

    @Id(id = R.id.recommend_layout_appbar)
    private AppBarLayout mLayoutAppBar;
    @Id(id = R.id.recommend_layout_toolbar)
    private CollapsingToolbarLayout mLayoutToolBar;
    @Id(id = R.id.recommend_iv_back)
    @OnClick
    private ImageView mIvBack;
    @Id(id = R.id.recommend_tv_title)
    private TextView mTvTitle;
    @Id(id = R.id.recommend_layout_title_play)
    @OnClick
    private LinearLayout mLayoutTitlePlay;
    @Id(id = R.id.recommend_iv_image)
    private ImageView mIvImage;
    @Id(id = R.id.recommend_v_ripple)
    private DiffuseView mVRipple;
    @Id(id = R.id.recommend_fab_play)
    @OnClick
    private FloatingActionButton mFabPlay;

    private HomeRecommend mRecommend;
    private String[] mTs;
    private RecommendDetail mRecommendDetail;

    private boolean mIsClickFab;
    private AnimatorSet mAnimatorSet;

    @Override
    protected int getContentView() {
        return R.layout.activity_recommend;
    }

    @Override
    protected void bindListener() {
        mLayoutAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange() && appBarLayout.getTotalScrollRange() != 0) {
                    mLayoutTitlePlay.setVisibility(View.VISIBLE);
                    mTvTitle.setVisibility(View.GONE);
                } else {
                    mLayoutTitlePlay.setVisibility(View.GONE);
                    mTvTitle.setVisibility(View.VISIBLE);
                }
                if (verticalOffset == 0 && mIsClickFab) {
                    clickFab();
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
        ImageManager.getInstance(mContext).showImage(mIvImage, mRecommend.getAv().getCover());
        getSign();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recommend_iv_back:
                finish();
                break;

            case R.id.recommend_fab_play:
            case R.id.recommend_layout_title_play:
                mIsClickFab = true;
                mLayoutAppBar.setExpanded(true, true);
                break;
        }
    }

    private void clickFab() {
        mIsClickFab = false;
        if (mAnimatorSet != null && mAnimatorSet.isRunning()) {
            return;
        }
        mAnimatorSet = new AnimatorSet();

        int width = mFabPlay.getWidth();
        int height = mFabPlay.getHeight();

        mVRipple.setCenterX(mVRipple.getWidth() - width / 2 - DisplayUtil.dip2px(mContext, 8));
        mVRipple.setCenterY(mVRipple.getHeight() - height / 2 - DisplayUtil.dip2px(mContext, 8));

        int translationY = height / 2 + DisplayUtil.dip2px(mContext, 8);
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(mFabPlay, "translationY", 0, -translationY);
        translationAnimator.setDuration(300);
        translationAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mFabPlay, "alpha", 1, 0);
        alphaAnimator.setDuration(300);
        alphaAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mFabPlay.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });

        ValueAnimator rippleAnimator = ValueAnimator.ofInt(width,
                (int) mVRipple.getMaxRadius());
        rippleAnimator.setDuration(300);
        rippleAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mVRipple.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        rippleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int radius = Integer.parseInt(animator.getAnimatedValue()
                        .toString());
                mVRipple.setRadius(radius);
            }
        });
        mAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mVRipple.setVisibility(View.GONE);
                AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) mLayoutToolBar.getLayoutParams();
                params.setScrollFlags(0);
                mLayoutAppBar.setExpanded(true);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        mAnimatorSet.play(translationAnimator).before(alphaAnimator).before(rippleAnimator);
        mAnimatorSet.start();
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
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {

            }
        });
    }
}
