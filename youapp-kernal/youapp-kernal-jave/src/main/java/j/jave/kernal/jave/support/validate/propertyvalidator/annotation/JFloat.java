package j.jave.kernal.jave.support.validate.propertyvalidator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@PropertyValidatable
public @interface JFloat {

	float min() default Float.NEGATIVE_INFINITY;
	
	float max() default Float.POSITIVE_INFINITY;
	
	boolean active() default true;
}
