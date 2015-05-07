/**
 * 
 */
package j.jave.framework.servicehub;

import j.jave.framework.listener.JAPPEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JServiceListenerDetectListener.class)
class JServiceListenerDetectEvent extends JAPPEvent<JServiceListenerDetectEvent> {

	private static final long serialVersionUID = -4047669203423811947L;
	
	private final Class<?> serviceClass;
	
	JServiceListenerDetectEvent(Object source,Class<?> serviceClass) {
		super(source);
		this.serviceClass=serviceClass;
	}

	JServiceListenerDetectEvent(Object source,int priority ,Class<?> serviceClass) {
		super(source,priority);
		this.serviceClass=serviceClass;
	}

	public Class<?> getServiceClass() {
		return serviceClass;
	}
	
}
