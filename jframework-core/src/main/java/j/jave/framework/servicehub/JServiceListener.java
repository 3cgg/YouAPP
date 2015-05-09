/**
 * 
 */
package j.jave.framework.servicehub;

import j.jave.framework.listener.JAPPListener;
import j.jave.framework.servicehub.JServiceEvent.TYPE;

/**
 * listener on the {@link JServiceEvent}.
 * @author J
 */
public class JServiceListener implements JAPPListener {
	
	private JServiceHub serviceHub=JServiceHub.get();
	
	public Object trigger(JServiceEvent event) {
		Object obj=null;
		if(TYPE.REGISTER==event.getType()){
			serviceHub.register(event.getServiceName(), event.getServiceFactory());
		}
		else if(TYPE.GET==event.getType()){
			obj=serviceHub.getService(event.getServiceName());
		}
		return obj;
	}
	
}
