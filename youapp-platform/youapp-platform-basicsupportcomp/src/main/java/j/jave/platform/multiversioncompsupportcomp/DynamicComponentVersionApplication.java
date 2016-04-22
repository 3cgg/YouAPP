package j.jave.platform.multiversioncompsupportcomp;

import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.io.JInputStreamWrapperSource;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.support._resource.JJARResourceURIScanner;
import j.jave.kernal.jave.utils.JPropertiesUtils;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.basicsupportcomp.core.SpringDynamicJARApplicationContext;
import j.jave.platform.basicsupportcomp.core.SpringDynamicJARApplicationContext.JARScan;
import j.jave.platform.multiversioncompsupportcomp.JComponentVersionSpringApplicationSupport.Component;
import j.jave.platform.multiversioncompsupportcomp.JComponentVersionSpringApplicationSupport.ComponentProperties;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

public class DynamicComponentVersionApplication extends ComponentVersionApplication {
	
	public static final JLogger LOGGER=JLoggerFactory.getLogger(JComponentVersionSpringApplicationSupport.class);
	
	private final URL[] jarUrls;
	
	private final URLClassLoader urlClassLoader;

	private SpringDynamicJARApplicationContext applicationContext;
	
	public SpringDynamicJARApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public URLClassLoader getUrlClassLoader() {
		return urlClassLoader;
	}
	
	public URL[] getJarUrls() {
		return jarUrls;
	}
	
	public DynamicComponentVersionApplication(ApplicationContext parent,URL[] jarUrls){
		this.jarUrls=jarUrls;
		applicationContext=new SpringDynamicJARApplicationContext(parent, jarUrls);
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
				JJARResourceURIScanner jarResourceScan=new JJARResourceURIScanner(jar.toURI());
				jarResourceScan.setClassLoader(classLoader);
				jarResourceScan.setRelativePath(Component.SPRING_LOCATION);
				return jarResourceScan.scan();
			}
		});
	}
	
	// get all info from component-version.properties.
	private void findComponentInfo(){
		
		InputStream inputStream=null;
		try {
			inputStream=urlClassLoader.findResource(ComponentProperties.PROPERTY_LOCATION).openStream();
			JInputStreamWrapperSource inputStreamWrapperSource=new JInputStreamWrapperSource(inputStream);
			Properties properties= JPropertiesUtils.loadProperties(inputStreamWrapperSource);
			app=JPropertiesUtils.getKey(ComponentProperties.APP_NAME, properties);
			component=JPropertiesUtils.getKey(ComponentProperties.COMPONENT_NAME, properties);
			version=JPropertiesUtils.getKey(ComponentProperties.COMPONENT_VERSION, properties);
			urlPrefix=JPropertiesUtils.getKey(ComponentProperties.COMPONENT_URL_PREFIX, properties);
			
			if(JStringUtils.isNotNullOrEmpty(urlPrefix)
					&&!(urlPrefix.startsWith("/")&&urlPrefix.endsWith("/"))){
				throw new JInitializationException(" the url prefix format must be '/..../'");
			}
		} catch (Exception e) {
			throw new JInitializationException(e);
		}finally{
			if(inputStream!=null){
				try {
					inputStream.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
	}
	
	SpringDynamicJARApplicationContext load(){
		applicationContext.setUnique(unique());
		applicationContext.setComponentVersionApplication(this);
		applicationContext.refresh();
		return applicationContext;
	}
	

	
	
}
