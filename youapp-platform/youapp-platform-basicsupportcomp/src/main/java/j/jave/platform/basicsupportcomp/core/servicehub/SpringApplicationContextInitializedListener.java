/**
 * 
 */
package j.jave.platform.basicsupportcomp.core.servicehub;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=SpringApplicationContextInitializedEvent.class)
public interface SpringApplicationContextInitializedListener extends JAPPListener {
	
	public Object trigger(SpringApplicationContextInitializedEvent event) ;
	
}
