/**
 * 
 */
package test.j.jave.kernal.eventdriven;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=UserFindEvent.class)
public interface UserFindListener extends JYouAPPListener {
	
	public Object trigger(UserFindEvent event) ;
	
}
