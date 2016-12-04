/**
 * 
 */
package j.jave.kernal.streaming.netty.server;

import j.jave.kernal.eventdriven.servicehub.JEventOnListener;
import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=AsyncRequestExecutingEvent.class)
public interface AsyncRequestExecutingListener extends JYouAPPListener {
	
	public Object trigger(AsyncRequestExecutingEvent event) ;
	
}
