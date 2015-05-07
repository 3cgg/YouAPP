/**
 * 
 */
package j.jave.framework.servicehub;

import j.jave.framework.listener.JAPPEvent;
import j.jave.framework.listener.JAPPListener;
import j.jave.framework.reflect.JClassUtils;
import j.jave.framework.utils.JUniqueUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * service hub. auto-loading all the implementations {@link JServiceFactory}
 * @author J
 */
class JServiceHub implements JService ,JServiceListenerDetectListener ,JServiceFactory<JServiceHub>  {
	
	
	protected final Logger LOGGER=LoggerFactory.getLogger(getClass());
	
	/**
	 * KEY: service. implements <code>JService</code>
	 * <p>VALUE : service factory .implements <code>JServiceFactory</code>
	 */
	private static Map<Class<?>, JServiceFactory<?>> services=new ConcurrentHashMap<Class<?>, JServiceFactory<?>>();
	
	private Object sync=new Object();
	
	/**
	 * KEY: service. implements <code>JService</code>
	 * <p>VALUE: listener.  extends <code>JAPPListener</code>
	 */
	private final static Map<Class<?>, List<Class<?>>> serviceListeners=new ConcurrentHashMap<Class<?>, List<Class<?>>>();

	/**
	 * KEY: listener.  extends <code>JAPPListener</code>
	 * <p>VALUE: services. implements <code>JService</code>
	 */
	private final static Map<Class<?>, List<Class<?>>> listenerServices=new ConcurrentHashMap<Class<?>, List<Class<?>>>();

	
	/**
	 * find all listeners on the event, then trigger one by one
	 * @param event
	 * @return <code>Object[]</code> , all listener returned. 
	 */ 
	public Object[] executeEventOnListener(JAPPEvent<?> event){
		Object[] objects=new Object[]{};
		try{
			// inner event, that initializes listenerServices property.
			if(JServiceListenerDetectEvent.class.isAssignableFrom(event.getClass())){
				objects=new Object[1];
				objects[0]=trigger((JServiceListenerDetectEvent) event);
				return objects;
			}
			
			
			Class<?> eventClass=event.getClass();
			JListenerOnEvent eventListener= eventClass.getAnnotation(JListenerOnEvent.class);
			if(eventListener==null){
				throw new IllegalStateException(eventClass.getName()+"must be modified by annotaion @JEventListener");
			}
			
			List<Class<?>> serviceClasses= listenerServices.get(eventListener.name());
			
			if(!serviceClasses.isEmpty()){
				objects=new Object[serviceClasses.size()];
				for(int i=0;i<serviceClasses.size();i++){
					Class<?> serviceClass=serviceClasses.get(i);
					JAPPListener<JAPPEvent<?>> listener=(JAPPListener<JAPPEvent<?>>) getService(serviceClass);
					objects[i]=listener.trigger(event);
				}
			}
		}catch(Exception e){
			LOGGER.error("Event Execute Error , caused by :",e);
		}
		return objects;
	}
	
	
	@SuppressWarnings("unchecked")
	public  <T> T getService(Class<T> clazz){
		return (T) services.get(clazz).getService();
	}
	
	public void register(Class<?> clazz,JServiceFactory<?> serviceFactory){
		synchronized (sync) {
			services.put(clazz, serviceFactory);
			JServiceEventProcessor.get().addEvent(new JServiceListenerDetectEvent(this, JAPPEvent.HIGEST, clazz));
		}
	}
	
	private JServiceHub(){}
	
	private static JServiceHub serviceHub;
	
	public static JServiceHub get(){
		if(serviceHub==null){
			serviceHub=new JServiceHub();
			//register self.
			serviceHub.register(JServiceHub.class, serviceHub);
		}
		return serviceHub;
	}

	@Override
	public Object trigger(JServiceListenerDetectEvent event) {
		Class<?> serviceClass =  event.getServiceClass();
		List<Class<?>> listenerClasses= JClassUtils.getAllInterfaces(serviceClass, JAPPListener.class);
		if(listenerClasses.size()>0){
			for(int i=0;i<listenerClasses.size();i++){
				Class<?> listenerClass=listenerClasses.get(i);
				JEventOnListener eventOnListener=listenerClass.getAnnotation(JEventOnListener.class);
				if(eventOnListener!=null){
					List<Class<?>> lnservices= listenerServices.get(listenerClass);
					if(lnservices==null){
						lnservices=new ArrayList<Class<?>>();
						listenerServices.put(listenerClass, lnservices);
					}
					lnservices.add(serviceClass);
				}
			}
		}
		serviceListeners.put(serviceClass, listenerClasses);
		return new Object();
	}

	@Override
	public JServiceHub getService() {
		return get();
	}

	@Override
	public String getName() {
		return "Service Hub";
	}

	@Override
	public String getUniqueId() {
		return JUniqueUtils.unique();
	}

	@Override
	public String describer() {
		return "Service Hub , contains all services provided by the platform.";
	}
	
	
	
	
}
