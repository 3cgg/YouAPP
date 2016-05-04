package j.jave.platform.basicwebcomp.web.youappmvc.container;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.platform.basicsupportcomp.core.container.MappingMeta;

/**
 * the implementation prefixes the test/ the target class name.
 * i.e. the final path of "j.jave.platform.basicwebcomp.web.youappmvc.container.DefaultControllerObjectParser"
 * is "test.j.jave.platform.basicwebcomp.web.youappmvc.container.DefaultControllerObjectParser"
 * @author J
 *
 */
public class SpringBeanControllerObjectParser implements ControllerObjectParser {

	private HttpInvokeContainerDelegateService requestInvokeContainerDelegate=
			JServiceHubDelegate.get().getService(this,HttpInvokeContainerDelegateService.class);
	
	@Override
	public Object parse(MappingMeta mappingMeta) throws Exception {
		Class<?> controllerClass=mappingMeta.getClazz();
		String controllerClassName=controllerClass.getName();
		String testControllerClassName="test."+controllerClassName;
		
		
		return JClassUtils.newObject(JClassUtils.load(testControllerClassName,controllerClass.getClassLoader()));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
