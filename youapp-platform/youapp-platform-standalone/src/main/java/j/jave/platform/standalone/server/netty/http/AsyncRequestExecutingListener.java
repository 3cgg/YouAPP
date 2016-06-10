/**
 * 
 */
package j.jave.platform.standalone.server.netty.http;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=AsyncRequestExecutingEvent.class)
public interface AsyncRequestExecutingListener extends JAPPListener {
	
	public Object trigger(AsyncRequestExecutingEvent event) ;
	
}
