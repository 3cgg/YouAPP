/**
 * 
 */
package j.jave.framework.commons.eventdriven.servicehub;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * the annotation uses to make the program know what event listener on. 
 * it is the bridge of {@link JAPPListener} and {@link JAPPEvent}
 * <strong> Note that the class only used together with {@link JAPPListener} </strong>
 * @author J
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JEventOnListener {

	Class<? extends JAPPEvent<?>> name();
	
}
