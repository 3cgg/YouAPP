package j.jave.framework.listener;

import java.util.EventListener;

/**
 * basic listener which all listeners should extend, provides the boundary to other system.
 * @author J
 * @param <T> implementations {@link JAPPEvent}
 */
public interface JAPPListener<T> extends EventListener {

	/**
	 * return expected object if . 
	 * @param event
	 * @return
	 */
	public Object trigger(T event);
	
}
