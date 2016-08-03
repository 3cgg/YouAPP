/**
 * 
 */
package j.jave.kernal.jave.sync;

import j.jave.kernal.eventdriven.servicehub.JEventOnListener;
import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=JAsyncTaskExecutingEvent.class)
public interface JAsyncTaskExecutingListener extends JYouAPPListener {
	
	public Object trigger(JAsyncTaskExecutingEvent event) ;
	
}
