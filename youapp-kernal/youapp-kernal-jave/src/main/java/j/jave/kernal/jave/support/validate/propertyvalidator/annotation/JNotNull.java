package j.jave.kernal.jave.support.validate.propertyvalidator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@PropertyValidatable
public @interface JNotNull {

	boolean active() default true;
	
}
