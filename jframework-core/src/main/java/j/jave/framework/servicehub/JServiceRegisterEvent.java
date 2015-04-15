/**
 * 
 */
package j.jave.framework.servicehub;

/**
 * service register event object. 
 * @author J
 */
public class JServiceRegisterEvent extends JServiceEvent {
	public JServiceRegisterEvent(Object source) {
		super(source);
		init();
	}
	
	private void init(){
		this.type=TYPE.REGISTER;
	}
	
	public JServiceRegisterEvent(Object source,Class<?> serviceName,JServiceFactory<?> serviceFactory) {
		super(source);
		setServiceName(serviceName);
		setServiceFactory(serviceFactory);
		init();
	}
}
