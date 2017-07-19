package com.don.libirary.util;

import android.content.Context;

public class DisplayUtil {

	public static int getWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	public static int getHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	public static float getDensity(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}

	public static float getScaledDensity(Context context) {
		return context.getResources().getDisplayMetrics().scaledDensity;
	}

	public static int px2dip(Context context, float pxValue) {
		return (int) (pxValue / getDensity(context) + 0.5f);
	}

	public static int dip2px(Context context, float dipValue) {
		return (int) (dipValue * getDensity(context) + 0.5f);
	}

	public static int px2sp(Context context, float pxValue) {
		return (int) (pxValue / getScaledDensity(context) + 0.5f);
	}

	public static int sp2px(Context context, float spValue) {
		return (int) (spValue * getScaledDensity(context) + 0.5f);
	}

}
