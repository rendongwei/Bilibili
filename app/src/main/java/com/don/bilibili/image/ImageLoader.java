package com.don.bilibili.image;

import android.content.Context;
import android.os.Environment;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DefaultConfigurationFactory;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;
import java.io.IOException;

public class ImageLoader {

	private static volatile ImageLoader mImageLoader;
	private Context mContext;
	private ImageLoaderConfiguration mConfig;
	private ImageLoaderConfiguration.Builder mConfigBuilder;
	private com.nostra13.universalimageloader.core.ImageLoader mLoader;
	private DisplayImageOptions mOptions;
	private DisplayImageOptions.Builder mOptionsBuilder;

	private ImageLoader(Context context) {
		this(context, null);
	}

	private ImageLoader(Context context, ImageLoaderConfiguration config) {
		mContext = context;
		if (config == null) {
			createDefaultConfig();
		} else {
			mConfig = config;
		}
		initImageLoader(mConfig);
	}

	public static synchronized ImageLoader getInstance(Context context) {
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader(context);
		}
		return mImageLoader;
	}

	public static synchronized ImageLoader getInstance(Context context,
			ImageLoaderConfiguration config) {
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader(context, config);
		}
		return mImageLoader;
	}

	public void initImageLoader(ImageLoaderConfiguration config) {
		if (config == null) {
			return;
		}
		mConfig = config;
		mLoader = com.nostra13.universalimageloader.core.ImageLoader
				.getInstance();
		mLoader.init(config);
		createDefaultOptions();
	}

	public ImageLoaderConfiguration createDefaultConfig() {
		mConfigBuilder = new ImageLoaderConfiguration.Builder(mContext);
		mConfigBuilder.memoryCacheExtraOptions(480, 800)
				.diskCacheExtraOptions(480, 800, null)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.memoryCacheSize(2 * 1024 * 1024)
				.diskCacheSize(100 * 1024 * 1024).diskCacheFileCount(5000)
				.diskCacheFileNameGenerator(new Md5FileNameGenerator());
		String filePath = Environment.getExternalStorageDirectory().getPath()
				+ File.separator + "Android" + File.separator + "data"
				+ File.separator + mContext.getPackageName() + File.separator
				+ "cache" + File.separator;
		File file = new File(filePath);
		if (file != null && !file.exists()) {
			try {
				file.createNewFile();
				mConfigBuilder.diskCache(new UnlimitedDiskCache(file));
			} catch (IOException e) {
			}
		}
		mConfig = mConfigBuilder.build();
		return mConfig;
	}

	public DisplayImageOptions createDefaultOptions() {
		mOptionsBuilder = new DisplayImageOptions.Builder();
		mOptionsBuilder.cacheInMemory(true).cacheOnDisk(true)
				.displayer(DefaultConfigurationFactory.createBitmapDisplayer());
		mOptions = mOptionsBuilder.build();
		return mOptions;
	}

	public DisplayImageOptions createDefaultOptions(int resId) {
		mOptionsBuilder = new DisplayImageOptions.Builder();
		mOptionsBuilder.cacheInMemory(true).cacheOnDisk(true)
				.displayer(DefaultConfigurationFactory.createBitmapDisplayer());
		if (resId != -1) {
			mOptionsBuilder.showImageForEmptyUri(resId).showImageOnFail(resId)
					.showImageOnLoading(resId);
		}
		mOptions = mOptionsBuilder.build();
		return mOptions;
	}

	public com.nostra13.universalimageloader.core.ImageLoader getLoader() {
		return mLoader;
	}

	public void displayImage(String uri, ImageView imageView) {
		mLoader.displayImage(uri, imageView, mOptions);
	}

	public void displayImage(String uri, ImageView imageView,
			DisplayImageOptions options) {
		mLoader.displayImage(uri, imageView, options);
	}

	public void displayImage(String uri, ImageView imageView,
			ImageLoadingListener listener) {
		mLoader.displayImage(uri, imageView, mOptions, listener);
	}

	public void displayImage(String uri, ImageView imageView,
			DisplayImageOptions options, ImageLoadingListener listener) {
		mLoader.displayImage(uri, imageView, options, listener);
	}

	public void loadImage(String uri, ImageLoadingListener listener) {
		mLoader.loadImage(uri, mOptions, listener);
	}

	public void loadImage(String uri, DisplayImageOptions options,
			ImageLoadingListener listener) {
		mLoader.loadImage(uri, options, listener);
	}

	public void loadImage(String uri, ImageSize minImageSize,
			ImageLoadingListener listener) {
		mLoader.loadImage(uri, minImageSize, mOptions, listener);
	}

	public void loadImage(String uri, ImageSize targetImageSize,
			DisplayImageOptions options, ImageLoadingListener listener) {
		mLoader.loadImage(uri, targetImageSize, options, listener);
	}

	public void onResume() {
		mLoader.resume();
	}

	public void onPause() {
		mLoader.pause();
	}

	public void onStop() {
		mLoader.stop();
	}

	public void onDestroy() {
		mLoader.destroy();
		mImageLoader = null;
	}

	public void clearMemoryCache() {
		mLoader.clearMemoryCache();
	}

	public void clearDiscCache() {
		mLoader.clearDiskCache();
	}
}
