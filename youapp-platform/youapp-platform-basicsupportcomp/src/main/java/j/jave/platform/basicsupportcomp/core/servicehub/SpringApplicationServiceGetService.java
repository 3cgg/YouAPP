package j.jave.platform.basicsupportcomp.core.servicehub;

import org.springframework.context.ApplicationContext;

import j.jave.kernal.jave.service.JService;

public interface SpringApplicationServiceGetService extends JService{

	public JService getService(ApplicationContext applicationContext, Class<?> clazz,String beanName);
	
}
