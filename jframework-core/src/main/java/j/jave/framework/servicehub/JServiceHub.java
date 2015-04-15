/**
 * 
 */
package j.jave.framework.servicehub;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * service hub. auto-loading all the implementations {@link JServiceFactory}
 * @author Administrator
 */
class JServiceHub {
	
	private static Map<Class<?>, JServiceFactory<?>> services=new ConcurrentHashMap<Class<?>, JServiceFactory<?>>();
	
	private Object sync=new Object();
	
	@SuppressWarnings("unchecked")
	public  <T> T getService(Class<T> clazz){
		return (T) services.get(clazz).getService();
	}
	
	public void register(Class<?> clazz,JServiceFactory<?> serviceFactory){
		synchronized (sync) {
			services.put(clazz, serviceFactory);
		}
	}
	
	private JServiceHub(){}
	
	private static JServiceHub serviceHub;
	
	public static JServiceHub get(){
		if(serviceHub==null){
			serviceHub=new JServiceHub();
		}
		return serviceHub;
	}
	
	
	
	
}
