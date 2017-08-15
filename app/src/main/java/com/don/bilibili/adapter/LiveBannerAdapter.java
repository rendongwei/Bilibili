package com.don.bilibili.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.don.bilibili.model.HomeLiveBanner;
import com.don.bilibili.R;
import com.don.bilibili.image.ImageManager;

import java.util.List;

public class LiveBannerAdapter extends PagerAdapter {
	private Context mContext;
	private List<HomeLiveBanner> mBanners;

	public LiveBannerAdapter(Context context, List<HomeLiveBanner> banners) {
		mContext = context;
		mBanners = banners;
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		ImageView imageView = (ImageView) LayoutInflater.from(mContext)
				.inflate(R.layout.pageritem_banner, null);
		String img = mBanners.get(position % mBanners.size()).getImg();
		ImageManager.getInstance(mContext).showImage(imageView, img);
		container.addView(imageView);
		return imageView;
	}

}
