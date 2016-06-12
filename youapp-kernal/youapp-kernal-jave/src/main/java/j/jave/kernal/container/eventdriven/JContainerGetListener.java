/**
 * 
 */
package j.jave.kernal.container.eventdriven;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JContainerGetEvent.class)
public interface JContainerGetListener extends JYouAPPListener{

	public Object trigger(JContainerGetEvent event);
	
}
