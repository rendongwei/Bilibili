package com.don.bilibili.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.don.bilibili.model.HomeLiveCategoryLive;
import com.don.bilibili.R;
import com.don.bilibili.activity.live.LiveActivity;
import com.don.bilibili.image.ImageManager;
import com.don.bilibili.utils.TextLinkUtil;
import com.don.bilibili.view.ClickableMovementMethod;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class LiveAllAdapter extends
		RecyclerView.Adapter<ViewHolder> {

	private Context mContext;
	private List<HomeLiveCategoryLive> mLives = new ArrayList<HomeLiveCategoryLive>();
	private int mType;

	public LiveAllAdapter(Context context, List<HomeLiveCategoryLive> lives,
			int type) {
		super();
		mContext = context;
		mLives = lives;
		mType = type;
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
		view = View.inflate(mContext, R.layout.listitem_live_all, null);
		holder = new ItemViewHolder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		HomeLiveCategoryLive live = mLives.get(position);
		ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
		ImageManager.getInstance(mContext).showRoundSquareImage(
				itemViewHolder.mIvCover, live.getCover().getSrc());
		if (mType == 3) {
			itemViewHolder.mTvTitle.setText(live.getTitle());
		}else {
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
		}
		
		itemViewHolder.mTvName.setText(live.getOwner().getName());
		String count = "";
		DecimalFormat df = new DecimalFormat("0.##");
		if (live.getOnline() > 10000) {
			double d = (double) live.getOnline() / 10000d;
			count = df.format(d) + "万";
		} else {
			count = live.getOnline() + "";
		}
		itemViewHolder.mLayoutCount.setVisibility(mType == 3 ? View.GONE
				: View.VISIBLE);
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
					.findViewById(R.id.listitem_live_all_iv_cover);
			mTvTitle = (TextView) view
					.findViewById(R.id.listitem_live_all_tv_title);
			mTvName = (TextView) view
					.findViewById(R.id.listitem_live_all_tv_name);
			mLayoutCount = (LinearLayout) view
					.findViewById(R.id.listitem_live_all_layout_count);
			mTvCount = (TextView) view
					.findViewById(R.id.listitem_live_all_tv_count);
			mVShadow = view.findViewById(R.id.listitem_live_all_v_shadow);
			view.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			HomeLiveCategoryLive live = mLives.get(getAdapterPosition());
			Intent intent = new Intent(mContext, LiveActivity.class);
			intent.putExtra("live", live);
			mContext.startActivity(intent);
		}
	}
}
