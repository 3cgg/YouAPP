/**
 * 
 */
package j.jave.framework.commons.eventdriven.servicehub.eventlistener;

import j.jave.framework.commons.eventdriven.servicehub.JAPPListener;
import j.jave.framework.commons.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceListenerEnableEvent.class)
public interface JServiceListenerEnableListener extends JAPPListener {
	
	public Object trigger(JServiceListenerEnableEvent event) ;
	
}
