/**
 * 
 */
package j.jave.framework.commons.eventdriven.servicehub.eventlistener;

import j.jave.framework.commons.eventdriven.servicehub.JAPPListener;
import j.jave.framework.commons.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceListenerDisableEvent.class)
public interface JServiceListenerDisableListener extends JAPPListener {
	
	public Object trigger(JServiceListenerDisableEvent event) ;
	
}
