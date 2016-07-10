/**
 * 
 */
package j.jave.kernal.jave.transaction.eventlistener;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JTransactionManagerGetEvent.class)
public interface JTransactionManagerGetListener extends JYouAPPListener {
	
	public Object trigger(JTransactionManagerGetEvent event) ;
	
}
