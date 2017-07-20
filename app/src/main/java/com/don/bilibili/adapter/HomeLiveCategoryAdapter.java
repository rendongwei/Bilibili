package com.don.bilibili.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.don.bilibili.Model.HomeLiveCategory;
import com.don.bilibili.Model.HomeLiveCategoryLive;
import com.don.bilibili.Model.HomeLiveCategoryLivePartition;
import com.don.bilibili.R;
import com.don.bilibili.adapter.base.BaseAdapter;
import com.don.bilibili.image.ImageManager;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeLiveCategoryAdapter extends BaseAdapter<HomeLiveCategory> {

    private Map<String, Boolean> mIsRefreshings = new HashMap<String, Boolean>();
    private Map<String, Integer> mRefreshCounts = new HashMap<String, Integer>();

    public HomeLiveCategoryAdapter(Context context, List<HomeLiveCategory> list) {
        super(context, list);
    }

    public void setRefreshing(String area, int listPosition,
                              boolean isRefreshing, boolean isAll) {
        mIsRefreshings.put(area, isRefreshing);
        if (!isRefreshing) {
            mRefreshCounts.put(area, getRandom());
        }
        if (isAll) {
            notifyItemRangeChanged(listPosition * 6, 6);
        } else {
            notifyItemChanged((listPosition + 1) * 6 - 1);
        }
    }

    private int getRandom() {
        return (int) (30 * Math.random() + 1);
    }

    private boolean getIsRefreshing(String area) {
        boolean isRefreshing = false;
        if (mRefreshCounts.containsKey(area)) {
            isRefreshing = mIsRefreshings.get(area);
        } else {
            isRefreshing = false;
            mIsRefreshings.put(area, isRefreshing);
        }
        return isRefreshing;
    }

    private int getRefreshCount(String area) {
        int count = 0;
        if (mRefreshCounts.containsKey(area)) {
            count = mRefreshCounts.get(area);
        } else {
            count = getRandom();
            mRefreshCounts.put(area, count);
        }
        return count;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() * 6;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 6 == 0) {
            return 0;
        } else if ((position + 1) % 6 == 0) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (type) {
            case 0:
                view = LayoutInflater.from(mContext).inflate(R.layout.listitem_home_live_head, parent, false);
                holder = new HeadViewHolder(view);
                break;

            case 1:
                view = LayoutInflater.from(mContext).inflate(R.layout.listitem_home_live_foot, parent, false);
                holder = new FootViewHolder(view);
                break;

            case 2:
                view = LayoutInflater.from(mContext).inflate(R.layout.listitem_home_live, parent, false);
                holder = new ItemViewHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int p = position / 6;
        HomeLiveCategory category = getAll().get(p);
        HomeLiveCategoryLivePartition partition = category.getPartition();
        if (holder instanceof HeadViewHolder) {
            HeadViewHolder headViewHolder = (HeadViewHolder) holder;
            ImageManager.getInstance(mContext).showImage(
                    headViewHolder.mIvIcon, partition.getIcon().getSrc());
            headViewHolder.mTvName.setText(partition.getName());
            headViewHolder.mTvCount.setText(Html
                    .fromHtml("当前<font color = #FB7299>" + partition.getCount()
                            + "</font>个直播"));
        }
        if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            boolean isRefreshing = getIsRefreshing(partition.getArea());
            footViewHolder.mTvContent.setText(isRefreshing ? null
                    : getRefreshCount(partition.getArea()) + "条新动态,点击刷新!");
            if (isRefreshing) {
                footViewHolder.mIvRefresh
                        .startAnimation(footViewHolder.mAnimation);
            } else {
                footViewHolder.mIvRefresh.clearAnimation();
            }
        }

        if (holder instanceof ItemViewHolder) {
            HomeLiveCategoryLive live = category.getLives().get(
                    (position - 2 * p) % 5);
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            ImageManager.getInstance(mContext).showRoundSquareImage(
                    itemViewHolder.mIvCover, live.getCover().getSrc());
            itemViewHolder.mTvTitle.setText(live.getTitle());
            String count = "";
            DecimalFormat df = new DecimalFormat("0.##");
            if (live.getOnline() > 10000) {
                double d = (double) live.getOnline() / 10000d;
                count = df.format(d) + "万";
            } else {
                count = live.getOnline() + "";
            }
            itemViewHolder.mTvCount.setText(count);
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mIvIcon;
        TextView mTvName;
        TextView mTvCount;

        public HeadViewHolder(View view) {
            super(view);
            mIvIcon = (ImageView) view
                    .findViewById(R.id.listitem_home_live_head_iv_icon);
            mTvName = (TextView) view
                    .findViewById(R.id.listitem_home_live_head_tv_name);
            mTvCount = (TextView) view
                    .findViewById(R.id.listitem_home_live_head_tv_count);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int p = getAdapterPosition() / 6;
            HomeLiveCategory category = getAll().get(p);
            HomeLiveCategoryLivePartition partition = category.getPartition();
//            Intent intent = new Intent(mContext, LiveAreaActivity.class);
//            intent.putExtra("partition", partition);
//            mContext.startActivity(intent);
        }
    }

    class FootViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTvMore;
        TextView mTvContent;
        LinearLayout mLayoutRefresh;
        ImageView mIvRefresh;

        Animation mAnimation;

        public FootViewHolder(View view) {
            super(view);
            mTvMore = (TextView) view
                    .findViewById(R.id.listitem_home_live_foot_tv_more);
            mTvContent = (TextView) view
                    .findViewById(R.id.listitem_home_live_foot_tv_content);
            mLayoutRefresh = (LinearLayout) view
                    .findViewById(R.id.listitem_home_live_foot_layout_refresh);
            mIvRefresh = (ImageView) view
                    .findViewById(R.id.listitem_home_live_foot_iv_refresh);

            mTvMore.setOnClickListener(this);
            mTvContent.setOnClickListener(this);
            mLayoutRefresh.setOnClickListener(this);

            mAnimation = AnimationUtils.loadAnimation(mContext, R.anim.rotate);
        }

        @Override
        public void onClick(View v) {
            int p = getAdapterPosition() / 6;
            HomeLiveCategory category = getAll().get(p);
            HomeLiveCategoryLivePartition partition = category.getPartition();
            switch (v.getId()) {
                case R.id.listitem_home_live_foot_tv_more:
//                    Intent intent = new Intent(mContext, LiveAreaActivity.class);
//                    intent.putExtra("partition", partition);
//                    mContext.startActivity(intent);
                    break;

                case R.id.listitem_home_live_foot_tv_content:
                case R.id.listitem_home_live_foot_layout_refresh:
//                    boolean isRefreshing = getIsRefreshing(partition.getArea());
//                    if (isRefreshing) {
//                        return;
//                    }
//                    setRefreshing(partition.getArea(), p, true, false);
//                    mFragment.getCategoryRefresh(partition.getArea(), p);
                    break;
            }
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mIvCover;
        TextView mTvTitle;
        TextView mTvName;
        TextView mTvCount;
        View mVShadow;

        public ItemViewHolder(View view) {
            super(view);
            mIvCover = (ImageView) view
                    .findViewById(R.id.listitem_home_live_iv_cover);
            mTvTitle = (TextView) view
                    .findViewById(R.id.listitem_home_live_tv_title);
            mTvName = (TextView) view
                    .findViewById(R.id.listitem_home_live_tv_name);
            mTvCount = (TextView) view
                    .findViewById(R.id.listitem_home_live_tv_count);
            mVShadow = view.findViewById(R.id.listitem_home_live_v_shadow);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int p = getAdapterPosition() / 6;
            HomeLiveCategory category = getAll().get(p);
            HomeLiveCategoryLive live = category.getLives().get(
                    (getAdapterPosition() - 2 * p) % 5);
//            Intent intent = new Intent(mContext, LiveActivity.class);
//            intent.putExtra("live", live);
//            mContext.startActivity(intent);
        }
    }
}
