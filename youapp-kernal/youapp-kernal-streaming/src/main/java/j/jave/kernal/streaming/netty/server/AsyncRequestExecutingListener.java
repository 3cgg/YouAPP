/**
 * 
 */
package j.jave.kernal.streaming.netty.server;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=AsyncRequestExecutingEvent.class)
public interface AsyncRequestExecutingListener extends JYouAPPListener {
	
	public Object trigger(AsyncRequestExecutingEvent event) ;
	
}
