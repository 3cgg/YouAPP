/**
 * 
 */
package j.jave.framework.servicehub;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import j.jave.framework.listener.JAPPEvent;
import j.jave.framework.listener.JAPPListener;

/**
 * the annotation uses to make the program know what listener listener on what event. 
 * it is the bridge of {@link JAPPListener} and {@link JAPPEvent}
 * <strong> Note that the class only used together with {@link JAPPEvent} </strong>
 * @author J
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JListenerOnEvent {

	@SuppressWarnings("rawtypes")
	Class<? extends JAPPListener> name();
	
}
