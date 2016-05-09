/**
 * 
 */
package j.jave.kernal.jave.startup.eventlistener;

import j.jave.kernal.eventdriven.servicehub.JAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JApplicationStartupServicePushListener.class)
public class JApplicationStartupServicePushEvent extends JAPPEvent<JApplicationStartupServicePushEvent> {
	
	private final Class<?> serviceClass;
	
	public JApplicationStartupServicePushEvent(Object source,Class<?> serviceClass) {
		super(source);
		this.serviceClass=serviceClass;
	}

	public JApplicationStartupServicePushEvent(Object source,int priority ,Class<?> serviceClass) {
		super(source,priority);
		this.serviceClass=serviceClass;
	}

	public Class<?> getServiceClass() {
		return serviceClass;
	}
	
}
