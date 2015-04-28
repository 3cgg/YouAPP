package j.jave.framework.core;

import junit.framework.TestCase;

public class StandaloneTest extends TestCase implements Spring{
	
	static{
		System.out.println("StandaloneTest loading ...  ");
		applicationContext.refresh();
		System.out.println("StandaloneTest loading end ...  ");
	}
	
	protected   <T> T getBean(String beanName,Class<T> clazz){
		return applicationContext.getBean(beanName,clazz);
	}
	
	protected  Object getBean(String beanName){
		return applicationContext.getBean(beanName);
	}

}
