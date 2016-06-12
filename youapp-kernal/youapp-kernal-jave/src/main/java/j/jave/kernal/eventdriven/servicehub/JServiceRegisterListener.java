/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub;


/**
 * listener on the {@link JServiceEvent}.
 * @author J
 */
@JEventOnListener(name=JServiceRegisterEvent.class)
interface JServiceRegisterListener extends JYouAPPListener {
	
	public void trigger(JServiceRegisterEvent event);
	
}
