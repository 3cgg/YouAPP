package j.jave.platform.sps.core.servicehub;

import org.springframework.context.ApplicationContext;

import me.bunny.kernel.jave.service.JService;

public interface SpringApplicationServiceGetService extends JService{

	public JService getService(ApplicationContext applicationContext, Class<?> clazz,String beanName);
	
}
