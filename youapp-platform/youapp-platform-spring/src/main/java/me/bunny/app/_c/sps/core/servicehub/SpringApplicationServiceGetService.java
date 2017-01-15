package me.bunny.app._c.sps.core.servicehub;

import org.springframework.context.ApplicationContext;

import me.bunny.kernel._c.service.JService;

public interface SpringApplicationServiceGetService extends JService{

	public JService getService(ApplicationContext applicationContext, Class<?> clazz,String beanName);
	
}
