/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub;


/**
 * @author J
 */
@JEventOnListener(name=JServiceListenerDetectEvent.class)
interface JServiceListenerDetectListener extends JAPPListener {
	
	public Object trigger(JServiceListenerDetectEvent event) ;
	
}
