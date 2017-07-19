package com.don.bilibili.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.FIELD })
@Inherited
public @interface Resource {

	public abstract int color() default -1;

	public abstract int drawable() default -1;

	public abstract int string() default -1;

	public abstract int dimen() default -1;
}
