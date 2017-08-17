package com.don.bilibili.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.don.bilibili.R;
import com.don.bilibili.image.ImageManager;
import com.don.bilibili.model.PartitionItem;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PartitionItemAdapter extends
		RecyclerView.Adapter<ViewHolder> {

	private Context mContext;
	private List<PartitionItem> mItems = new ArrayList<PartitionItem>();

	public PartitionItemAdapter(Context context, List<PartitionItem> items) {
		super();
		mContext = context;
		mItems = items;
	}

	@Override
	public int getItemCount() {
		return mItems.size();
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
				R.layout.listitem_partition_item, parent, false);
		holder = new ItemHolder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		ItemHolder itemHolder = (ItemHolder) holder;
		PartitionItem item = mItems.get(position);

		ImageManager.getInstance(mContext).showRoundSquareImage(
				itemHolder.mIvCover, item.getCover());
		itemHolder.mTvTitle.setText(item.getTitle());
		String count = "";
		DecimalFormat df = new DecimalFormat("0.##");
		if (item.getPlay() > 10000) {
			double d = (double) item.getPlay() / 10000d;
			count = df.format(d) + "万";
		} else {
			count = item.getPlay() + "";
		}
		itemHolder.mTvViewCount.setText(count);

		count = "";
		if (item.getDanmaku() > 10000) {
			double d = (double) item.getDanmaku() / 10000d;
			count = df.format(d) + "万";
		} else {
			count = item.getDanmaku() + "";
		}
		itemHolder.mTvFollowCount.setText(count);
	}

	class ItemHolder extends ViewHolder implements OnClickListener {

		ImageView mIvCover;
		TextView mTvTitle;
		TextView mTvViewCount;
		TextView mTvFollowCount;
		View mVShadow;

		public ItemHolder(View view) {
			super(view);
			mIvCover = (ImageView) view
					.findViewById(R.id.listitem_partition_item_iv_cover);
			mTvTitle = (TextView) view
					.findViewById(R.id.listitem_partition_item_tv_title);
			mTvViewCount = (TextView) view
					.findViewById(R.id.listitem_partition_item_tv_view_count);
			mTvFollowCount = (TextView) view
					.findViewById(R.id.listitem_partition_item_tv_follow_count);
			mVShadow = view.findViewById(R.id.listitem_home_live_v_shadow);
			view.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {

		}
	}
}
