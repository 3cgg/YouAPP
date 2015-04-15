/**
 * 
 */
package j.jave.framework.servicehub;

import j.jave.framework.servicehub.JServiceEvent.TYPE;

/**
 * service get event object. 
 * @author J
 */
public class JServiceGetEvent extends JServiceEvent {

	public JServiceGetEvent(Object source) {
		super(source);
		init();
	}

	public JServiceGetEvent(Object source,Class<?> serviceName) {
		super(source);
		setServiceName(serviceName);
		init();
	}
	
	private void init(){
		this.type=TYPE.GET;
	}
}
