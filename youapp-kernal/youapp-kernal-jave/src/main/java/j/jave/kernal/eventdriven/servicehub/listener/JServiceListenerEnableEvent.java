/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.listener;

import j.jave.kernal.eventdriven.servicehub.JYouAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JServiceListenerEnableListener.class)
public class JServiceListenerEnableEvent extends JYouAPPEvent<JServiceListenerEnableEvent> {

	private static final long serialVersionUID = -4047669203423811947L;
	
	private final Class<?> serviceClass;
	
	private final Class<? extends JYouAPPListener> listener;
	
	public JServiceListenerEnableEvent(Object source,Class<?> serviceClass,Class<? extends JYouAPPListener> listener) {
		super(source);
		this.serviceClass=serviceClass;
		this.listener=listener;
	}

	public JServiceListenerEnableEvent(Object source,int priority ,Class<?> serviceClass,Class<? extends JYouAPPListener> listener) {
		super(source,priority);
		this.serviceClass=serviceClass;
		this.listener=listener;
	}

	public Class<?> getServiceClass() {
		return serviceClass;
	}
	
	public Class<? extends JYouAPPListener> getListener() {
		return listener;
	}
}
