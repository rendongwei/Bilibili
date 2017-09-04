package com.don.bilibili.fragment.recommend;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.don.bilibili.R;
import com.don.bilibili.annotation.Id;
import com.don.bilibili.annotation.OnClick;
import com.don.bilibili.fragment.base.BindFragment;
import com.don.bilibili.http.HttpManager;
import com.don.bilibili.image.ImageManager;
import com.don.bilibili.model.HomeRecommend;
import com.don.bilibili.model.RecommendDetail;
import com.don.bilibili.service.SignService;
import com.don.bilibili.utils.DisplayUtil;
import com.don.bilibili.utils.TimeUtil;
import com.don.bilibili.utils.Util;
import com.don.bilibili.view.CircularImageView;

import org.json.JSONObject;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SynopsisFragment extends BindFragment implements View.OnClickListener {

    @Id(id = R.id.recommend_synopsis_tv_title)
    private TextView mTvTitle;
    @Id(id = R.id.recommend_synopsis_tv_play_count)
    private TextView mTvPlayCount;
    @Id(id = R.id.recommend_synopsis_tv_danmaku_count)
    private TextView mTvDanmakuCount;
    @Id(id = R.id.recommend_synopsis_tv_content)
    private TextView mTvContent;
    @Id(id = R.id.recommend_layout_share)
    @OnClick
    private LinearLayout mLayoutShare;
    @Id(id = R.id.recommend_tv_share)
    private TextView mTvShare;
    @Id(id = R.id.recommend_layout_coin)
    @OnClick
    private LinearLayout mLayoutCoin;
    @Id(id = R.id.recommend_tv_coin)
    private TextView mTvCoin;
    @Id(id = R.id.recommend_layout_collect)
    @OnClick
    private LinearLayout mLayoutCollect;
    @Id(id = R.id.recommend_tv_collect)
    private TextView mTvCollect;
    @Id(id = R.id.recommend_layout_download)
    @OnClick
    private LinearLayout mLayoutDownload;
    @Id(id = R.id.recommend_tv_download)
    private TextView mTvDownload;
    @Id(id = R.id.recommend_iv_head)
    private CircularImageView mIvHead;
    @Id(id = R.id.recommend_tv_name)
    private TextView mTvName;
    @Id(id = R.id.recommend_tv_date)
    private TextView mTvDate;
    @Id(id = R.id.recommend_tv_follow)
    @OnClick
    private TextView mTvFollow;
    @Id(id = R.id.recommend_tv_charge)
    @OnClick
    private TextView mTvCharge;
    @Id(id = R.id.recommend_layout_tag)
    private LinearLayout mLayoutTag;
    @Id(id = R.id.recommend_layout_tag_arrow)
    @OnClick
    private LinearLayout mLayoutTagArrow;
    @Id(id = R.id.recommend_lv_display)
    private RecyclerView mLvDisplay;

    private HomeRecommend mRecommend;
    private String[] mTs;
    private RecommendDetail mRecommendDetail;

    private AnimatorSet mAnimatorSet;
    private boolean mIsTagOpen = false;

    public static SynopsisFragment newInstance(HomeRecommend recommend) {
        SynopsisFragment fragment = new SynopsisFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("recommend", recommend);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_recommend_synopsis;
    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void init() {
        mRecommend = getArguments().getParcelable("recommend");
        getSign();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recommend_layout_tag_arrow:
                clickTagArrow();
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

    public void getView(String sign) {
        if (mTvTitle == null) {
            return;
        }
        Call<JSONObject> call = HttpManager.getInstance().getApiSevice().getUrl(sign);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.body().optInt("code", -1) == 0) {
                    JSONObject data = response.body().optJSONObject("data");
                    if (data != null) {
                        mRecommendDetail = new RecommendDetail();
                        mRecommendDetail.parse(data);
                        mTvTitle.setText(mRecommendDetail.getTitle());
                        String count = "";
                        DecimalFormat df = new DecimalFormat("0.##");
                        if (mRecommendDetail.getStat().getView() > 10000) {
                            double d = (double) mRecommendDetail.getStat().getView() / 10000d;
                            count = df.format(d) + "万";
                        } else {
                            count = mRecommendDetail.getStat().getView() + "";
                        }
                        mTvPlayCount.setText(count);
                        mTvDanmakuCount.setText(mRecommendDetail.getStat().getDanmaku() + "");
                        mTvContent.setText(mRecommendDetail.getDesc());
                        interceptHyperLink(mTvContent);
                        removeHyperLinkUnderline(mTvContent);
                        mTvShare.setText(mRecommendDetail.getStat().getShare() + "");
                        mTvCoin.setText(mRecommendDetail.getStat().getCoin() + "");
                        mTvCollect.setText(mRecommendDetail.getStat().getLike() + "");
                        ImageManager.getInstance(mContext).showHead(mIvHead, mRecommendDetail.getOwner().getFace());
                        mTvName.setText(mRecommendDetail.getOwner().getName());
                        mTvDate.setText(TimeUtil.getDescriptionTimeFromTimestamp(mRecommendDetail.getPubdate() * 1000) + "投递");
                        initTag();
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {

            }
        });
    }

    private void clickTagArrow() {
        if (mAnimatorSet != null && mAnimatorSet.isRunning()) {
            return;
        }
        mAnimatorSet = new AnimatorSet();
        int minHeight = DisplayUtil.dip2px(mContext, 30);
        int maxHeight = minHeight * mLayoutTag.getChildCount() + (mLayoutTag.getChildCount() - 1) * DisplayUtil.dip2px(mContext, 8);
        ObjectAnimator rotationAnimator = null;
        ValueAnimator valueAnimator = null;
        if (mIsTagOpen) {
            rotationAnimator = ObjectAnimator.ofFloat(mLayoutTagArrow.getChildAt(0), "rotation", 0, 180);
            valueAnimator = ValueAnimator.ofInt(maxHeight, minHeight);
        } else {
            rotationAnimator = ObjectAnimator.ofFloat(mLayoutTagArrow.getChildAt(0), "rotation", 180, 0);
            valueAnimator = ValueAnimator.ofInt(minHeight, maxHeight);
        }
        mIsTagOpen = !mIsTagOpen;
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int height = Integer.parseInt(valueAnimator.getAnimatedValue()
                        .toString());
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mLayoutTag.getLayoutParams();
                params.height = height;
                mLayoutTag.setLayoutParams(params);
            }
        });
        mAnimatorSet.setDuration(300);
        mAnimatorSet.playTogether(rotationAnimator, valueAnimator);
        mAnimatorSet.start();
    }

    private void initTag() {
        mLayoutTag.removeAllViews();
        LinearLayout rowLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams rowParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        rowLayout.setLayoutParams(rowParams);
        int margin = DisplayUtil.dip2px(mContext, 8);
        int height = DisplayUtil.dip2px(mContext, 30);
        int maxWidth = mLayoutTag.getWidth();
        for (RecommendDetail.Tag tag : mRecommendDetail.getTags()) {
            TextView textView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.include_recommend_tag, null);
            textView.setText(tag.getName());
            textView.setTag(tag);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RecommendDetail.Tag t = (RecommendDetail.Tag) view.getTag();
                    System.out.println(t.getName());
                }
            });
            textView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int newMargin = rowLayout.getChildCount() == 0 ? 0 : margin;
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            height);
            params.setMargins(newMargin, 0, 0, 0);
            if (textView.getMeasuredWidth() + newMargin > maxWidth) {
                mLayoutTag.addView(rowLayout);
                rowLayout = new LinearLayout(mContext);
                rowParams =
                        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                rowParams.setMargins(0, margin, 0, 0);
                rowLayout.setLayoutParams(rowParams);
                maxWidth = mLayoutTag.getWidth();
                newMargin = 0;
                maxWidth -= textView.getMeasuredWidth() + newMargin;
                params.setMargins(0, 0, 0, 0);
                rowLayout.addView(textView, params);
            } else {
                rowLayout.addView(textView, params);
                maxWidth -= textView.getMeasuredWidth() + newMargin;
            }
        }
        mLayoutTag.removeView(rowLayout);
        mLayoutTag.addView(rowLayout);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mLayoutTag.getLayoutParams();
        params.height = height;
        mLayoutTag.setLayoutParams(params);
        if (mLayoutTag.getChildCount() < 2) {
            mLayoutTagArrow.setVisibility(View.GONE);
        }else{
            mLayoutTagArrow.setVisibility(View.VISIBLE);
        }
    }

    private void interceptHyperLink(TextView tv) {
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        CharSequence text = tv.getText();
        if (text instanceof Spannable) {
            int end = text.length();
            Spannable spannable = (Spannable) tv.getText();
            Linkify.addLinks(spannable, Linkify.WEB_URLS);
            URLSpan[] urlSpans = spannable.getSpans(0, end, URLSpan.class);
            if (urlSpans.length == 0) {
                return;
            }
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
            for (URLSpan uri : urlSpans) {
                String url = uri.getURL();
                spannableStringBuilder.removeSpan(uri);
                CustomUrlSpan customUrlSpan = new CustomUrlSpan(mContext, url);
                ForegroundColorSpan color = new ForegroundColorSpan(Color.parseColor("#FB7299"));
                spannableStringBuilder.setSpan(customUrlSpan, spannable.getSpanStart(uri),
                        spannable.getSpanEnd(uri), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                spannableStringBuilder.setSpan(color, spannable.getSpanStart(uri),
                        spannable.getSpanEnd(uri), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            tv.setText(spannableStringBuilder);
        }
    }

    private void removeHyperLinkUnderline(TextView tv) {
        CharSequence text = tv.getText();
        if (text instanceof Spannable) {
            Spannable spannable = (Spannable) tv.getText();
            NoUnderlineSpan noUnderlineSpan = new NoUnderlineSpan();
            spannable.setSpan(noUnderlineSpan, 0, text.length(), Spanned.SPAN_MARK_MARK);
        }
    }

    private class NoUnderlineSpan extends UnderlineSpan {

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(false);
        }
    }

    private class CustomUrlSpan extends ClickableSpan {

        private Context context;
        private String url;

        public CustomUrlSpan(Context context, String url) {
            this.context = context;
            this.url = url;
        }

        @Override
        public void onClick(View widget) {
            System.out.println(url);
        }
    }
}
