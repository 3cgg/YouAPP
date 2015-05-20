/**
 * 
 */
package j.jave.framework.servicehub.filedistribute;

import j.jave.framework.listener.JAPPListener;
import j.jave.framework.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JFileDistStoreEvent.class)
public interface JFileDistStoreListener extends JAPPListener{

	public Object trigger(JFileDistStoreEvent event);
	
}
