package me.bunny.kernel._c.support.validate.annotationvalidator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@PropertyValidatable
public @interface JDouble {

	double min() default Double.NEGATIVE_INFINITY;
	
	double max() default Double.POSITIVE_INFINITY;
	
	boolean active() default true;
}
