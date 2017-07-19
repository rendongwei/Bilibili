package com.don.libirary.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.FIELD,
		java.lang.annotation.ElementType.METHOD })
@Inherited
public @interface OnClick {
	/**
	 * 设置控件的单击事件 自定义方法是需要输入控件的ID
	 * 
	 * @return
	 */
	public abstract int id() default -1;
	
	public abstract int parentViewId() default -1;

	public abstract String methodName() default "";
}
