package com.don.bilibili.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.don.bilibili.R;
import com.don.bilibili.image.ImageManager;
import com.don.bilibili.model.RecommendComment;
import com.don.bilibili.model.RecommendCommentItem;
import com.don.bilibili.utils.TimeUtil;
import com.don.bilibili.utils.Util;
import com.don.bilibili.view.CircularImageView;
import com.don.bilibili.view.VerticalCenterImageSpan;

import java.util.List;

public class RecommendCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<RecommendCommentItem> mComments;
    private int mHotCount;
    private int mTitleCount;

    public RecommendCommentAdapter(Context context, List<RecommendCommentItem> comments, int hotCount, int titleCount) {
        mContext = context;
        mComments = comments;
        mHotCount = hotCount;
        mTitleCount = titleCount;
    }

    @Override
    public int getItemCount() {
        return mHotCount > 0 ? mComments.size() + 1 : mComments.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mHotCount > 0 && position == mTitleCount) {
            return 1;
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        if (viewType == 0) {
            view = LayoutInflater.from(mContext).inflate(
                    R.layout.listitem_recommend_comment, parent, false);
            holder = new ItemViewHolder(view);
        }
        if (viewType == 1) {
            view = LayoutInflater.from(mContext).inflate(
                    R.layout.listitem_recommend_comment_more_hot, parent, false);
            holder = new MoreItemView(view);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecommendCommentItem commentItem = null;
        if (mHotCount > 0) {
            if (position < mTitleCount) {
                commentItem = mComments.get(position);
            }
            if (position > mTitleCount) {
                commentItem = mComments.get(position - 1);
            }
        } else {
            commentItem = mComments.get(position);
        }
        RecommendComment.Comment comment = commentItem == null ? null : commentItem.getComment();
        if (comment == null) {
            return;
        }
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            ImageManager.getInstance(mContext).showHead(itemViewHolder.mIvHead, comment.getMember().getAvatar());
            if (commentItem.isTop()) {
                SpannableString spannableString = null;
                Bitmap labelBitmap = null;
                Bitmap levelBitmap = null;
                String name = "  " + comment.getMember().getUname() + "  ";
                labelBitmap = Util.createRecommendCommentLabel(mContext, "UP");
                levelBitmap = Util.createRecommendCommentLevel(mContext, comment.getMember().getLevelInfo().getCurrentLevel());
                spannableString = new SpannableString(name);
                spannableString.setSpan(new VerticalCenterImageSpan(mContext,
                                labelBitmap), 0, 1,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new VerticalCenterImageSpan(mContext,
                                levelBitmap), name.length() - 1, name.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                itemViewHolder.mTvName.setText(spannableString);


                String content = "  " + comment.getContent().getMessage();
                labelBitmap = Util.createRecommendCommentLabel(mContext, "置顶");
                spannableString = new SpannableString(content);
                spannableString.setSpan(new VerticalCenterImageSpan(mContext,
                                labelBitmap), 0, 1,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                itemViewHolder.mTvContent.setText(spannableString);
            } else {
                SpannableString spannableString = null;
                Bitmap levelBitmap = null;
                String name = comment.getMember().getUname() + "  ";
                levelBitmap = Util.createRecommendCommentLevel(mContext, comment.getMember().getLevelInfo().getCurrentLevel());
                spannableString = new SpannableString(name);
                spannableString.setSpan(new VerticalCenterImageSpan(mContext,
                                levelBitmap), name.length() - 1, name.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                itemViewHolder.mTvName.setText(spannableString);

                itemViewHolder.mTvContent.setText(comment.getContent().getMessage());
            }
            itemViewHolder.mTvFloor.setText("#" + comment.getFloor());
            itemViewHolder.mTvTime.setText(TimeUtil.getDescriptionTimeFromTimestamp(comment.getCtime() * 1000));
            itemViewHolder.mTvComment.setText(comment.getRcount() == 0 ? "" : comment.getRcount() + "");
            itemViewHolder.mVLine.setVisibility(position == mTitleCount - 1 ? View.GONE : View.VISIBLE);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        CircularImageView mIvHead;
        TextView mTvName;
        TextView mTvFloor;
        TextView mTvTime;
        TextView mTvContent;
        TextView mTvComment;
        ImageView mIvMore;
        View mVLine;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mIvHead = (CircularImageView) itemView.findViewById(R.id.listitem_recommend_comment_iv_head);
            mTvName = (TextView) itemView.findViewById(R.id.listitem_recommend_comment_tv_name);
            mTvFloor = (TextView) itemView.findViewById(R.id.listitem_recommend_comment_tv_floor);
            mTvTime = (TextView) itemView.findViewById(R.id.listitem_recommend_comment_tv_time);
            mTvContent = (TextView) itemView.findViewById(R.id.listitem_recommend_comment_tv_content);
            mTvComment = (TextView) itemView.findViewById(R.id.listitem_recommend_comment_tv_comment);
            mIvMore = (ImageView) itemView.findViewById(R.id.listitem_recommend_comment_iv_more);
            mVLine = itemView.findViewById(R.id.listitem_recommend_comment_v_line);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    class MoreItemView extends RecyclerView.ViewHolder {

        public MoreItemView(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
