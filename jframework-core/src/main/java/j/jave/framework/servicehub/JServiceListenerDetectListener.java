/**
 * 
 */
package j.jave.framework.servicehub;

import j.jave.framework.listener.JAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceListenerDetectEvent.class)
interface JServiceListenerDetectListener extends JAPPListener {
	
	public Object trigger(JServiceListenerDetectEvent event) ;
	
}
