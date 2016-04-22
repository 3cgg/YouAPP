package j.jave.platform.basicsupportcomp.core;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.support._resource.JJARResourceURIScanner;
import j.jave.kernal.jave.utils.JCollectionUtils;
import j.jave.platform.basicsupportcomp.core.container.MappingMeta;
import j.jave.platform.multiversioncompsupportcomp.ComponentVersionApplication;
import j.jave.platform.multiversioncompsupportcomp.DynamicComponentVersionApplication;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.core.io.FileSystemResourceLoader;

/**
 * default the application automatically scan all spring configuration with the file name starting with "spring" in the related JAR file to find all spring XMLs .
 * You also can provide yourself {@link JARScan} to search spring XML , not only from the JAR.
 * @author J
 * @see JARScan
 */
public class SpringDynamicJARApplicationContext extends AbstractXmlApplicationContext {
	
	protected final JLogger LOGGER=JLoggerFactory.getLogger(getClass());
	
	/**
	 * component information. app:component:version
	 */
	private String unique;
	
	private DynamicComponentVersionApplication componentVersionApplication;
	
	private final URLClassLoader urlClassLoader;
	
	private JARScan jarScan;
	
	private Map<String, MappingMeta> mappingMetas=new ConcurrentHashMap<String, MappingMeta>(128);
	
	public void setJarScan(JARScan jarScan) {
		this.jarScan = jarScan;
	}
	
	public URLClassLoader getUrlClassLoader() {
		return urlClassLoader;
	}

	public String getUnique() {
		return unique;
	}
	
	public void setUnique(String unique) {
		this.unique = unique;
	}
	
	public SpringDynamicJARApplicationContext(ApplicationContext applicationContext,URL[] jarUrls){
		super(applicationContext);
		this.urlClassLoader=new URLClassLoader(jarUrls,applicationContext.getClassLoader());
	}
	
	public SpringDynamicJARApplicationContext(ConfigurableApplicationContext applicationContext,URLClassLoader urlClassLoader){
		super(applicationContext);
		this.urlClassLoader=urlClassLoader;
	}
	
	private List<String> configLocations=new ArrayList<String>();
	
	/**
	 * scanning the jar to find all expected spring XMLs.
	 * @author J
	 */
	public static interface JARScan {
		/**
		 * to find expected spring XMLs.
		 * @param jar the context already loaded one 
		 * @param classLoader  underlying the class loader of loading the jar.
		 * @return
		 * @throws Exception
		 */
		List<URI> scan(URL jar,URLClassLoader classLoader) throws Exception;
	}

	/**
	 * defined the XML that is out of the JARs.
	 * {@link XmlBeanDefinitionReader#loadBeanDefinitions(String)}
	 * @param location
	 * @return
	 */
	public void loadBeanDefinitions(String... location){
		configLocations.addAll(Arrays.asList(location));
	}
	
	private XmlBeanDefinitionReader getXmlBeanDefinitionReader(DefaultListableBeanFactory beanFactoryIn){
		// set application class loader 
		setClassLoader(urlClassLoader);
		
		ConfigurableBeanFactory beanFactory=(ConfigurableBeanFactory) beanFactoryIn;
		//set bean loader class loader
		beanFactory.setBeanClassLoader(urlClassLoader);
		
		XmlBeanDefinitionReader xmlBeanDefinitionReader=new XmlBeanDefinitionReader((BeanDefinitionRegistry)beanFactory);
		
		//set bean loader class loader
		xmlBeanDefinitionReader.setBeanClassLoader(urlClassLoader);
		
		FileSystemResourceLoader resourceLoader=new FileSystemResourceLoader();
		resourceLoader.setClassLoader(urlClassLoader);
		xmlBeanDefinitionReader.setResourceLoader(resourceLoader);
		
		return xmlBeanDefinitionReader;
		
	}
	
	private void loadSpringXmlLocationFromJar() throws Exception{
		URL[] urls = urlClassLoader.getURLs();
		if(JCollectionUtils.hasInArray(urls)){
			for (int i = 0; i < urls.length; i++) {
				URL url=urls[i];
				LOGGER.info("load spring context from : "+url.toString());
				List<URI> uris=null;
				if(jarScan==null){
					JJARResourceURIScanner jarResourceScan=new JJARResourceURIScanner(url.toURI());
					jarResourceScan.setClassLoader(urlClassLoader);
					jarResourceScan.setIncludeExpression("spring.*[.]xml");
					uris=jarResourceScan.scan();
				}
				else{
					uris=jarScan.scan(url, urlClassLoader);	
				}
				List<String> uriString=new ArrayList<String>();
				if(JCollectionUtils.hasInCollect(uris)){
					for(int j=0;j<uris.size();j++){
						uriString.add(uris.get(j).toString());
					}
				}
				configLocations.addAll(uriString);
			}
		}
	}
	
	@Override
	protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException, IOException {
		// Create a new XmlBeanDefinitionReader for the given BeanFactory.
		XmlBeanDefinitionReader beanDefinitionReader = getXmlBeanDefinitionReader(beanFactory);
		// Configure the bean definition reader with this context's
		beanDefinitionReader.setEnvironment(this.getEnvironment());
		// Allow a subclass to provide custom initialization of the reader,
		// then proceed with actually loading the bean definitions.
		initBeanDefinitionReader(beanDefinitionReader);
		loadBeanDefinitions(beanDefinitionReader);
	}
	
	
	@Override
	public void refresh() throws BeansException, IllegalStateException {
		try {
			LOGGER.info("ready to refresh application context."); 
			loadSpringXmlLocationFromJar();
			String[] locations=new String[configLocations.size()];
			setConfigLocations(configLocations.toArray(locations));
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		super.refresh();
	}

	public ComponentVersionApplication getComponentVersionApplication() {
		return componentVersionApplication;
	}

	public void setComponentVersionApplication(
			ComponentVersionApplication componentVersionApplication) {
		this.componentVersionApplication = (DynamicComponentVersionApplication) componentVersionApplication;
	}
	
	
	/**
	 * OVERRIDE the super method to prevent the event propagate, promise any jar should not affect the
	 * platform environment.
	 */
	@Override
	public void publishEvent(ApplicationEvent event) {
		LOGGER.info("ignore the propagate refresh event");
	}
	
	public void putMappingMeta(MappingMeta mappingMeta){
		mappingMetas.put(mappingMeta.getPath(), mappingMeta);
	}
	
	public Map<String, MappingMeta> getMappingMetas() {
		return Collections.unmodifiableMap(mappingMetas);
	}

}
