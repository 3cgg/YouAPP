/**
 * 
 */
package me.bunny.kernel._c.transaction.eventlistener;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=JTransactionManagerGetEvent.class)
public interface JTransactionManagerGetListener extends JYouAPPListener {
	
	public Object trigger(JTransactionManagerGetEvent event) ;
	
}
