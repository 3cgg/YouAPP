package j.jave.platform.sps.core.servicehub;

import j.jave.kernal.jave.utils.JStringUtils;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service(value="CHECK-SPRING-BEAN-NAME-AS-CLASS-NAME-BEAN")
public class ServiceNameSameAsClassNameCheckService
implements SpringApplicationServiceNameCheckService{

	@Override
	public boolean valid(Class<?> clazz) {
		
		if(clazz.getAnnotation(SkipServiceNameCheck.class)!=null){
			return true;
		}
		
		Service service=clazz.getAnnotation(Service.class);
		String name=clazz.getSimpleName();
		String serviceName=null;
		if(service!=null&&JStringUtils.isNotNullOrEmpty(serviceName=service.value())){
			return name.equals(serviceName);
		}
		Component component=clazz.getAnnotation(Component.class);
		if(component!=null&&JStringUtils.isNotNullOrEmpty(serviceName=component.value())){
			return name.equals(serviceName);
		}
		
		return true;
	}
	
}
