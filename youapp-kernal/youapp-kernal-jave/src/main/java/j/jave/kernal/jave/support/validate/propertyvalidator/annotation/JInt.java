package j.jave.kernal.jave.support.validate.propertyvalidator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@PropertyValidatable
public @interface JInt {

	int min() default Integer.MIN_VALUE;
	
	int max() default Integer.MAX_VALUE;
	
	boolean active() default true;
}
