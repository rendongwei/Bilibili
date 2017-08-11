package com.don.bilibili.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils.TruncateAt;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.don.bilibili.Model.HomeLiveCategory;
import com.don.bilibili.Model.HomeLiveCategoryBanner;
import com.don.bilibili.Model.HomeLiveCategoryLive;
import com.don.bilibili.Model.HomeLiveCategoryLivePartition;
import com.don.bilibili.R;
import com.don.bilibili.activity.live.LiveActivity;
import com.don.bilibili.activity.live.LiveAllActivity;
import com.don.bilibili.fragment.home.LiveFragment;
import com.don.bilibili.image.ImageManager;
import com.don.bilibili.utils.TextLinkUtil;
import com.don.bilibili.view.ClickableMovementMethod;

import java.text.DecimalFormat;

public class HomeLiveRecommendAdapter extends
		RecyclerView.Adapter<ViewHolder> {

	private Context mContext;
	private LiveFragment mFragment;
	private HomeLiveCategory mRecommend;
	private HomeLiveCategoryBanner mBanner;

	private boolean mIsRefreshing = false;
	private int mRefreshCount;

	public HomeLiveRecommendAdapter(Context context, LiveFragment fragment,
			HomeLiveCategory recommend, HomeLiveCategoryBanner banner) {
		super();
		mContext = context;
		mFragment = fragment;
		mRecommend = recommend;
		mBanner = banner;
		mRefreshCount = getRandom();
	}

	public void setRefreshing(boolean isRefreshing, boolean isAll) {
		mIsRefreshing = isRefreshing;
		if (!isRefreshing) {
			mRefreshCount = getRandom();
		}
		if (isAll) {
			notifyDataSetChanged();
		} else {
			notifyItemChanged(14);
		}
	}

	public void setBanner(HomeLiveCategoryBanner banner) {
		mBanner = banner;
	}

	private int getRandom() {
		return (int) (30 * Math.random() + 1);
	}

	@Override
	public int getItemCount() {
		return mRecommend.getLives().size() + 3;
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0) {
			return 0;
		} else if (position == 7) {
			return 1;
		} else if (position == 14) {
			return 3;
		} else {
			return 2;
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
		View view = null;
		ViewHolder holder = null;
		switch (type) {
		case 0:
			view = View.inflate(mContext, R.layout.listitem_home_live_head,
					null);
			holder = new HeadViewHolder(view);
			break;

		case 1:
			view = View.inflate(mContext, R.layout.listitem_home_live_banner,
					null);
			holder = new BannerViewHolder(view);
			break;
		case 2:
			view = View.inflate(mContext, R.layout.listitem_home_live, null);
			holder = new ItemViewHolder(view);
			break;

		case 3:
			view = View.inflate(mContext, R.layout.listitem_home_live_foot,
					null);
			holder = new FootViewHolder(view);
			break;
		}
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		HomeLiveCategoryLivePartition partition = mRecommend.getPartition();
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
			footViewHolder.mTvContent.setText(mIsRefreshing ? null
					: mRefreshCount + "条新动态,点击刷新!");
			if (mIsRefreshing) {
				footViewHolder.mIvRefresh
						.startAnimation(footViewHolder.mAnimation);
			} else {
				footViewHolder.mIvRefresh.clearAnimation();
			}
		}
		if (holder instanceof BannerViewHolder) {
			BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
			if (mBanner.isClip()) {
				ImageManager.getInstance(mContext).showRoundSquareImage(
						bannerViewHolder.mIvCover, mBanner.getCover().getSrc());
				bannerViewHolder.mLayoutBottom.setVisibility(View.GONE);
				bannerViewHolder.mTvTitle.setSingleLine(true);
				bannerViewHolder.mTvTitle.setEllipsize(TruncateAt.END);
				bannerViewHolder.mTvTitle.setText(mBanner.getTitle());
			} else {
				ImageManager.getInstance(mContext).showRoundSquareImage(
						bannerViewHolder.mIvCover,
						mBanner.getLive().getCover().getSrc());
				bannerViewHolder.mLayoutBottom.setVisibility(View.VISIBLE);
				bannerViewHolder.mTvTitle.setSingleLine(false);
				bannerViewHolder.mTvTitle.setText(mBanner.getLive().getTitle());
				bannerViewHolder.mTvName.setText(mBanner.getLive().getOwner()
						.getName());
				String count = "";
				DecimalFormat df = new DecimalFormat("0.##");
				if (mBanner.getLive().getOnline() > 10000) {
					double d = (double) mBanner.getLive().getOnline() / 10000d;
					count = df.format(d) + "万";
				} else {
					count = mBanner.getLive().getOnline() + "";
				}
				bannerViewHolder.mTvCount.setText(count);
			}
		}
		if (holder instanceof ItemViewHolder) {
			HomeLiveCategoryLive live = null;
			if (position < 7) {
				live = mRecommend.getLives().get(position - 1);
			} else if (position == 7) {
				live = mBanner.getLive();
			} else {
				live = mRecommend.getLives().get(position - 2);
			}
			ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
			ImageManager.getInstance(mContext).showRoundSquareImage(
					itemViewHolder.mIvCover, live.getCover().getSrc());
			String area = "#" + live.getArea() + "#";
			String title = live.getTitle();
			String content = area + " " + title;

			SpannableString sp = new SpannableString(content);
			sp.setSpan(new TextLinkUtil.IntentSpan(new OnClickListener() {

				@Override
				public void onClick(View v) {
					System.out.println("超链接");
				}
			}, false), 0, area.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			sp.setSpan(new ForegroundColorSpan(0xFFFB7299), 0, area.length(),
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			itemViewHolder.mTvTitle.setText(sp);
			itemViewHolder.mTvTitle.setMovementMethod(ClickableMovementMethod
					.getInstance());
			itemViewHolder.mTvTitle.setFocusable(false);
			itemViewHolder.mTvTitle.setClickable(false);
			itemViewHolder.mTvTitle.setLongClickable(false);
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
	}

	class HeadViewHolder extends ViewHolder implements OnClickListener {

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
			mContext.startActivity(new Intent(mContext, LiveAllActivity.class));
		}
	}

	class FootViewHolder extends ViewHolder implements OnClickListener {

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
			switch (v.getId()) {
			case R.id.listitem_home_live_foot_tv_more:
				mContext.startActivity(new Intent(mContext, LiveAllActivity.class));
				break;

			case R.id.listitem_home_live_foot_tv_content:
			case R.id.listitem_home_live_foot_layout_refresh:
				if (mIsRefreshing) {
					return;
				}
				setRefreshing(true, false);
				mFragment.getRecommendRefresh();
				break;
			}
		}
	}

	class ItemViewHolder extends ViewHolder implements OnClickListener {

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
			HomeLiveCategoryLive live = null;
			if (getAdapterPosition() < 7) {
				live = mRecommend.getLives().get(getAdapterPosition() - 1);
			} else if (getAdapterPosition() == 7) {
				live = mBanner.getLive();
			} else {
				live = mRecommend.getLives().get(getAdapterPosition() - 2);
			}
			Intent intent = new Intent(mContext, LiveActivity.class);
			intent.putExtra("live", live);
			mContext.startActivity(intent);
		}
	}

	class BannerViewHolder extends ViewHolder implements OnClickListener {

		ImageView mIvCover;
		TextView mTvTitle;
		LinearLayout mLayoutBottom;
		TextView mTvName;
		TextView mTvCount;
		View mVShadow;

		public BannerViewHolder(View view) {
			super(view);
			mIvCover = (ImageView) view
					.findViewById(R.id.listitem_home_live_banner_iv_cover);
			mTvTitle = (TextView) view
					.findViewById(R.id.listitem_home_live_banner_tv_title);
			mLayoutBottom = (LinearLayout) view
					.findViewById(R.id.listitem_home_live_banner_layout_bottom);
			mTvName = (TextView) view
					.findViewById(R.id.listitem_home_live_banner_tv_name);
			mTvCount = (TextView) view
					.findViewById(R.id.listitem_home_live_banner_tv_count);
			mVShadow = view
					.findViewById(R.id.listitem_home_live_banner_v_shadow);
			view.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
		}
	}
}
