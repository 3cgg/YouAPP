/**
 * 
 */
package me.bunny.kernel.eventdriven.servicehub;


/**
 * @author J
 */
@JListenerOnEvent(name=JServiceListenerDetectListener.class)
class JServiceListenerDetectEvent extends JYouAPPEvent<JServiceListenerDetectEvent> {

	private static final long serialVersionUID = -4047669203423811947L;
	
	private final Class<?> serviceClass;
	
	private final Class<?> serviceFactoryClass;
	
	
	JServiceListenerDetectEvent(Object source,Class<?> serviceClass,Class<?> serviceFactoryClass) {
		super(source);
		this.serviceClass=serviceClass;
		this.serviceFactoryClass=serviceFactoryClass;
	}

	JServiceListenerDetectEvent(Object source,int priority ,Class<?> serviceClass,Class<?> serviceFactoryClass) {
		super(source,priority);
		this.serviceClass=serviceClass;
		this.serviceFactoryClass=serviceFactoryClass;
	}

	public Class<?> getServiceClass() {
		return serviceClass;
	}
	
	public Class<?> getServiceFactoryClass() {
		return serviceFactoryClass;
	}
	
}
