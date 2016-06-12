/**
 * 
 */
package j.jave.kernal.jave.sync;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JSyncMonitorWakeupEvent.class)
public interface JSyncMonitorWakeupListener extends JYouAPPListener {
	
	public Object trigger(JSyncMonitorWakeupEvent event) ;
	
}
