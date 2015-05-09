/**
 * 
 */
package j.jave.framework.servicehub.filedistribute;

import j.jave.framework.listener.JAPPListener;
import j.jave.framework.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JFileDisStoreEvent.class)
public interface JFileDisStoreListener extends JAPPListener{

	public Object trigger(JFileDisStoreEvent event);
	
}
