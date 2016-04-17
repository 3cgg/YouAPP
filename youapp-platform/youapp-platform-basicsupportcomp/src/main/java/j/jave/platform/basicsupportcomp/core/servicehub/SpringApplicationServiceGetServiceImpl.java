package j.jave.platform.basicsupportcomp.core.servicehub;

import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.utils.JStringUtils;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service(value="GET-SERVICE-INS-FROM-APPLICATION-AOP")
public class SpringApplicationServiceGetServiceImpl
implements SpringApplicationServiceGetService {
	
	@Override
	public JService getService(ApplicationContext applicationContext,Class<?> clazz,String beanName) {
		
		if(JStringUtils.isNotNullOrEmpty(beanName)){
			return (JService) applicationContext.getBean(beanName);
		}
		
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
