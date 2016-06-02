/**
 * 
 */
package j.jave.kernal.jave.sync;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JSyncMonitorWakeupEvent.class)
public interface JSyncMonitorWakeupListener extends JAPPListener {
	
	public Object trigger(JSyncMonitorWakeupEvent event) ;
	
}
