/**
 * 
 */
package j.jave.framework.servicehub;

import j.jave.framework.listener.JAPPEvent;

/**
 * service event.
 * @author J
 */
public class JServiceEvent extends JAPPEvent<JServiceEvent> {

	private static final long serialVersionUID = -5902817626685017563L;

	enum TYPE{
		GET,REGISTER
	}
	
	protected TYPE type;
	
	private Class<?> serviceName;
	
	private JServiceFactory<?> serviceFactory;
	
	public JServiceEvent(Object source) {
		super(source);
	}

	

	public Class<?> getServiceName() {
		return serviceName;
	}

	public void setServiceName(Class<?> serviceName) {
		this.serviceName = serviceName;
	}

	public JServiceFactory<?> getServiceFactory() {
		return serviceFactory;
	}

	public void setServiceFactory(JServiceFactory<?> serviceFactory) {
		this.serviceFactory = serviceFactory;
	}
	
	/**
	 * @return the type
	 */
	public TYPE getType() {
		return type;
	}
	
	
}
