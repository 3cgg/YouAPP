/**
 * 
 */
package j.jave.kernal.container.eventdriven;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JContainerRegisterEvent.class)
public interface JContainerRegisterListener extends JAPPListener{

	public Object trigger(JContainerRegisterEvent event);
	
}
