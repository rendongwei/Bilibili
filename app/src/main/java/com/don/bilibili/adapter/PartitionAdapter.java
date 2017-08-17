package com.don.bilibili.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.don.bilibili.R;
import com.don.bilibili.image.ImageManager;
import com.don.bilibili.model.Partition;
import com.don.bilibili.model.PartitionBanner;
import com.don.bilibili.utils.DisplayUtil;
import com.don.bilibili.utils.EmptyUtil;
import com.don.bilibili.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class PartitionAdapter extends
        RecyclerView.Adapter<ViewHolder> {

    private Context mContext;
    private List<Partition> mPartitions = new ArrayList<Partition>();

    public PartitionAdapter(Context context, List<Partition> partitions) {
        super();
        mContext = context;
        mPartitions = partitions;
    }

    @Override
    public int getItemCount() {
        return mPartitions.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = null;
        ViewHolder holder = null;
        view = LayoutInflater.from(mContext).inflate(
                R.layout.listitem_partition, parent, false);
        holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemHolder itemHolder = (ItemHolder) holder;
        Partition partition = mPartitions.get(position);
        int resId = mContext.getResources().getIdentifier(
                "ic_category_t" + partition.getParam(), "drawable",
                mContext.getPackageName());
        if (resId != 0) {
            itemHolder.mIvIcon.setImageResource(resId);
        }
        itemHolder.mTvName.setText(partition.getTitle());

        PartitionBanner banner = EmptyUtil.isEmpty(partition.getBanners()) ? null
                : partition.getBanners().get(0);
        itemHolder.mLayoutBanner.setVisibility(banner == null ? View.GONE
                : View.VISIBLE);
        if (banner != null) {
            ImageManager.getInstance(mContext).showRoundImage(
                    itemHolder.mIvBanner, banner.getImage());
        }
        itemHolder.mLvDisplay.setAdapter(new PartitionItemAdapter(mContext,
                partition.getBody()));
    }

    class ItemHolder extends ViewHolder implements OnClickListener {

        LinearLayout mLayoutTop;
        ImageView mIvIcon;
        TextView mTvName;
        TextView mTvMore;
        RelativeLayout mLayoutBanner;
        ImageView mIvBanner;
        View mVBannerShadow;
        RecyclerView mLvDisplay;

        public ItemHolder(View view) {
            super(view);
            mLayoutTop = (LinearLayout) view
                    .findViewById(R.id.listitem_partition_layout_top);
            mIvIcon = (ImageView) view
                    .findViewById(R.id.listitem_partition_iv_icon);
            mTvName = (TextView) view
                    .findViewById(R.id.listitem_partition_tv_name);
            mTvMore = (TextView) view
                    .findViewById(R.id.listitem_partition_tv_more);
            mLayoutBanner = (RelativeLayout) view
                    .findViewById(R.id.listitem_partition_layout_banner);
            mIvBanner = (ImageView) view
                    .findViewById(R.id.listitem_partition_iv_banner);
            mVBannerShadow = view
                    .findViewById(R.id.listitem_partition_v_banner_shadow);
            mLvDisplay = (RecyclerView) view
                    .findViewById(R.id.listitem_partition_lv_display);

            mLayoutTop.setOnClickListener(this);
            mVBannerShadow.setOnClickListener(this);

            mLvDisplay.setNestedScrollingEnabled(false);
            GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
            layoutManager.setAutoMeasureEnabled(true);
            layoutManager.setSmoothScrollbarEnabled(true);
            mLvDisplay.setLayoutManager(layoutManager);
            mLvDisplay
                    .addItemDecoration(new DividerItemDecoration(new DividerItemDecoration.OnDividerItemDecorationListener() {
                        @Override
                        public Rect getOffsets(int position) {
                            int width = DisplayUtil.dip2px(mContext, 15);
                            int halfWidth = width / 2;
                            int left = position % 2 == 0 ? 0 : halfWidth / 2;
                            int top = halfWidth;
                            int right = position % 2 == 0 ? halfWidth / 2 : 0;
                            int bottom = 0;
                            return new Rect(left, top, right, bottom);
                        }
                    }));
        }

        @Override
        public void onClick(View v) {

        }
    }
}
