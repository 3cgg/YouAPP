package j.jave.module.crawl.def;

import j.jave.module.crawl.kernel.JNodeAnalyse;
import j.jave.module.crawl.kernel.JWebDataGetter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JWebNodeModel {
	
	String url();
	
	boolean urlFixed() default false;
	
	/**
	 * element name
	 * @return
	 */
	String[] scope() default {};
	
	String tableOrient() default JWebModelDefProperties.EMPTY;
	
	/**
	 * for {@link JWebDataGetter}  xpath, scope
	 * @return
	 */
	String[] getter() default {JWebModelDefProperties.WEB_MODEL_GETTER_SCOPE};
	
	/**
	 * table...
	 * for {@link JNodeAnalyse}
	 * @return
	 */
	String[] analyse() default {JWebModelDefProperties.NODE_ANALYSE_TABLE};
	
	boolean single() default true;
	
}
