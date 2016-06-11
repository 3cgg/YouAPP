/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.eventlistener;

import j.jave.kernal.eventdriven.servicehub.JYouAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JServicesOnListenerListener.class)
public class JServicesOnListenerEvent extends JYouAPPEvent<JServicesOnListenerEvent> {
	
	private final Class<?> listenerClass;
	
	public JServicesOnListenerEvent(Object source,Class<?> listenerClass) {
		super(source);
		this.listenerClass=listenerClass;
	}

	public JServicesOnListenerEvent(Object source,int priority ,Class<?> listenerClass) {
		super(source,priority);
		this.listenerClass=listenerClass;
	}

	public Class<?> getListenerClass() {
		return listenerClass;
	}
	
}
