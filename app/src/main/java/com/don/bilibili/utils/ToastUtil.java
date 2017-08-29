package com.don.bilibili.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

	private static volatile Toast mShortToast;
	private static volatile Toast mLongToast;

	public static void showToast(Context context, CharSequence text) {
		if (mShortToast == null) {
			mShortToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		} else {
			mShortToast.setText(text);
		}
		mShortToast.show();
	}

	public static void cancelShortToast() {
		if (mShortToast != null) {
			mShortToast.cancel();
		}
	}

	public static void showLongToast(Context context, CharSequence text) {
		if (mLongToast == null) {
			mLongToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
		} else {
			mLongToast.setText(text);
		}
		mLongToast.show();
	}

	public static void cancelLongToast() {
		if (mLongToast != null) {
			mLongToast.cancel();
		}
	}
}
