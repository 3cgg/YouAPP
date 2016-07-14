/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.listener;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceExistsEvent.class)
public interface JServiceExistsListener extends JYouAPPListener {
	
	public Object trigger(JServiceExistsEvent event) ;
	
}
