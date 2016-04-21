/**
 * 
 */
package j.jave.kernal.container.eventdriven;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JContainerUniquesGetEvent.class)
public interface JContainerUniquesGetListener extends JAPPListener{

	public Object trigger(JContainerUniquesGetEvent event);
	
}
