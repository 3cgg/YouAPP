package me.bunny.kernel._c.model.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import me.bunny.kernel._c.model.JBaseModel;

/**
 * which model the mapper supports.
 * @author J
 */
@Target(value=ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JModelRepo {

	/**
	 * name of model of mapping to table . @see
	 * {@link JTable}
	 * @return
	 */
	Class<? extends JBaseModel> name ();
	
	String component() default "";
}
