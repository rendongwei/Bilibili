package com.don.bilibili.fragment.recommend;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.don.bilibili.R;
import com.don.bilibili.annotation.Id;
import com.don.bilibili.annotation.OnClick;
import com.don.bilibili.fragment.base.BindFragment;
import com.don.bilibili.http.HttpManager;
import com.don.bilibili.model.HomeRecommend;
import com.don.bilibili.model.RecommendDetail;
import com.don.bilibili.service.SignService;
import com.don.bilibili.utils.Util;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SynopsisFragment extends BindFragment {

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

    private HomeRecommend mRecommend;
    private String[] mTs;
    private RecommendDetail mRecommendDetail;

    public SynopsisFragment(HomeRecommend recommend) {
        mRecommend = recommend;
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
        getSign();
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
                        mTvPlayCount.setText(mRecommendDetail.getStat().getView() + "");
                        mTvDanmakuCount.setText(mRecommendDetail.getStat().getDanmaku() + "");
                        mTvContent.setText(mRecommendDetail.getDesc());
                        interceptHyperLink(mTvContent);
                        removeHyperLinkUnderline(mTvContent);
                        mTvShare.setText(mRecommendDetail.getStat().getShare() + "");
                        mTvCoin.setText(mRecommendDetail.getStat().getCoin() + "");
                        mTvCollect.setText(mRecommendDetail.getStat().getLike() + "");
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {

            }
        });
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
