package test.j.jave.framework.temp.test.jarload;

import j.jave.framework.commons.eventdriven.servicehub.JServiceFactoryManager;
import j.jave.framework.commons.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.framework.commons.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.framework.commons.service.JService;
import j.jave.framework.commons.support._package.JClassesResolve;
import j.jave.framework.commons.support._package.JJARDefaultScanner;
import j.jave.framework.commons.utils.JCollectionUtils;
import j.jave.framework.components.test.service.TestLoginService;
import j.jave.framework.components.test.service.TestRecordService;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Iterator;
import java.util.Set;

public class TestJARLoad1 {

	private TestLoginService loadTestLoginService() throws Exception{
		String jarFile="D:/test/a.jar";
		registerServiceExternalJAR(jarFile);
		
		TestLoginService testLoginService=JServiceHubDelegate.get().getService(this, TestLoginService.class);
		testLoginService.login("nm", "pwd");
		return testLoginService;
	}


	private void registerServiceExternalJAR(String jarFile) throws Exception {
		ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
		URL[] urls=new URL[]{new File(jarFile).toURI().toURL()}; 
		URLClassLoader urlClassLoader=new URLClassLoader(urls,classLoader);
		
		Class<?> temp= urlClassLoader.loadClass("j.jave.framework.temp.test.jarload.TestJARLoad1");
		System.out.println("load by parent classloader..."+temp);
		
		JJARDefaultScanner jarDefaultScan=new JJARDefaultScanner(new File(jarFile));
		jarDefaultScan.setExpression(new String[]{".*"});
		jarDefaultScan.setClassLoader(urlClassLoader); 
		Set<Class<?>> classes= JClassesResolve.get().getImplements(jarDefaultScan, JServiceFactorySupport.class);
		if(JCollectionUtils.hasInCollect(classes)){
			for (Iterator iterator = classes.iterator(); iterator.hasNext();) {
				Class<?> class1 = (Class<?>) iterator.next();
				JServiceFactoryManager.get().registerService((Class<? extends JServiceFactorySupport<JService>>) class1);
			}
		}
	}
	
	
	private TestRecordService loadTestRecordService() throws Exception{
		String jarFile="D:/test/b.jar";
		registerServiceExternalJAR(jarFile);
		TestRecordService testRecordService=JServiceHubDelegate.get().getService(this, TestRecordService.class);
		testRecordService.record(" i am "+testRecordService.toString());
		return testRecordService;
	}
	
	
	public static void main(String[] args) throws Exception {
		
		TestJARLoad1 testJARLoad=new TestJARLoad1();
		
		
		
		TestLoginService testLoginService1= testJARLoad.loadTestLoginService();
		TestLoginService testLoginService2=JServiceHubDelegate.get().getService(testJARLoad, TestLoginService.class);
		
		System.out.println("================");
		
		TestLoginService testLoginService3= testJARLoad.loadTestLoginService();
		TestLoginService testLoginService4=JServiceHubDelegate.get().getService(testJARLoad, TestLoginService.class);
		
		testJARLoad.loadTestRecordService();
		
		
	}
}
