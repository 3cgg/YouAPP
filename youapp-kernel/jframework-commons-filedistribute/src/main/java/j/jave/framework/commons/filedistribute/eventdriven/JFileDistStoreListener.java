/**
 * 
 */
package j.jave.framework.commons.filedistribute.eventdriven;

import j.jave.framework.commons.eventdriven.servicehub.JAPPListener;
import j.jave.framework.commons.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JFileDistStoreEvent.class)
public interface JFileDistStoreListener extends JAPPListener{

	public Object trigger(JFileDistStoreEvent event);
	
}
