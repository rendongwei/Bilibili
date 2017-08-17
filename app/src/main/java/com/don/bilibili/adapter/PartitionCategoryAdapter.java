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

public class PartitionCategoryAdapter extends
		RecyclerView.Adapter<ViewHolder> {

	private Context mContext;
	private int[] mImages = new int[] { R.drawable.ic_category_live,
			R.drawable.ic_category_t13, R.drawable.ic_category_t1,
			R.drawable.ic_category_t167, R.drawable.ic_category_t3,
			R.drawable.ic_category_t129, R.drawable.ic_category_t4,
			R.drawable.ic_category_t36, R.drawable.ic_category_t160,
			R.drawable.ic_category_t119, R.drawable.ic_category_t155,
			R.drawable.ic_category_t165, R.drawable.ic_category_t5,
			R.drawable.ic_category_t23, R.drawable.ic_category_t11,
			R.drawable.ic_category_game_center };
	private String[] mImageNames = new String[] { "直播", "番剧", "动画", "国创", "音乐",
			"舞蹈", "游戏", "科技", "生活", "鬼畜", "时尚", "广告", "娱乐", "电影", "电视剧", "游戏中心" };

	public PartitionCategoryAdapter(Context context) {
		super();
		mContext = context;
	}

	@Override
	public int getItemCount() {
		return 16;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
		View view = null;
		ViewHolder holder = null;
		view = View.inflate(mContext, R.layout.listitem_partition_category,
				null);
		holder = new ItemHolder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		ItemHolder itemHolder = (ItemHolder) holder;
		itemHolder.mIvImage.setImageResource(mImages[position]);
		itemHolder.mTvName.setText(mImageNames[position]);
	}

	class ItemHolder extends ViewHolder implements OnClickListener {

		ImageView mIvImage;
		TextView mTvName;

		public ItemHolder(View view) {
			super(view);
			mIvImage = (ImageView) view
					.findViewById(R.id.listitem_partition_category_iv_image);
			mTvName = (TextView) view
					.findViewById(R.id.listitem_partition_category_tv_name);
			view.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {

		}
	}
}
