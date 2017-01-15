/**
 * 
 */
package me.bunny.kernel.eventdriven.servicehub;


/**
 * @author J
 */
@JEventOnListener(name=JServiceListenerDetectEvent.class)
interface JServiceListenerDetectListener extends JYouAPPListener {
	
	public Object trigger(JServiceListenerDetectEvent event) ;
	
}
