package j.jave.kernal.jave.support.validate.annotationvalidator.annotation;

import j.jave.kernal.jave.utils.JDateUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@PropertyValidatable
public @interface JDate {

	long min() default 0;
	
	long max() default Long.MAX_VALUE;
	
	boolean active() default true;
	
	String pattern() default JDateUtils.ANY;
}
