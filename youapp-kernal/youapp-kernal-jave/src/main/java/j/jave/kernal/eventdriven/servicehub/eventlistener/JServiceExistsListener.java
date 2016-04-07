/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.eventlistener;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceExistsEvent.class)
public interface JServiceExistsListener extends JAPPListener {
	
	public Object trigger(JServiceExistsEvent event) ;
	
}
