package j.jave.kernal.jave.model.support;

import j.jave.kernal.jave.model.JBaseModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * which model the mapper supports.
 * @author J
 */
@Target(value=ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JModelMapper {

	/**
	 * name of model of mapping to table . @see
	 * {@link JTable}
	 * @return
	 */
	Class<? extends JBaseModel> name ();
	
	String component() default "";
}
