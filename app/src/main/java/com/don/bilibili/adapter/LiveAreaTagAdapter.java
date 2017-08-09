package com.don.bilibili.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.don.bilibili.R;
import com.don.bilibili.activity.live.LiveAreaActivity;

import java.util.ArrayList;
import java.util.List;

public class LiveAreaTagAdapter extends
		RecyclerView.Adapter<ViewHolder> {

	private Context mContext;
	private List<String> mTags = new ArrayList<String>();
	private boolean mShowAll;
	private String mTag;

	public LiveAreaTagAdapter(Context context, List<String> tags,
			boolean showAll, String tag) {
		super();
		mContext = context;
		mTags = tags;
		mShowAll = showAll;
		mTag = tag;
	}

	@Override
	public int getItemCount() {
		return mShowAll ? mTags.size() : mTags.size() > 5 ? 5 : mTags.size();
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
		View view = null;
		ViewHolder holder = null;
		view = View.inflate(mContext, R.layout.listitem_live_area_tag, null);
		holder = new ItemHolder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		String tag = mTags.get(position);
		ItemHolder itemHolder = (ItemHolder) holder;
		itemHolder.mTvName.setText(tag);
		boolean isSelected = tag.equals(mTag)
				|| (tag.equals("全部") && "".equals(mTag));
		itemHolder.mTvName.setTextColor(ContextCompat.getColor(mContext,
				isSelected ? R.color.pink : R.color.black));
		itemHolder.mTvName
				.setBackgroundResource(isSelected ? R.drawable.bg_live_area_tag_pink
						: R.drawable.bg_live_area_tag_gray);
	}

	class ItemHolder extends ViewHolder implements OnClickListener {

		TextView mTvName;

		public ItemHolder(View view) {
			super(view);
			mTvName = (TextView) view
					.findViewById(R.id.listitem_live_area_tag_tv_name);
			view.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			mTag = getAdapterPosition() == 0 ? "" : mTags
					.get(getAdapterPosition());
			notifyDataSetChanged();
			((LiveAreaActivity)mContext).changeTag(mTag);
		}
	}
}
