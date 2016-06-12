/**
 * 
 */
package j.jave.platform.basicsupportcomp.core.servicehub;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=SpringApplicationContextInitializedEvent.class)
public interface SpringApplicationContextInitializedListener extends JYouAPPListener {
	
	public Object trigger(SpringApplicationContextInitializedEvent event) ;
	
}
