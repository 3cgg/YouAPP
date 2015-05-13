/**
 * 
 */
package j.jave.framework.components.web.subhub.sessionuser;

import j.jave.framework.listener.JAPPEvent;
import j.jave.framework.servicehub.JListenerOnEvent;

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
