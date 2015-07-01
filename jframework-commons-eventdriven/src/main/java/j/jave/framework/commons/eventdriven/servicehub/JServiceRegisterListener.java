/**
 * 
 */
package j.jave.framework.commons.eventdriven.servicehub;


/**
 * listener on the {@link JServiceEvent}.
 * @author J
 */
@JEventOnListener(name=JServiceRegisterEvent.class)
interface JServiceRegisterListener extends JAPPListener {
	
	public void trigger(JServiceRegisterEvent event);
	
}
