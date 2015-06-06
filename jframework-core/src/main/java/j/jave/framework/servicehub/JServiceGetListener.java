/**
 * 
 */
package j.jave.framework.servicehub;

import j.jave.framework.listener.JAPPListener;

/**
 * listener on the {@link JServiceEvent}.
 * @author J
 */
@JEventOnListener(name=JServiceGetEvent.class)
interface JServiceGetListener extends JAPPListener {
	
	public JService trigger(JServiceGetEvent event);
	
}
