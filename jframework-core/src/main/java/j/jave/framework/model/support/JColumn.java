package j.jave.framework.model.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author J
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JColumn {
	
	String name();
	
	JSQLType type();
	
	int length() default Integer.MAX_VALUE;
	
	boolean nullable() default true;

}
