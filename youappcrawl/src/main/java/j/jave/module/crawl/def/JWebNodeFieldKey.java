package j.jave.module.crawl.def;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JWebNodeFieldKey {
	
	String xpath() default  JWebModelDefProperties.EMPTY;
	
	/**
	 * used to xpath
	 * @return
	 */
	String parser()  default  JWebModelDefProperties.EMPTY;

	String matchesValue() default JWebModelDefProperties.EMPTY;
	
	boolean requireMatch() default false;
	
}
