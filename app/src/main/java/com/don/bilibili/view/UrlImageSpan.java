package com.don.bilibili.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;

import com.don.bilibili.image.ImageManager;
import com.don.bilibili.utils.DisplayUtil;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.lang.reflect.Field;

public class UrlImageSpan extends VerticalCenterImageSpan {

	private Context mContext;
	private TextView mTextView;
	private String mUrl;
	private boolean mIsShowed;
	private ImageSize mImageSize;

	@SuppressWarnings("deprecation")
	public UrlImageSpan(Context context, TextView textView, String url,
			int widthDp, int heightDp) {
		super(new BitmapDrawable());
		mContext = context;
		mTextView = textView;
		mUrl = url;
		mImageSize = new ImageSize(DisplayUtil.dip2px(mContext, widthDp),
				DisplayUtil.dip2px(mContext, heightDp));
	}

	@Override
	public Drawable getDrawable() {
		if (!mIsShowed) {
			ImageManager.getInstance(mContext).getImageLoader()
					.loadImage(mUrl, mImageSize, new ImageLoadingListener() {

						@Override
						public void onLoadingStarted(String arg0, View arg1) {

						}

						@Override
						public void onLoadingFailed(String arg0, View arg1,
								FailReason arg2) {

						}

						@Override
						public void onLoadingComplete(String arg0, View arg1,
								Bitmap arg2) {
							BitmapDrawable b = new BitmapDrawable(mContext
									.getResources(), arg2);
							b.setBounds(0, 0, b.getIntrinsicWidth(),
									b.getIntrinsicHeight());
							Field mDrawable;
							Field mDrawableRef;
							try {
								mDrawable = ImageSpan.class
										.getDeclaredField("mDrawable");
								mDrawable.setAccessible(true);
								mDrawable.set(UrlImageSpan.this, b);

								mDrawableRef = DynamicDrawableSpan.class
										.getDeclaredField("mDrawableRef");
								mDrawableRef.setAccessible(true);
								mDrawableRef.set(UrlImageSpan.this, null);

								mIsShowed = true;
								mTextView.setText(mTextView.getText());
							} catch (IllegalAccessException e) {
							} catch (NoSuchFieldException e) {
							}
						}

						@Override
						public void onLoadingCancelled(String arg0, View arg1) {

						}
					});
		}
		return super.getDrawable();
	}
}
