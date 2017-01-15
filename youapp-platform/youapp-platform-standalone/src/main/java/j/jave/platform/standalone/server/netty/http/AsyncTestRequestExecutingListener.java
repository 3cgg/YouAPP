/**
 * 
 */
package j.jave.platform.standalone.server.netty.http;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=AsyncTestRequestExecutingEvent.class)
public interface AsyncTestRequestExecutingListener extends JYouAPPListener {
	
	public Object trigger(AsyncTestRequestExecutingEvent event) ;
	
}
