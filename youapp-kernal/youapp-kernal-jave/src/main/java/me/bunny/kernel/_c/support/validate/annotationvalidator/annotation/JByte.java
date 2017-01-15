package me.bunny.kernel._c.support.validate.annotationvalidator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@PropertyValidatable
public @interface JByte {

	byte min() default Byte.MIN_VALUE;
	
	byte max() default Byte.MAX_VALUE;
	
	boolean active() default true;
}
