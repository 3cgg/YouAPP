package j.jave.platform.sps.multiv;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JAssert;
import j.jave.platform.sps.core.SpringDynamicJARApplicationContext;
import j.jave.platform.sps.core.context.SpringContextSupport;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

public abstract class ComponentVersionSpringApplicationSupport {
	
	public static final JLogger LOGGER=JLoggerFactory.getLogger(ComponentVersionSpringApplicationSupport.class);
	
	private static final ComponentVersionPostService postService=JServiceHubDelegate
			.get().getService(new Object(), ComponentVersionPostService.class);
	
	/**
	 * predefined configuration of spring etc.
	 * @author J
	 */
	public static interface Component{
		public static final String CONFIG_LOCATION="config/";
		public static final String SPRING_LOCATION=CONFIG_LOCATION+"spring/";
	}
	
	/**
	 * predefined component properties.
	 * @author J
	 *
	 */
	public static interface ComponentProperties{
		
		public static final String PROPERTY_LOCATION=Component.CONFIG_LOCATION+"component-version.properties";
		
	}
	
	private static Map<String, ComponentVersionApplication> componentVersions=new ConcurrentHashMap<String, ComponentVersionApplication>();
	
	/**
	 * load dynamic component application. 
	 * @param parent
	 * @param jarUrls
	 * @return
	 */
	public static DynamicComponentVersionApplication loadComponent(ApplicationContext parent,URL[] jarUrls) throws Exception{
		DynamicComponentVersionApplication application=new DynamicComponentVersionApplication((ConfigurableApplicationContext)parent, jarUrls);
		//processing before initialization
		postService.before(application);
		
		SpringDynamicJARApplicationContext springDynamicJARApplicationCotext= application.load();
		String key=application.unique();
		componentVersions.put(key, application);
		SpringContextSupport.setApplicationContext(springDynamicJARApplicationCotext, key);
		
		//processing after initialization 
		postService.afterComplete(application);
		
		return application;
	}
	
	public static ApplicationContext getApplicationContext(String key){
		return SpringContextSupport.getApplicationContext(key);
	}
	
	public static ComponentVersionApplication getComponent(String key){
		return componentVersions.get(key);
	}
	
	public static void removeComponent(String key) throws Exception{
		ComponentVersionApplication old= componentVersions.remove(key);
		SpringContextSupport.removeApplicationContext(key);
		postService.cleanupAfterDestroy((DynamicComponentVersionApplication)old);
	}
	
	public static String unique(String appName,String componentName,String version){
		JAssert.isNotEmpty(appName);
		JAssert.isNotEmpty(componentName);
		JAssert.isNotEmpty(version);
		return appName+":"+componentName+":"+version;
	}
}
