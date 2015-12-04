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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Iterator;
import java.util.Set;

public class TestJARLoad {

	private void loadTestLoginService() throws Exception{
		String jarFile="D:/test/a.jar";
		registerServiceExternalJAR(jarFile);
		
		TestLoginService testLoginService=JServiceHubDelegate.get().getService(this, TestLoginService.class);
		testLoginService.login("nm", "pwd");
	}


	private void registerServiceExternalJAR(String jarFile) throws MalformedURLException {
		ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
		URL[] urls=new URL[]{new File(jarFile).toURI().toURL()}; 
		URLClassLoader urlClassLoader=new URLClassLoader(urls,classLoader);
		
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
	
	
	private void loadTestRecordService() throws Exception{
		String jarFile="D:/test/b.jar";
		registerServiceExternalJAR(jarFile);
		TestRecordService testRecordService=JServiceHubDelegate.get().getService(this, TestRecordService.class);
		testRecordService.record(" i am "+testRecordService.toString());
	}
	
	
	public static void main(String[] args) throws Exception {
		
		TestJARLoad testJARLoad=new TestJARLoad();
		
		
		testJARLoad.loadTestRecordService();
		System.out.println("================");
		testJARLoad.loadTestLoginService();
		
		
	}
}
