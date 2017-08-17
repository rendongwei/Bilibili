package com.don.bilibili.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.don.bilibili.R;
import com.don.bilibili.image.ImageManager;
import com.don.bilibili.model.HomeEpisode;
import com.don.bilibili.model.HomeEpisodeFoot;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class HomeEpisodeAdapter extends
		RecyclerView.Adapter<ViewHolder> {

	private Context mContext;
	private List<HomeEpisode> mEpisodes = new ArrayList<HomeEpisode>();
	private List<HomeEpisodeFoot> mEpisodeFoots = new ArrayList<HomeEpisodeFoot>();

	public HomeEpisodeAdapter(Context context, List<HomeEpisode> episodes,
			List<HomeEpisodeFoot> foots) {
		super();
		mContext = context;
		mEpisodes = episodes;
		mEpisodeFoots = foots;
	}

	@Override
	public int getItemCount() {
		return mEpisodes.size() + mEpisodeFoots.size();
	}

	@Override
	public int getItemViewType(int position) {
		return position < 3 ? 0 : 1;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
		View view = null;
		ViewHolder holder = null;
		switch (type) {
		case 0:
			view = View.inflate(mContext, R.layout.listitem_home_episode, null);
			holder = new ItemViewHolder(view);
			break;

		case 1:
			view = View.inflate(mContext, R.layout.listitem_home_episode_foot,
					null);
			holder = new FootViewHolder(view);
			break;
		}
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		if (holder instanceof ItemViewHolder) {
			ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
			HomeEpisode episode = mEpisodes.get(position);
			ImageManager.getInstance(mContext).showRoundImage(
					itemViewHolder.mIvCover, episode.getCover());
			String count = "";
			DecimalFormat df = new DecimalFormat("0.##");
			if (episode.getFavourites() > 10000) {
				double d = (double) episode.getFavourites() / 10000d;
				count = df.format(d) + "万人追番";
			} else {
				count = episode.getFavourites() + "人追番";
			}
			itemViewHolder.mTvCount.setText(count);
			itemViewHolder.mTvName.setText(episode.getTitle());
			boolean isNumber = false;
			try {
				Integer.parseInt(episode.getNewestEpIndex());
				isNumber = true;
			} catch (Exception e) {
				isNumber = false;
			}
			itemViewHolder.mTvContent.setText("更新至"
					+ episode.getNewestEpIndex() + (isNumber ? "话" : ""));
		}

		if (holder instanceof FootViewHolder) {
			FootViewHolder footViewHolder = (FootViewHolder) holder;
			HomeEpisodeFoot foot = mEpisodeFoots.get(position
					- mEpisodes.size());
			ImageManager.getInstance(mContext).showImage(
					footViewHolder.mIvCover, foot.getCover());
			footViewHolder.mLayoutNew.setVisibility(foot.isNew() ? View.VISIBLE
					: View.GONE);
			footViewHolder.mTvName.setText(foot.getTitle());
			footViewHolder.mTvContent.setText(foot.getDesc());
		}
	}

	class ItemViewHolder extends ViewHolder implements OnClickListener {

		RelativeLayout mLayoutCover;
		ImageView mIvCover;
		TextView mTvCount;
		TextView mTvName;
		TextView mTvContent;

		public ItemViewHolder(View view) {
			super(view);
			mLayoutCover = (RelativeLayout) view
					.findViewById(R.id.listitem_home_episode_layout_cover);
			mIvCover = (ImageView) view
					.findViewById(R.id.listitem_home_episode_iv_cover);
			mTvCount = (TextView) view
					.findViewById(R.id.listitem_home_episode_tv_count);
			mTvName = (TextView) view
					.findViewById(R.id.listitem_home_episode_tv_name);
			mTvContent = (TextView) view
					.findViewById(R.id.listitem_home_episode_tv_content);
			view.setOnClickListener(this);
			mLayoutCover.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {

		}
	}

	class FootViewHolder extends ViewHolder implements OnClickListener {

		ImageView mIvCover;
		LinearLayout mLayoutNew;
		TextView mTvName;
		TextView mTvContent;

		public FootViewHolder(View view) {
			super(view);
			mIvCover = (ImageView) view
					.findViewById(R.id.listitem_home_episode_foot_iv_cover);
			mLayoutNew = (LinearLayout) view
					.findViewById(R.id.listitem_home_episode_foot_layout_new);
			mTvName = (TextView) view
					.findViewById(R.id.listitem_home_episode_foot_tv_name);
			mTvContent = (TextView) view
					.findViewById(R.id.listitem_home_episode_foot_tv_content);
			view.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {

		}
	}
}
