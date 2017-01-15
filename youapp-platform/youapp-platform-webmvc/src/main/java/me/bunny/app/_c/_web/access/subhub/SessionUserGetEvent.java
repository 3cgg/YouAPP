/**
 * 
 */
package me.bunny.app._c._web.access.subhub;

import me.bunny.kernel.eventdriven.servicehub.JListenerOnEvent;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPEvent;

/**
 * session user get event.
 * @author J
 */
@JListenerOnEvent(name=SessionUserGetListener.class)
public class SessionUserGetEvent extends JYouAPPEvent<SessionUserGetEvent> {

	/**
	 * @param source
	 * @param priority
	 */
	public SessionUserGetEvent(Object source, int priority) {
		super(source, priority);
	}

	/**
	 * @param source
	 * @param priority
	 * @param unique
	 */
	public SessionUserGetEvent(Object source, int priority, String unique) {
		super(source, priority, unique);
	}
	
	
		/**
	 * @param source
	 */
	public SessionUserGetEvent(Object source) {
		super(source);
	}



	
	
	
}
