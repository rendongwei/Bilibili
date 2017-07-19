package com.don.bilibili.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.FIELD })
@Inherited
public @interface Id {
	/**
	 * 设置控件的ID
	 * 
	 * @return
	 */
	public abstract int id() default -1;

	public abstract int parentViewId() default -1;
	
}
