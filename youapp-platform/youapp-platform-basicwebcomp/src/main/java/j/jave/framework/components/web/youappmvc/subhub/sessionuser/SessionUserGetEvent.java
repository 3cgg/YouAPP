/**
 * 
 */
package j.jave.framework.components.web.youappmvc.subhub.sessionuser;

import j.jave.framework.commons.eventdriven.servicehub.JAPPEvent;
import j.jave.framework.commons.eventdriven.servicehub.JListenerOnEvent;

/**
 * session user get event.
 * @author J
 */
@JListenerOnEvent(name=SessionUserGetListener.class)
public class SessionUserGetEvent extends JAPPEvent<SessionUserGetEvent> {

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
