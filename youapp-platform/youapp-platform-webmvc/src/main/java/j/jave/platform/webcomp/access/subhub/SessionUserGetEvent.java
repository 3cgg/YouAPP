/**
 * 
 */
package j.jave.platform.webcomp.access.subhub;

import j.jave.kernal.eventdriven.servicehub.JYouAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;

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
