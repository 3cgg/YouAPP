/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.monitor;

import j.jave.kernal.eventdriven.servicehub.JYouAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;
import j.jave.kernal.jave.service.JService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author J
 */
@JListenerOnEvent(name=JServiceMonitorListener.class)
public class JServiceMonitorEvent extends JYouAPPEvent<JServiceMonitorEvent> {
	
	private Class<? extends JService>[] service;
	
	private Set<String> serviceNames=new HashSet<String>();
	
	public JServiceMonitorEvent(Object source) {
		this(source,new Class[]{});
	}
	public JServiceMonitorEvent(Object source,Class<? extends JService>... service) {
		super(source);
		this.service=service;
		for (int i = 0; i < service.length; i++) {
			serviceNames.add(service[i].getName());
		}
	}
	public JServiceMonitorEvent(Object source,int priority) {
		this(source,priority,new Class[]{});
	}
	
	public JServiceMonitorEvent(Object source,int priority,Class<? extends JService>... service) {
		super(source,priority);
		this.service=service;
		for (int i = 0; i < service.length; i++) {
			serviceNames.add(service[i].getName());
		}
	}
	
	public Class<? extends JService>[] getService() {
		return service;
	}
	public Set<String> getServiceNames() {
		return serviceNames;
	}
	public void addServiceName(String serviceName){
		if(!this.serviceNames.contains(serviceName)){
			this.serviceNames.add(serviceName);
		}
	}
	
	public void addAllServiceNames(Collection<String> serviceNames){
		this.serviceNames.addAll(serviceNames);
	}
}
