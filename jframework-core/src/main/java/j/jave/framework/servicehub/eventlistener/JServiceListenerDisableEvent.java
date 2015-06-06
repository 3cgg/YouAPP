/**
 * 
 */
package j.jave.framework.servicehub.eventlistener;

import j.jave.framework.listener.JAPPEvent;
import j.jave.framework.listener.JAPPListener;
import j.jave.framework.servicehub.JListenerOnEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JServiceListenerDisableListener.class)
public class JServiceListenerDisableEvent extends JAPPEvent<JServiceListenerDisableEvent> {

	private static final long serialVersionUID = -4047669203423811947L;
	
	private final Class<?> serviceClass;
	
	private final Class<? extends JAPPListener> listener;
	
	public JServiceListenerDisableEvent(Object source,Class<?> serviceClass,Class<? extends JAPPListener> listener) {
		super(source);
		this.serviceClass=serviceClass;
		this.listener=listener;
	}

	public JServiceListenerDisableEvent(Object source,int priority ,Class<?> serviceClass,Class<? extends JAPPListener> listener) {
		super(source,priority);
		this.serviceClass=serviceClass;
		this.listener=listener;
	}

	public Class<?> getServiceClass() {
		return serviceClass;
	}
	
	public Class<? extends JAPPListener> getListener() {
		return listener;
	}
}
