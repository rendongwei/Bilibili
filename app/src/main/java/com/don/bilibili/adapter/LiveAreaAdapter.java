package com.don.bilibili.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.don.bilibili.Model.HomeLiveCategoryLive;
import com.don.bilibili.R;
import com.don.bilibili.image.ImageManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class LiveAreaAdapter extends
		RecyclerView.Adapter<ViewHolder> {

	private Context mContext;
	private List<HomeLiveCategoryLive> mLives = new ArrayList<HomeLiveCategoryLive>();

	public LiveAreaAdapter(Context context, List<HomeLiveCategoryLive> lives) {
		super();
		mContext = context;
		mLives = lives;
	}

	@Override
	public int getItemCount() {
		return mLives.size();
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
		View view = null;
		ViewHolder holder = null;
		view = View.inflate(mContext, R.layout.listitem_live_area, null);
		holder = new ItemViewHolder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		HomeLiveCategoryLive live = mLives.get(position);
		ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
		ImageManager.getInstance(mContext).showRoundSquareImage(
				itemViewHolder.mIvCover, live.getCover().getSrc());
		itemViewHolder.mTvTitle.setText(live.getTitle());
		itemViewHolder.mTvName.setText(live.getOwner().getName());
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

	class ItemViewHolder extends ViewHolder implements OnClickListener {

		ImageView mIvCover;
		TextView mTvTitle;
		TextView mTvName;
		LinearLayout mLayoutCount;
		TextView mTvCount;
		View mVShadow;

		public ItemViewHolder(View view) {
			super(view);
			mIvCover = (ImageView) view
					.findViewById(R.id.listitem_live_area_iv_cover);
			mTvTitle = (TextView) view
					.findViewById(R.id.listitem_live_area_tv_title);
			mTvName = (TextView) view
					.findViewById(R.id.listitem_live_area_tv_name);
			mLayoutCount = (LinearLayout) view
					.findViewById(R.id.listitem_live_area_layout_count);
			mTvCount = (TextView) view
					.findViewById(R.id.listitem_live_area_tv_count);
			mVShadow = view.findViewById(R.id.listitem_live_area_v_shadow);
			view.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
//			HomeLiveCategoryLive live = mLives.get(getAdapterPosition());
//			Intent intent = new Intent(mContext, LiveActivity.class);
//			intent.putExtra("live", live);
//			mContext.startActivity(intent);
		}
	}
}
