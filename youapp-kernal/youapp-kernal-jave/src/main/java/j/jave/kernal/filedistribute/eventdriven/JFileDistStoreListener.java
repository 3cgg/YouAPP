/**
 * 
 */
package j.jave.kernal.filedistribute.eventdriven;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JFileDistStoreEvent.class)
public interface JFileDistStoreListener extends JYouAPPListener{

	public Object trigger(JFileDistStoreEvent event);
	
}
