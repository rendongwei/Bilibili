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
import com.don.bilibili.model.LiveRankLove;

import java.util.ArrayList;
import java.util.List;

public class LiveRankLoveAdapter extends
		RecyclerView.Adapter<ViewHolder> {

	private Context mContext;
	private List<LiveRankLove> mLoves = new ArrayList<LiveRankLove>();

	public LiveRankLoveAdapter(Context context, List<LiveRankLove> loves) {
		super();
		mContext = context;
		mLoves = loves;
	}

	@Override
	public int getItemCount() {
		return mLoves.size();
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
				R.layout.listitem_live_rank_love, parent, false);
		holder = new ItemHolder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		LiveRankLove love = mLoves.get(position);
		ItemHolder itemHolder = (ItemHolder) holder;
		int id = mContext.getResources().getIdentifier(
				"ic_rank_" + love.getRank(), "drawable",
				mContext.getPackageName());
		itemHolder.mIvRank.setImageResource(id);
		itemHolder.mTvName.setText(love.getUname());
		itemHolder.mTvCount.setText(love.getScore() + "");
	}

	class ItemHolder extends ViewHolder implements OnClickListener {

		ImageView mIvRank;
		TextView mTvName;
		TextView mTvCount;

		public ItemHolder(View view) {
			super(view);
			mIvRank = (ImageView) view
					.findViewById(R.id.listitem_live_rank_love_iv_rank);
			mTvName = (TextView) view
					.findViewById(R.id.listitem_live_rank_love_tv_name);
			mTvCount = (TextView) view
					.findViewById(R.id.listitem_live_rank_love_tv_count);
		}

		@Override
		public void onClick(View v) {

		}
	}
}
