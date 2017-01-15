package me.bunny.app._c._web.web.youappmvc.container;

import org.springframework.context.ApplicationContext;

import me.bunny.app._c.data.web.mapping.MappingMeta;

/**
 * the implementation prefixes the test/ the target bean name.
 * i.e. if the bean name is "usercontroller" ,then the test bean name is "test/usercontroller"
 * @author J
 *
 */
public class SpringBeanControllerObjectGetter implements ControllerObjectGetter {

	private ApplicationContext applicationContext;
	
	public SpringBeanControllerObjectGetter(ApplicationContext applicationContext) {
		this.applicationContext=applicationContext;
	}
	
	@Override
	public Object getObjet(MappingMeta mappingMeta) throws Exception {
		String controllerBeanName=mappingMeta.getControllerName();
		String testControllerBeanName="test/"+controllerBeanName;
		return applicationContext.getBean(testControllerBeanName);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
