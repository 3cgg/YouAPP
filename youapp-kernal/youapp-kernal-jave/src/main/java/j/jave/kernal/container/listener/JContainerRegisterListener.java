/**
 * 
 */
package j.jave.kernal.container.listener;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JContainerRegisterEvent.class)
public interface JContainerRegisterListener extends JYouAPPListener{

	public Object trigger(JContainerRegisterEvent event);
	
}
