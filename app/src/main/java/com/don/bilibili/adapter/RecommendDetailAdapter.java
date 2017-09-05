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
import com.don.bilibili.model.RecommendDetail;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RecommendDetailAdapter extends
        RecyclerView.Adapter<ViewHolder> {

    private Context mContext;
    private List<RecommendDetail.Relates> mRelates = new ArrayList<RecommendDetail.Relates>();

    public RecommendDetailAdapter(Context context, List<RecommendDetail.Relates> relates) {
        super();
        mContext = context;
        mRelates = relates;
    }

    @Override
    public int getItemCount() {
        return mRelates.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = View.inflate(mContext, R.layout.listitem_recommend_detail,
                null);
        ViewHolder holder = new ItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RecommendDetail.Relates relates = mRelates.get(position);
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        ImageManager.getInstance(mContext).showRoundImage(
                itemViewHolder.mIvCover, relates.getPic());
        itemViewHolder.mTvTitle.setText(relates.getTitle());
        itemViewHolder.mTvName.setText(relates.getOwner().getName());
        String count = "";
        DecimalFormat df = new DecimalFormat("0.##");
        if (relates.getStat().getView() > 10000) {
            double d = (double) relates.getStat().getView() / 10000d;
            count = df.format(d) + "万";
        } else {
            count = relates.getStat().getView() + "";
        }
        itemViewHolder.mTvLookCount.setText(count);
        if (relates.getStat().getDanmaku() > 10000) {
            double d = (double) relates.getStat().getDanmaku() / 10000d;
            count = df.format(d) + "万";
        } else {
            count = relates.getStat().getDanmaku() + "";
        }
        itemViewHolder.mTvCommentCount.setText(count);
    }

    class ItemViewHolder extends ViewHolder implements OnClickListener {

        ImageView mIvCover;
        TextView mTvTitle;
        TextView mTvName;
        TextView mTvLookCount;
        TextView mTvCommentCount;
        ImageView mIvMore;
        View mVShadow;

        public ItemViewHolder(View view) {
            super(view);
            mIvCover = (ImageView) view
                    .findViewById(R.id.listitem_recommend_detail_iv_cover);
            mTvTitle = (TextView) view
                    .findViewById(R.id.listitem_recommend_detail_tv_title);
            mTvName = (TextView) view
                    .findViewById(R.id.listitem_recommend_detail_tv_name);
            mTvLookCount = (TextView) view
                    .findViewById(R.id.listitem_recommend_detail_tv_look_count);
            mTvCommentCount = (TextView) view
                    .findViewById(R.id.listitem_recommend_detail_tv_comment_count);
            mIvMore = (ImageView) view
                    .findViewById(R.id.listitem_recommend_detail_iv_more);
            mVShadow = view
                    .findViewById(R.id.listitem_recommend_detail_v_shadow);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            RecommendDetail.Relates relates = mRelates.get(getAdapterPosition());
            Intent intent = new Intent(mContext, RecommendActivity.class);
            intent.putExtra("aid", relates.getAid());
            intent.putExtra("cover", relates.getPic());
            mContext.startActivity(intent);
        }
    }
}
