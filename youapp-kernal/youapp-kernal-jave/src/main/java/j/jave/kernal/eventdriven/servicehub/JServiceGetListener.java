/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.jave.service.JService;


/**
 * listener on the {@link JServiceEvent}.
 * @author J
 */
@JEventOnListener(name=JServiceGetEvent.class)
interface JServiceGetListener extends JYouAPPListener {
	
	public JService trigger(JServiceGetEvent event);
	
}
