package me.bunny.app._c._web.web.proext.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import me.bunny.kernel._c.support.databind.proext.annotation.PropertyExtendable;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@PropertyExtendable
public @interface CodeExtend{

	/**
	 * reference to another property, they must be included in the same class.
	 * i.e. if the referenced property appears in the super class, then an exception should be thrown to
	 * the caller. 
	 * @return
	 */
	String property();
	
	/**
	 * the category of all codes
	 * @return
	 */
	String codeType();
	
	boolean active() default true;
}
