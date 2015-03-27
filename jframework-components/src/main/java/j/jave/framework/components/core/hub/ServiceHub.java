/**
 * 
 */
package j.jave.framework.components.core.hub;

import j.jave.framework.components.login.service.LoginAccessService;
import j.jave.framework.components.memcached.JMemcachedDistService;

import java.util.HashMap;

/**
 * @author Administrator
 *
 */
public class ServiceHub {

	public static class StandardServiceInterfaces{
		public static final Class<LoginAccessService> LOGIN_ACCESS_SERVICE=LoginAccessService.class; 
		public static final Class<JMemcachedDistService> MEMCACHED_DIST_SERVICE=JMemcachedDistService.class;
	}
	
	private static HashMap<Class<? extends Service>, ServiceFactory<? extends Service>> services=new HashMap<Class<? extends Service>, ServiceFactory<? extends Service>>();
	
	public  <T> T getService(Class<T> clazz){
		return (T) services.get(clazz).getService();
	}
	
	public <T extends Service> void register(Class<T> clazz,ServiceFactory<T> serviceFactory){
		services.put(clazz, serviceFactory);
	}
	private ServiceHub(){}
	
	private static ServiceHub serviceHub;
	
	public static ServiceHub get(){
		if(serviceHub==null){
			serviceHub=new ServiceHub();
		}
		return serviceHub;
	}
	
	
	
	
}
