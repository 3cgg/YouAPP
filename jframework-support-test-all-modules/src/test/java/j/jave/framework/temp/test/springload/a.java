package j.jave.framework.temp.test.springload;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResourceLoader;

public class a extends ClassPathXmlApplicationContext {
	
	private ClassLoader classLoader;
	
	private ConfigurableListableBeanFactory beanFactory;
	
	private ConfigurableApplicationContext applicationContext;
	
	private URL[] jarUrls;
	
	public a(ConfigurableApplicationContext applicationContext,
			ClassLoader classLoader,URL[] jarUrls){
		super(applicationContext);
		this.classLoader=classLoader;
		this.jarUrls=jarUrls;
		this.applicationContext=applicationContext;
	}

	/**
	 * {@link XmlBeanDefinitionReader#loadBeanDefinitions(String)}
	 * @param location
	 * @return
	 */
	public void loadBeanDefinitions(String... location){
		setConfigLocations(location);
	}
	
	private XmlBeanDefinitionReader getXmlBeanDefinitionReader(DefaultListableBeanFactory beanFactoryIn){

		ClassLoader classLoader=this.classLoader==null?Thread.currentThread().getContextClassLoader():this.classLoader;
		URLClassLoader urlClassLoader=new URLClassLoader(jarUrls,classLoader);
//		Resources.setCustomeClassLoader(urlClassLoader);
//		if(this.beanFactory==null){
//			this.beanFactory=new DefaultListableBeanFactory(applicationContext.getBeanFactory());
//		}
		setClassLoader(urlClassLoader);
		
		ConfigurableBeanFactory beanFactory=(ConfigurableBeanFactory) beanFactoryIn;
		beanFactory.setBeanClassLoader(urlClassLoader);
		XmlBeanDefinitionReader xmlBeanDefinitionReader=new XmlBeanDefinitionReader((BeanDefinitionRegistry)beanFactory);
		xmlBeanDefinitionReader.setBeanClassLoader(urlClassLoader);
		FileSystemResourceLoader resourceLoader=new FileSystemResourceLoader();
		resourceLoader.setClassLoader(urlClassLoader);
		xmlBeanDefinitionReader.setResourceLoader(resourceLoader);
		return xmlBeanDefinitionReader;
		
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
	
}
