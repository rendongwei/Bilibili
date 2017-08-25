package com.don.bilibili.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.don.bilibili.R;
import com.don.bilibili.activity.recommend.RecommendActivity;
import com.don.bilibili.image.ImageManager;
import com.don.bilibili.model.HomeRecommend;
import com.don.bilibili.model.HomeRecommendAv;
import com.don.bilibili.utils.EmptyUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class HomeRecommendAdapter extends
        RecyclerView.Adapter<ViewHolder> {

    private Context mContext;
    private List<HomeRecommend> mRecommends = new ArrayList<HomeRecommend>();

    public HomeRecommendAdapter(Context context, List<HomeRecommend> recommends) {
        super();
        mContext = context;
        mRecommends = recommends;
    }

    @Override
    public int getItemCount() {
        return mRecommends.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (mRecommends.get(position).getType()) {
            case AV:
                return 0;

            default:
                break;
        }
        return 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = null;
        ViewHolder holder = null;
        switch (type) {
            case 0:
                view = View.inflate(mContext, R.layout.listitem_home_recommend_av,
                        null);
                holder = new AvViewHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HomeRecommend recommend = mRecommends.get(position);
        if (holder instanceof AvViewHolder) {
            AvViewHolder avViewHolder = (AvViewHolder) holder;
            HomeRecommendAv av = recommend.getAv();
            ImageManager.getInstance(mContext).showRoundImage(
                    avViewHolder.mIvCover, av.getCover());
            int minute = av.getDuration() / 60;
            int second = av.getDuration() % 60;
            avViewHolder.mTvTime.setText((minute > 9 ? "" : "0") + minute + ":"
                    + (second > 9 ? "" : "0") + second);
            avViewHolder.mTvTitle.setText(av.getTitle());
            String count = "";
            DecimalFormat df = new DecimalFormat("0.##");
            if (av.getPlay() > 10000) {
                double d = (double) av.getPlay() / 10000d;
                count = df.format(d) + "万";
            } else {
                count = av.getPlay() + "";
            }
            avViewHolder.mTvLookCount.setText(count);

            if (av.getReply() > 10000) {
                double d = (double) av.getReply() / 10000d;
                count = df.format(d) + "万";
            } else {
                count = av.getReply() + "";
            }
            avViewHolder.mTvCommentCount.setText(count);

            if (av.getFavorite() > 5000) {
                avViewHolder.mIvHot.setVisibility(View.VISIBLE);
                avViewHolder.mTvType.setText("热门推荐");
            } else if (av.getShare() > 500) {
                avViewHolder.mIvHot.setVisibility(View.VISIBLE);
                avViewHolder.mTvType.setText("编辑精选");
            } else {
                avViewHolder.mIvHot.setVisibility(View.GONE);

                avViewHolder.mTvType.setText(av.getTname()
                        + (EmptyUtil.isEmpty(av.getTag().getName()) ? "" : "·"
                        + av.getTag().getName()));
            }
        }
    }

    class AvViewHolder extends ViewHolder implements OnClickListener {

        ImageView mIvCover;
        TextView mTvTime;
        TextView mTvTitle;
        TextView mTvLookCount;
        TextView mTvCommentCount;
        ImageView mIvHot;
        TextView mTvType;
        ImageView mIvMore;
        View mVShadow;

        public AvViewHolder(View view) {
            super(view);
            mIvCover = (ImageView) view
                    .findViewById(R.id.listitem_home_recommend_av_iv_cover);
            mTvTime = (TextView) view
                    .findViewById(R.id.listitem_home_recommend_av_tv_time);
            mTvTitle = (TextView) view
                    .findViewById(R.id.listitem_home_recommend_av_tv_title);
            mTvLookCount = (TextView) view
                    .findViewById(R.id.listitem_home_recommend_av_tv_look_count);
            mTvCommentCount = (TextView) view
                    .findViewById(R.id.listitem_home_recommend_av_tv_comment_count);
            mIvHot = (ImageView) view
                    .findViewById(R.id.listitem_home_recommend_av_iv_hot);
            mIvMore = (ImageView) view
                    .findViewById(R.id.listitem_home_recommend_av_iv_more);
            mTvType = (TextView) view
                    .findViewById(R.id.listitem_home_recommend_av_tv_type);
            mVShadow = view
                    .findViewById(R.id.listitem_home_recommend_av_v_shadow);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            HomeRecommend recommend = mRecommends.get(getAdapterPosition());
            if (recommend.getType() == HomeRecommend.Type.AV) {
                Intent intent = new Intent(mContext, RecommendActivity.class);
                intent.putExtra("recommend", recommend);
                mContext.startActivity(intent);
            }
        }
    }
}
