/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.monitor;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

import java.util.List;

/**
 * @author J
 */
@JEventOnListener(name=JServiceMonitorEvent.class)
public interface JServiceMonitorListener extends JAPPListener {
	
	public List<JServiceRuntimeMeta> trigger(JServiceMonitorEvent event) ;
	
}
