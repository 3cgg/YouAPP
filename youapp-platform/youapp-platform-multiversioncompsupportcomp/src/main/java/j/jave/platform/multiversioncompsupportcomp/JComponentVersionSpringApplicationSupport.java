package j.jave.platform.multiversioncompsupportcomp;

import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.io.JInputStreamWrapperSource;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.support._resource.JJARResourceURIScan;
import j.jave.kernal.jave.utils.JPropertiesUtils;
import j.jave.platform.basicsupportcomp.core.SpringDynamicJARApplicationCotext;
import j.jave.platform.basicsupportcomp.core.SpringDynamicJARApplicationCotext.JARScan;
import j.jave.platform.basicsupportcomp.core.context.SpringContextSupport;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.io.Resources;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

public abstract class JComponentVersionSpringApplicationSupport {
	
	protected final JLogger LOGGER=JLoggerFactory.getLogger(JComponentVersionSpringApplicationSupport.class);
	
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
		
		public static final String APP_NAME="j.jave.framework.components.multi.version.app.name";
		
		public static final String COMPONENT_NAME="j.jave.framework.components.multi.version.component.name";
		
		public static final String COMPONENT_VERSION="j.jave.framework.components.multi.version.component.version";
	}
	
	public static class ComponentVersionApplication{
		
		private int version;
		
		private String component;
		
		private String app;
		
		private final URL[] jarUrls;
		
		private final URLClassLoader urlClassLoader;

		private SpringDynamicJARApplicationCotext applicationContext;
		
		public URLClassLoader getUrlClassLoader() {
			return urlClassLoader;
		}
		
		public URL[] getJarUrls() {
			return jarUrls;
		}
		
		public ComponentVersionApplication(ConfigurableApplicationContext parent,URL[] jarUrls){
			this.jarUrls=jarUrls;
			applicationContext=new SpringDynamicJARApplicationCotext(parent, jarUrls);
			urlClassLoader=applicationContext.getUrlClassLoader();
			initContext();
		}

		public String unique(){
			return JComponentVersionSpringApplicationSupport.unique(app, component, version);
		}
		
		private void initContext(){
			
			findComponentInfo();
			
			applicationContext.setJarScan(new JARScan() {
				@Override
				public List<URI> scan(URL jar, URLClassLoader classLoader) throws Exception {
					JJARResourceURIScan jarResourceScan=new JJARResourceURIScan(jar.toURI());
					jarResourceScan.setClassLoader(classLoader);
					jarResourceScan.setRelativePath(Component.SPRING_LOCATION);
					return jarResourceScan.scan();
				}
			});
		}
		
		// get all info from component-version.properties.
		private void findComponentInfo(){
			
			InputStream inputStream=urlClassLoader.getResourceAsStream(ComponentProperties.PROPERTY_LOCATION);
			try {
				JInputStreamWrapperSource inputStreamWrapperSource=new JInputStreamWrapperSource(inputStream);
				Properties properties= JPropertiesUtils.loadProperties(inputStreamWrapperSource);
				app=JPropertiesUtils.getKey(ComponentProperties.APP_NAME, properties);
				component=JPropertiesUtils.getKey(ComponentProperties.COMPONENT_NAME, properties);
				version=Integer.parseInt(JPropertiesUtils.getKey(ComponentProperties.COMPONENT_VERSION, properties));
			} catch (Exception e) {
				throw new JInitializationException(e);
			}
		}
		
		SpringDynamicJARApplicationCotext load(){
			applicationContext.refresh();
			return applicationContext;
		}

		public int getVersion() {
			return version;
		}

		public String getComponent() {
			return component;
		}

		public String getApp() {
			return app;
		}
	}
	
	private static Map<String, ComponentVersionApplication> componentVersions=new ConcurrentHashMap<String, JComponentVersionSpringApplicationSupport.ComponentVersionApplication>();
	
	public static ComponentVersionApplication loadComponent(ApplicationContext parent,URL[] jarUrls){
		ComponentVersionApplication application=new ComponentVersionApplication((ConfigurableApplicationContext)parent, jarUrls);
		Resources.setCustomeClassLoader(application.unique(), application.getUrlClassLoader());
		SpringDynamicJARApplicationCotext springDynamicJARApplicationCotext= application.load();
		String key=application.unique();
		componentVersions.put(key, application);
		SpringContextSupport.setApplicationContext(springDynamicJARApplicationCotext, key);
		return application;
	}
	
	public static ApplicationContext getApplicationContext(String key){
		return SpringContextSupport.getApplicationContext(key);
	}
	
	public static void removeComponent(String key){
		componentVersions.remove(key);
		Resources.removeCustomClassLoader(key);
		SpringContextSupport.removeApplicationContext(key);
	}
	
	
	public static String unique(String appName,String componentName,int version){
		return appName+":"+componentName+":"+version;
	}
	
}
