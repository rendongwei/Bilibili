package com.don.libirary.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import android.content.pm.ActivityInfo;

@Retention(RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.TYPE })
@Inherited
public @interface ScreenOrientation {
	/**
	 * 设置屏幕方向
	 * 
	 * 参数为ActivityInfo 默认值ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
	 * 
	 */
	public abstract int orientation() default ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
}
