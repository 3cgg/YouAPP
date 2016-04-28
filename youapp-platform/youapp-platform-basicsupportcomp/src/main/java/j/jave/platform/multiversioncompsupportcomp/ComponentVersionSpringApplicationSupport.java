package j.jave.platform.multiversioncompsupportcomp;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JAssert;
import j.jave.platform.basicsupportcomp.core.SpringDynamicJARApplicationContext;
import j.jave.platform.basicsupportcomp.core.context.SpringContextSupport;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.io.Resources;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

public abstract class ComponentVersionSpringApplicationSupport {
	
	public static final JLogger LOGGER=JLoggerFactory.getLogger(ComponentVersionSpringApplicationSupport.class);
	
	/**
	 * predefined configuration of spring etc.
	 * @author J
	 */
	public static interface Component{
		public static final String CONFIG_LOCATION="config/";
		public static final String SPRING_LOCATION=CONFIG_LOCATION+"spring/";
		
		public static final String YOUAPP_WEB_APP_NAME_KEY="_youapp_app_name";
		public static final String YOUAPP_WEB_COM_NAME_KEY="_youapp_com_name";
		public static final String YOUAPP_WEB_COM_VER_KEY="_youapp_com_ver";
	}
	
	/**
	 * predefined component properties.
	 * @author J
	 *
	 */
	public static interface ComponentProperties{
		
		public static final String PROPERTY_LOCATION=Component.CONFIG_LOCATION+"component-version.properties";
		
		public static final String APP_NAME="j.jave.platform.components.multi.version.app.name";
		
		public static final String COMPONENT_NAME="j.jave.platform.components.multi.version.component.name";
		
		public static final String COMPONENT_VERSION="j.jave.platform.components.multi.version.component.version";
		
		public static final String COMPONENT_URL_PREFIX="j.jave.platform.components.multi.version.component.controller.prefix";
		
	}
	
	private static Map<String, ComponentVersionApplication> componentVersions=new ConcurrentHashMap<String, ComponentVersionApplication>();
	
	public static DynamicComponentVersionApplication loadComponent(ApplicationContext parent,URL[] jarUrls){
		DynamicComponentVersionApplication application=new DynamicComponentVersionApplication((ConfigurableApplicationContext)parent, jarUrls);
		Resources.setCustomeClassLoader(application.unique(), application.getUrlClassLoader());
		SpringDynamicJARApplicationContext springDynamicJARApplicationCotext= application.load();
		String key=application.unique();
		componentVersions.put(key, application);
		SpringContextSupport.setApplicationContext(springDynamicJARApplicationCotext, key);
		return application;
	}
	
	public static ApplicationContext getApplicationContext(String key){
		return SpringContextSupport.getApplicationContext(key);
	}
	
	public static ComponentVersionApplication getComponent(String key){
		return componentVersions.get(key);
	}
	
	public static void removeComponent(String key){
		componentVersions.remove(key);
		Resources.removeCustomClassLoader(key);
		SpringContextSupport.removeApplicationContext(key);
	}
	
	
	public static String unique(String appName,String componentName,int version){
		JAssert.isNotEmpty(appName);
		JAssert.isNotEmpty(componentName);
		return appName+":"+componentName+":"+version;
	}
	
	public static String unique(String appName,String componentName,String version){
		JAssert.isNotEmpty(appName);
		JAssert.isNotEmpty(componentName);
		JAssert.isNotEmpty(version);
		return appName+":"+componentName+":"+version;
	}
}
