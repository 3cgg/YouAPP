/**
 * 
 */
package j.jave.framework.commons.eventdriven.servicehub;

import j.jave.framework.commons.service.JService;


/**
 * listener on the {@link JServiceEvent}.
 * @author J
 */
@JEventOnListener(name=JServiceGetEvent.class)
interface JServiceGetListener extends JAPPListener {
	
	public JService trigger(JServiceGetEvent event);
	
}
