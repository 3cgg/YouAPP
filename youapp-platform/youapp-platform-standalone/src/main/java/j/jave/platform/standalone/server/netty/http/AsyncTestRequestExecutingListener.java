/**
 * 
 */
package j.jave.platform.standalone.server.netty.http;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=AsyncTestRequestExecutingEvent.class)
public interface AsyncTestRequestExecutingListener extends JYouAPPListener {
	
	public Object trigger(AsyncTestRequestExecutingEvent event) ;
	
}
