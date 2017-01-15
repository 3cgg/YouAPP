/**
 * 
 */
package me.bunny.kernel.eventdriven.servicehub;

import me.bunny.kernel._c.service.JService;


/**
 * listener on the {@link JServiceEvent}.
 * @author J
 */
@JEventOnListener(name=JServiceGetEvent.class)
interface JServiceGetListener extends JYouAPPListener {
	
	public JService trigger(JServiceGetEvent event);
	
}
