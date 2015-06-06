/**
 * 
 */
package j.jave.framework.servicehub.eventlistener;

import j.jave.framework.listener.JAPPListener;
import j.jave.framework.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceListenerEnableEvent.class)
public interface JServiceListenerEnableListener extends JAPPListener {
	
	public Object trigger(JServiceListenerEnableEvent event) ;
	
}
