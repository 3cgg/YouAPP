package j.jave.framework.temp.test.springload;
import j.jave.framework.commons.support._resource.JJARResourceURIScan;
import j.jave.framework.components.core.SpringDynamicJARApplicationCotext;
import j.jave.framework.components.core.SpringXMLLocationLoader;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringDMLoad2 {

	public static void main(String[] args) throws Exception {
		
		ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring-context.xml");
		String jarFile="D:\\java_\\JFramework1.1/trunk/jframework-components-runtime-load\\target\\jframework-components-runtime-load-1.0.jar";
		URL[] urls=new URL[]{new File(jarFile).toURI().toURL()}; 
		
		URLClassLoader urlClassLoader=new URLClassLoader(urls,Thread.currentThread().getContextClassLoader());;
		Enumeration<URL> enumeration=urlClassLoader.getResources("j/jave/framework/components/runtimeload");
		System.out.println(enumeration);
		Class<?> clazz=urlClassLoader.loadClass("j.jave.framework.components.runtimeload.springxml.RuntimeLoadSpringXMLLoader");
		
		URL url=urlClassLoader.getResource("config/spring-xml/spring-context.xml");
		System.out.println(url);
		SpringXMLLocationLoader xmlLocationLoader=(SpringXMLLocationLoader) clazz.newInstance();
		List<String> xmls=xmlLocationLoader.getSpringXmlLocation();
		File file=new File(xmls.get(0));
		System.out.println(file.exists());
		
		JJARResourceURIScan resourceScan=new JJARResourceURIScan(urls[0].toURI());
		resourceScan.setClassLoader(urlClassLoader);
		resourceScan.setRelativePath("spring/",null,null);
		List<URI> uris= resourceScan.scan();
		System.out.println(uris.size());
		
		urlClassLoader.close();
		SpringDynamicJARApplicationCotext dynamicXMLApplicationCotext=
				new SpringDynamicJARApplicationCotext(applicationContext, urls);
//		dynamicXMLApplicationCotext.loadBeanDefinitions("file:/d:/test/spring-context.xml");
//		dynamicXMLApplicationCotext.loadBeanDefinitions(xmls.get(0));
		
//		JResourceURIScan scan=new JFileSystemResourceURIScan(new JFile(new File("d:/test/spring-context.xml")));
//		dynamicXMLApplicationCotext.setResourceScan(scan);
		System.out.println("load count: ~");
		
		Resources.setCustomeClassLoader(jarFile, dynamicXMLApplicationCotext.getUrlClassLoader());
		
		dynamicXMLApplicationCotext.refresh();
//		Object mapperScanner=dynamicXMLApplicationCotext.getBean("runtimeLoadMapperScanner");
//		System.out.println(mapperScanner);
		Object runtimeloadService=dynamicXMLApplicationCotext.getBean("runtimeloadService.transation");
		
		System.out.println(runtimeloadService);
		
		Object login =dynamicXMLApplicationCotext.getBean("login.loginaction");
		
		System.out.println(login);
		
		
		applicationContext.close();
		dynamicXMLApplicationCotext.close();
	}
	
}
