package com.don.bilibili.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
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
import com.don.bilibili.model.LiveRankSevenDay;
import com.don.bilibili.utils.DisplayUtil;
import com.don.bilibili.view.VerticalCenterImageSpan;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class LiveRankSevenDayAdapter extends
		RecyclerView.Adapter<ViewHolder> {

	private Context mContext;
	private List<LiveRankSevenDay> mSevenDays = new ArrayList<LiveRankSevenDay>();
	private WeakHashMap<Integer, Bitmap> mGuards = new WeakHashMap<Integer, Bitmap>();

	public LiveRankSevenDayAdapter(Context context,
			List<LiveRankSevenDay> sevenDays) {
		super();
		mContext = context;
		mSevenDays = sevenDays;
	}

	@Override
	public int getItemCount() {
		return mSevenDays.size();
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
				R.layout.listitem_live_rank_seven_day, parent, false);
		holder = new ItemHolder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		LiveRankSevenDay sevenDay = mSevenDays.get(position);
		ItemHolder itemHolder = (ItemHolder) holder;
		int id = mContext.getResources().getIdentifier(
				"ic_rank_" + sevenDay.getRank(), "drawable",
				mContext.getPackageName());
		itemHolder.mIvRank.setImageResource(id);
		if (sevenDay.getGuardLevel() > 0 && sevenDay.getGuardLevel() < 4) {
			String content = sevenDay.getGuardLevel() + " "
					+ sevenDay.getUname();
			SpannableString spannableString = new SpannableString(content);
			spannableString.setSpan(new VerticalCenterImageSpan(mContext,
					getGuard(sevenDay.getGuardLevel())), 0, 1,
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			itemHolder.mTvName.setText(spannableString);
		} else {
			itemHolder.mTvName.setText(sevenDay.getUname());
		}
		switch (sevenDay.getRank()) {
		case 1:
			itemHolder.mTvName.setTextColor(Color.parseColor("#D16700"));
			break;
			
		case 2:
			itemHolder.mTvName.setTextColor(Color.parseColor("#3BA6FF"));
			break;
			
		case 3:
			itemHolder.mTvName.setTextColor(Color.parseColor("#FF901D"));
			break;

		default:
			itemHolder.mTvName.setTextColor(Color.parseColor("#333333"));
			break;
		}
		itemHolder.mTvCount.setText(sevenDay.getCoin() + "");
	}

	private Bitmap getGuard(int guardLevel) {
		Bitmap bitmap;
		int id = 0;
		switch (guardLevel) {
		case 1:
			id = R.drawable.ic_live_guard_governor;
			break;
		case 2:
			id = R.drawable.ic_live_guard_commander;
			break;
		case 3:
			id = R.drawable.ic_live_guard_captain;
			break;
		}
		if (mGuards.containsKey(id)) {
			bitmap = mGuards.get(id);
		} else {
			bitmap = getZoom(id);
			mGuards.put(id, bitmap);
		}
		return bitmap;
	}

	private Bitmap getZoom(int id) {
		int widthAndHeight = DisplayUtil.dip2px(mContext, 20);
		Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),
				id);
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) widthAndHeight / bitmap.getWidth());
		float scaleHeight = ((float) widthAndHeight / bitmap.getHeight());
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		return newBitmap;
	}

	class ItemHolder extends ViewHolder implements OnClickListener {

		ImageView mIvRank;
		TextView mTvName;
		TextView mTvCount;

		public ItemHolder(View view) {
			super(view);
			mIvRank = (ImageView) view
					.findViewById(R.id.listitem_live_rank_seven_day_iv_rank);
			mTvName = (TextView) view
					.findViewById(R.id.listitem_live_rank_seven_day_tv_name);
			mTvCount = (TextView) view
					.findViewById(R.id.listitem_live_rank_seven_day_tv_count);
		}

		@Override
		public void onClick(View v) {

		}
	}
}
