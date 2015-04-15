package j.jave.framework.listener;

import java.util.EventListener;

/**
 * template for all listener. 
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
