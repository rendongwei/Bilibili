package com.don.bilibili.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.don.bilibili.R;
import com.don.bilibili.image.ImageManager;
import com.don.bilibili.model.LiveGuardRank;
import com.don.bilibili.view.CircularImageView;

import java.util.ArrayList;
import java.util.List;

public class LiveGuardRankAdapter extends
		RecyclerView.Adapter<ViewHolder> {

	private Context mContext;
	private List<LiveGuardRank> mRanks = new ArrayList<LiveGuardRank>();

	public LiveGuardRankAdapter(Context context, List<LiveGuardRank> ranks) {
		super();
		mContext = context;
		mRanks = ranks;
	}

	@Override
	public int getItemCount() {
		return mRanks.size();
	}

	@Override
	public int getItemViewType(int position) {
		if (position < 3) {
			return 0;
		}
		return 1;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
		View view = null;
		ViewHolder holder = null;
		if (type == 0) {
			view = View.inflate(mContext,
					R.layout.listitem_live_guard_rank_top, null);
			holder = new TopHolder(view);
		}
		if (type == 1) {
			view = View.inflate(mContext, R.layout.listitem_live_guard_rank,
					null);
			holder = new ItemHolder(view);
		}
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		LiveGuardRank rank = mRanks.get(position);
		if (holder instanceof TopHolder) {
			TopHolder topHolder = (TopHolder) holder;
			ImageManager.getInstance(mContext).showHead(topHolder.mIvHead,
					rank.getFace());
			switch (rank.getLevel()) {
			case 1:
				topHolder.mIvLevel
						.setImageResource(R.drawable.ic_guard_gold_border);
				break;

			case 2:
				topHolder.mIvLevel
						.setImageResource(R.drawable.ic_guard_silver_border);
				break;

			case 3:
				topHolder.mIvLevel
						.setImageResource(R.drawable.ic_guard_cuprum_border);
				break;

			default:
				topHolder.mIvLevel
						.setImageResource(R.drawable.ic_guard_cuprum_border);
				break;
			}
			topHolder.mTvName.setText(rank.getUserName());
		}
		if (holder instanceof ItemHolder) {
			ItemHolder itemHolder = (ItemHolder) holder;
			switch (rank.getLevel()) {
			case 1:
				itemHolder.mIvLevel
						.setImageResource(R.drawable.ic_live_guard_governor);
				break;

			case 2:
				itemHolder.mIvLevel
						.setImageResource(R.drawable.ic_live_guard_commander);
				break;

			case 3:
				itemHolder.mIvLevel
						.setImageResource(R.drawable.ic_live_guard_captain);
				break;

			default:
				itemHolder.mIvLevel
						.setImageResource(R.drawable.ic_live_guard_captain);
				break;
			}
			itemHolder.mTvName.setText(rank.getUserName());
		}
	}

	class TopHolder extends ViewHolder implements OnClickListener {

		CircularImageView mIvHead;
		ImageView mIvLevel;
		TextView mTvName;

		public TopHolder(View view) {
			super(view);
			mIvHead = (CircularImageView) view
					.findViewById(R.id.listitem_live_guard_rank_top_head);
			mIvLevel = (ImageView) view
					.findViewById(R.id.listitem_live_guard_rank_top_level);
			mTvName = (TextView) view
					.findViewById(R.id.listitem_live_guard_rank_top_name);
		}

		@Override
		public void onClick(View v) {

		}
	}

	class ItemHolder extends ViewHolder implements OnClickListener {

		ImageView mIvLevel;
		TextView mTvName;

		public ItemHolder(View view) {
			super(view);
			mIvLevel = (ImageView) view
					.findViewById(R.id.listitem_live_guard_rank_iv_level);
			mTvName = (TextView) view
					.findViewById(R.id.listitem_live_guard_rank_tv_name);
		}

		@Override
		public void onClick(View v) {

		}
	}

}
