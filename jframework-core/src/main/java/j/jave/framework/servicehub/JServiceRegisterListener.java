/**
 * 
 */
package j.jave.framework.servicehub;

import j.jave.framework.listener.JAPPListener;

/**
 * listener on the {@link JServiceEvent}.
 * @author J
 */
@JEventOnListener(name=JServiceRegisterEvent.class)
interface JServiceRegisterListener extends JAPPListener {
	
	public void trigger(JServiceRegisterEvent event);
	
}
