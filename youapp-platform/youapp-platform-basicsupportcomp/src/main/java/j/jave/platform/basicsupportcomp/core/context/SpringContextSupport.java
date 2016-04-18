/**
 * 
 */
package j.jave.platform.basicsupportcomp.core.context;

import j.jave.kernal.jave.utils.JCollectionUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Quickly Get Spring Context. the property {@link #applicationContext} can be initialized during the Spring Initialization.
 * <strong>note that the class can be only initialized by Spring , any new instance never supports the ability of exposing Spring Context</strong>
 * @author J
 * @see ApplicationContextAware
 */
@Service("SpringContextSupport-Get-Bean-Not-Supported")
public class SpringContextSupport implements ApplicationContextAware {
	
	public static final String PLATFORM="YOUAPP:COM:0";
	
	/**
	 * platform application context. 
	 */
	private static ApplicationContext applicationContext;

	public static  ApplicationContext getApplicationContext(String... unique) {
		
		if(JCollectionUtils.hasInArray(unique)){
			return getApplicationContext(unique[0]);
		}
		return applicationContext;
	}

	public  void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextSupport.applicationContext = applicationContext;
		setApplicationContext(applicationContext, PLATFORM);
	}
	
	/**
	 * KEY : consist of  app:component : version 
	 * VALUE : Application Context
	 */
	private static Map<String, ApplicationContext> applicationContexts=new ConcurrentHashMap<String, ApplicationContext>();
	
	public static  ApplicationContext getApplicationContext(String unique) {
		return applicationContexts.get(unique);
	}
	
	public static void setApplicationContext(ApplicationContext applicationContext,String unique) {
		applicationContexts.put(unique, applicationContext);
	}
	
	public static void removeApplicationContext(String unique){
		applicationContexts.remove(unique);
	}
	
	
}
