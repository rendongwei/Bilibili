package com.don.bilibili.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.don.bilibili.R;
import com.don.bilibili.model.RecommendComment;
import com.don.bilibili.utils.TimeUtil;

import java.util.List;

public class RecommendMoreHotCommentItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<RecommendComment.Comment> mComments;

    public RecommendMoreHotCommentItemAdapter(Context context, List<RecommendComment.Comment> comments) {
        mContext = context;
        mComments = comments;
    }

    @Override
    public int getItemCount() {
        return mComments.size() > 3 ? 4 : mComments.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position > 2) {
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
                    R.layout.listitem_recommend_more_hot_comment_item, parent, false);
            holder = new ItemViewHolder(view);
        }
        if (viewType == 1) {
            view = LayoutInflater.from(mContext).inflate(
                    R.layout.listitem_recommend_more_hot_comment_item_more, parent, false);
            holder = new MoreViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecommendComment.Comment comment = mComments.get(position);
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.mTvName.setText(comment.getMember().getUname());
            itemViewHolder.mTvTime.setText(TimeUtil.getDescriptionTimeFromTimestamp(comment.getCtime() * 1000));
            itemViewHolder.mTvContent.setText(comment.getContent().getMessage());
        }

        if (holder instanceof MoreViewHolder) {
            MoreViewHolder moreViewHolder = (MoreViewHolder) holder;
            moreViewHolder.mTvContent.setText("共" + mComments.size() + "条回复");
        }
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView mTvName;
        TextView mTvTime;
        TextView mTvContent;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.listitem_recommend_comment_item_tv_name);
            mTvTime = (TextView) itemView.findViewById(R.id.listitem_recommend_comment_item_tv_time);
            mTvContent = (TextView) itemView.findViewById(R.id.listitem_recommend_comment_item_tv_content);
        }
    }

    class MoreViewHolder extends RecyclerView.ViewHolder {

        TextView mTvContent;

        public MoreViewHolder(View itemView) {
            super(itemView);
            mTvContent = (TextView) itemView.findViewById(R.id.listitem_recommend_comment_item_more_tv_content);
        }
    }
}
