/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * the annotation uses to make the program know what listener listener on what event. 
 * it is the bridge of {@link JYouAPPListener} and {@link JYouAPPEvent}
 * <strong> Note that the class only used together with {@link JYouAPPEvent} </strong>
 * @author J
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JListenerOnEvent {

	Class<? extends JYouAPPListener> name();
	
}
