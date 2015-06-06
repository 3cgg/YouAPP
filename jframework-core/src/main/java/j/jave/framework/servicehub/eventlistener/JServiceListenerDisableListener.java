/**
 * 
 */
package j.jave.framework.servicehub.eventlistener;

import j.jave.framework.listener.JAPPListener;
import j.jave.framework.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceListenerDisableEvent.class)
public interface JServiceListenerDisableListener extends JAPPListener {
	
	public Object trigger(JServiceListenerDisableEvent event) ;
	
}
