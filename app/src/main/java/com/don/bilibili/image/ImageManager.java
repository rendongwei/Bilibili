package com.don.bilibili.image;

import android.content.Context;
import android.widget.ImageView;

import com.don.bilibili.R;
import com.don.bilibili.utils.DisplayUtil;
import com.don.bilibili.utils.EmptyUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;

public class ImageManager {

	private static volatile ImageManager mManager;
	private ImageLoader mImageLoader;
	private DisplayImageOptions mDefaultOptions;
	private DisplayImageOptions mHeadOptions;
	private DisplayImageOptions mRoundSquareDisplayImageOptions;
	private DisplayImageOptions mRoundDisplayImageOptions;

	private ImageManager(Context context) {
		mImageLoader = ImageLoader.getInstance(context);
		mDefaultOptions = mImageLoader.createDefaultOptions();

		mHeadOptions = mImageLoader
				.createDefaultOptions(R.drawable.bili_default_avatar);

		Builder mOptionsBuilder = new Builder();
		mOptionsBuilder
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.displayer(
						new FlexibleRoundedBitmapDisplayer(
								DisplayUtil.dip2px(context, 4),
								FlexibleRoundedBitmapDisplayer.CORNER_TOP_LEFT
										| FlexibleRoundedBitmapDisplayer.CORNER_TOP_RIGHT))
				.showImageForEmptyUri(R.drawable.ic_next_video_placeholder)
				.showImageOnFail(R.drawable.ic_next_video_placeholder);
		mRoundSquareDisplayImageOptions = mOptionsBuilder.build();

		mOptionsBuilder = new Builder();
		mOptionsBuilder
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.displayer(
						new FlexibleRoundedBitmapDisplayer(DisplayUtil.dip2px(
								context, 4)))
				.showImageForEmptyUri(R.drawable.ic_next_video_placeholder)
				.showImageOnFail(R.drawable.ic_next_video_placeholder);
		mRoundDisplayImageOptions = mOptionsBuilder.build();
	}

	public static synchronized ImageManager getInstance(Context context) {
		if (mManager == null) {
			mManager = new ImageManager(context);
		}
		return mManager;
	}

	public ImageLoader getImageLoader() {
		return mImageLoader;
	}

	public void showImage(ImageView imageView, String url) {
		if (EmptyUtil.isEmpty(url)) {
			imageView.setImageBitmap(null);
		}
		mImageLoader.displayImage(url, imageView, mDefaultOptions);
	}

	public void showHead(ImageView imageView, String url) {
		if (EmptyUtil.isEmpty(url)) {
			imageView.setImageResource(R.drawable.bili_default_avatar);
		}
		mImageLoader.displayImage(url, imageView, mHeadOptions);
	}

	public void showRoundSquareImage(ImageView imageView, String url) {
		mImageLoader.displayImage(url, imageView,
				mRoundSquareDisplayImageOptions);
	}

	public void showRoundImage(ImageView imageView, String url) {
		mImageLoader.displayImage(url, imageView, mRoundDisplayImageOptions);
	}
}
