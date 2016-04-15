package j.jave.platform.basicsupportcomp.core.servicehub;

import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.utils.JStringUtils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service(value="GET-SERVICE-INS-FROM-APPLICATION-AOP")
public class SpringApplicationServiceGetServiceImpl
implements SpringApplicationServiceGetService,ApplicationContextAware {
	
	private ApplicationContext applicationContext=null;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext=applicationContext;
	}
	
	@Override
	public JService getService(Class<?> clazz) {
		Service service=clazz.getAnnotation(Service.class);
		String name=clazz.getSimpleName();
		String serviceName=name.substring(0, 1).toLowerCase()
				+name.substring(1);
		if(service!=null&&JStringUtils.isNotNullOrEmpty(service.value())){
			serviceName=service.value();
		}
		
		Component component=clazz.getAnnotation(Component.class);
		if(component!=null&&JStringUtils.isNotNullOrEmpty(component.value())){
			serviceName=service.value();
		}
		return (JService) applicationContext.getBean(serviceName);
	}

}
