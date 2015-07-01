package j.jave.framework.temp.test.springload;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResourceLoader;

public class SpringDMLoad {

	public static void main(String[] args) throws Exception {
		
		ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring-context.xml");
		
		
		ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
		String jarFile="D:/test/jframework-components-runtime-load-1.0.jar";
		URL[] urls=new URL[]{new File(jarFile).toURI().toURL()}; 
		URLClassLoader urlClassLoader=new URLClassLoader(urls,classLoader);
	
		ConfigurableBeanFactory beanFactory=applicationContext.getBeanFactory( );
		beanFactory.setBeanClassLoader(urlClassLoader);
		XmlBeanDefinitionReader xmlBeanDefinitionReader=new XmlBeanDefinitionReader((BeanDefinitionRegistry)beanFactory);
		xmlBeanDefinitionReader.setBeanClassLoader(urlClassLoader);
		
		FileSystemResourceLoader resourceLoader=new FileSystemResourceLoader();
		resourceLoader.setClassLoader(urlClassLoader);
		
		xmlBeanDefinitionReader.setResourceLoader(resourceLoader);
//		InputSource inputSource=new InputSource(new FileInputStream("d:/test/spring-context.xml"));
		int i =xmlBeanDefinitionReader.loadBeanDefinitions("file:/d:/test/spring-context.xml");
		System.out.println("load count: "+i);
		Object obj=applicationContext.getBean("runtimeloadService.transation");
		
		System.out.println(obj);
		
		
		applicationContext.close();
	}
	
}
