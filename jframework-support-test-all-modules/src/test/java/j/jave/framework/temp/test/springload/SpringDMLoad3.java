package j.jave.framework.temp.test.springload;
import j.jave.framework.components.core.SpringDynamicJARApplicationCotext;

import java.io.File;
import java.net.URL;

import org.apache.ibatis.io.Resources;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringDMLoad3 {

	public static void main(String[] args) throws Exception {
		
		ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring-context.xml");
		String jarFile="D:\\java_\\JFramework1.1/trunk/jframework-components-runtime-load\\target\\jframework-components-runtime-load-1.0.jar";
		URL[] urls=new URL[]{new File(jarFile).toURI().toURL()}; 
		SpringDynamicJARApplicationCotext dynamicXMLApplicationCotext=
				new SpringDynamicJARApplicationCotext(applicationContext, urls);
		
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
