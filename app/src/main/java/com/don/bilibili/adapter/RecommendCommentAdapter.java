package com.don.bilibili.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import com.don.bilibili.view.CircularImageView;

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
//        return mHotCount > 0 ? mComments.size() + 1 : mComments.size();
        return mComments.size();
    }

    @Override
    public int getItemViewType(int position) {
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
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecommendCommentItem commentItem = mComments.get(position);
        RecommendComment.Comment comment = commentItem.getComment();
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            ImageManager.getInstance(mContext).showHead(itemViewHolder.mIvHead, comment.getMember().getAvatar());
            itemViewHolder.mTvName.setText(comment.getMember().getUname());
            itemViewHolder.mTvFloor.setText("#" + comment.getFloor());
            itemViewHolder.mTvTime.setText(TimeUtil.getDescriptionTimeFromTimestamp(comment.getCtime() * 1000));
            itemViewHolder.mTvContent.setText(comment.getContent().getMessage());
            itemViewHolder.mTvComment.setText(comment.getRcount() == 0 ? "" : comment.getRcount() + "");
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
        }
    }
}
