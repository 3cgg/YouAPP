package j.jave.platform.sps.core.servicehub;

import j.jave.kernal.jave.service.JService;

import org.springframework.context.ApplicationContext;

public interface SpringApplicationServiceGetService extends JService{

	public JService getService(ApplicationContext applicationContext, Class<?> clazz,String beanName);
	
}
