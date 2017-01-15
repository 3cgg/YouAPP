/**
 * 
 */
package me.bunny.kernel._c.async;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=JAsyncTaskExecutingEvent.class)
public interface JAsyncTaskExecutingListener extends JYouAPPListener {
	
	public Object trigger(JAsyncTaskExecutingEvent event) ;
	
}
