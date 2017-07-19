package com.don.libirary.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.TYPE })
@Inherited
public @interface TranslucentStatusBar {
	/**
	 * 状态栏颜色 R.color
	 * 
	 * @return
	 */
	public abstract int color() default -1;

	/**
	 * 状态栏颜色 #FFFFFF
	 * 
	 * @return
	 */
	public abstract String sColor() default "";
}
