package j.jave.platform.webcomp.web.proext.annotation;

import j.jave.kernal.jave.support.databind.proext.annotation.PropertyExtendable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@PropertyExtendable
public @interface ObjectExtend {
	/**
	 * reference to another property, they must be included in the same class.
	 * i.e. if the referenced property appears in the super class, then an exception should be thrown to
	 * the caller.  generally the property is the identifier that exists in the certain cache.
	 * @return
	 */
	String property();
	
	boolean active() default true;
}
