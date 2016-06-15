package j.jave.platform.webcomp.web.youappmvc.container;

import j.jave.platform.data.web.mapping.MappingMeta;

import org.springframework.context.ApplicationContext;

/**
 * the implementation prefixes the test/ the target bean name.
 * i.e. if the bean name is "usercontroller" ,then the test bean name is "test/usercontroller"
 * @author J
 *
 */
public class SpringBeanControllerObjectParser implements ControllerObjectParser {

	private ApplicationContext applicationContext;
	
	public SpringBeanControllerObjectParser(ApplicationContext applicationContext) {
		this.applicationContext=applicationContext;
	}
	
	@Override
	public Object parse(MappingMeta mappingMeta) throws Exception {
		String controllerBeanName=mappingMeta.getControllerName();
		String testControllerBeanName="test/"+controllerBeanName;
		return applicationContext.getBean(testControllerBeanName);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
