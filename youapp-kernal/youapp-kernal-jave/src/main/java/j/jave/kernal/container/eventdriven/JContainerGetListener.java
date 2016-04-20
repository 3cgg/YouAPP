/**
 * 
 */
package j.jave.kernal.container.eventdriven;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JContainerGetEvent.class)
public interface JContainerGetListener extends JAPPListener{

	public Object trigger(JContainerGetEvent event);
	
}
