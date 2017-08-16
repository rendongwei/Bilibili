package com.don.bilibili.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.don.bilibili.R;
import com.don.bilibili.model.LiveRankFavorite;
import com.don.bilibili.utils.Util;
import com.don.bilibili.view.VerticalCenterImageSpan;

import java.util.ArrayList;
import java.util.List;

public class LiveRankFavoriteAdapter extends
		RecyclerView.Adapter<ViewHolder> {

	private Context mContext;
	private List<LiveRankFavorite> mFavorites = new ArrayList<LiveRankFavorite>();

	public LiveRankFavoriteAdapter(Context context,
			List<LiveRankFavorite> favorites) {
		super();
		mContext = context;
		mFavorites = favorites;
	}

	@Override
	public int getItemCount() {
		return mFavorites.size();
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
				R.layout.listitem_live_rank_favorite, parent, false);
		holder = new ItemHolder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		LiveRankFavorite favorite = mFavorites.get(position);
		ItemHolder itemHolder = (ItemHolder) holder;
		int id = mContext.getResources().getIdentifier(
				"ic_rank_" + favorite.getRank(), "drawable",
				mContext.getPackageName());
		itemHolder.mIvRank.setImageResource(id);

		String medal = favorite.getMedalName() + " " + favorite.getLevel();
		String content = medal + "        " + favorite.getUname();
		SpannableString spannableString = new SpannableString(content);
		spannableString.setSpan(
				new VerticalCenterImageSpan(mContext, Util.createDanmakuMedal(
						mContext, favorite.getMedalName(), favorite.getLevel(),
						Util.parseColor(favorite.getColor()))), 0, medal
						.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		itemHolder.mTvName.setText(spannableString);
	}

	class ItemHolder extends ViewHolder implements OnClickListener {

		ImageView mIvRank;
		TextView mTvName;

		public ItemHolder(View view) {
			super(view);
			mIvRank = (ImageView) view
					.findViewById(R.id.listitem_live_rank_favorite_iv_rank);
			mTvName = (TextView) view
					.findViewById(R.id.listitem_live_rank_favorite_tv_name);
		}

		@Override
		public void onClick(View v) {

		}
	}
}
