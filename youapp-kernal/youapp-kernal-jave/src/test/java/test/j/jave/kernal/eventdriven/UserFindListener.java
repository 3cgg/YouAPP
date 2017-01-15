/**
 * 
 */
package test.j.jave.kernal.eventdriven;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=UserFindEvent.class)
public interface UserFindListener extends JYouAPPListener {
	
	public Object trigger(UserFindEvent event) ;
	
}
