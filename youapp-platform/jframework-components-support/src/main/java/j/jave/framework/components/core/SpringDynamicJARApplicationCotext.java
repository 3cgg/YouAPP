package j.jave.framework.components.core;

import j.jave.framework.commons.logging.JLogger;
import j.jave.framework.commons.logging.JLoggerFactory;
import j.jave.framework.commons.support._resource.JJARResourceURIScan;
import j.jave.framework.commons.utils.JCollectionUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.core.io.FileSystemResourceLoader;

/**
 * default the application automatically scan all spring configuration with the file name starting with "spring" in the related JAR file to find all spring XMLs .
 * You also can provide yourself {@link JARScan} to search spring XML , not only from the JAR.
 * @author J
 * @see JARScan
 */
public class SpringDynamicJARApplicationCotext extends AbstractXmlApplicationContext {
	
	protected final JLogger LOGGER=JLoggerFactory.getLogger(getClass());
	
	private final URLClassLoader urlClassLoader;
	
	private JARScan jarScan;
	
	public void setJarScan(JARScan jarScan) {
		this.jarScan = jarScan;
	}
	
	public URLClassLoader getUrlClassLoader() {
		return urlClassLoader;
	}
			
	public SpringDynamicJARApplicationCotext(ConfigurableApplicationContext applicationContext,URL[] jarUrls){
		super(applicationContext);
		this.urlClassLoader=new URLClassLoader(jarUrls,applicationContext.getClassLoader());
	}
	
	public SpringDynamicJARApplicationCotext(ConfigurableApplicationContext applicationContext,URLClassLoader urlClassLoader){
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
					JJARResourceURIScan jarResourceScan=new JJARResourceURIScan(url.toURI());
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
	
}
