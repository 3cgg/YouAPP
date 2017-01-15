package me.bunny.kernel.jave.support.databind.proext.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import me.bunny.kernel.jave.utils.JDateUtils;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@PropertyExtendable
public @interface DateFormatExtend{

	/**
	 * reference to another property, they must be included in the same class.
	 * i.e. if the referenced property appears in the super class, then an exception should be thrown to
	 * the caller. 
	 * @return
	 */
	String property();
	
	/**
	 * the date format pattern, such as "yyyy-MM-dd",
	 * "yyyy-MM-dd HH:mm:ss",
	 * @return
	 */
	String pattern() default JDateUtils.yyyyMMddHHmmss;
	
	boolean active() default true;
}
