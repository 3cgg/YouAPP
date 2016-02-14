/**
 * 
 */
package j.jave.kernal.filedistribute.eventdriven;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JFileDistStoreEvent.class)
public interface JFileDistStoreListener extends JAPPListener{

	public Object trigger(JFileDistStoreEvent event);
	
}
