/**
 * 
 */
package j.jave.framework.commons.eventdriven.servicehub.eventlistener;

import j.jave.framework.commons.eventdriven.servicehub.JAPPEvent;
import j.jave.framework.commons.eventdriven.servicehub.JListenerOnEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JServiceUninstallListener.class)
public class JServiceUninstallEvent extends JAPPEvent<JServiceUninstallEvent> {

	private static final long serialVersionUID = -4047669203423811947L;
	
	private final Class<?> serviceClass;
	
	public JServiceUninstallEvent(Object source,Class<?> serviceClass) {
		super(source);
		this.serviceClass=serviceClass;
	}

	public JServiceUninstallEvent(Object source,int priority ,Class<?> serviceClass) {
		super(source,priority);
		this.serviceClass=serviceClass;
	}

	public Class<?> getServiceClass() {
		return serviceClass;
	}
	
}
