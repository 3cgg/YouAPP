/**
 * 
 */
package j.jave.kernal.container.eventdriven;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JContainerUniquesGetEvent.class)
public interface JContainerUniquesGetListener extends JYouAPPListener{

	public Object trigger(JContainerUniquesGetEvent event);
	
}
