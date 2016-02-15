/**
 * 
 */
package test.j.jave.kernal.eventdriven;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=UserFindEvent.class)
public interface UserFindListener extends JAPPListener {
	
	public Object trigger(UserFindEvent event) ;
	
}
