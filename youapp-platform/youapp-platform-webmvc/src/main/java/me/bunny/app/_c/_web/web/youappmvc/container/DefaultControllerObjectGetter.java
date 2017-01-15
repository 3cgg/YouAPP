package me.bunny.app._c._web.web.youappmvc.container;

import me.bunny.app._c.data.web.mapping.MappingMeta;
import me.bunny.kernel._c.reflect.JClassUtils;

/**
 * the implementation prefixes the test/ the target class name.
 * i.e. the final path of "j.jave.platform.basicwebcomp.web.youappmvc.container.DefaultControllerObjectParser"
 * is "test.j.jave.platform.basicwebcomp.web.youappmvc.container.DefaultControllerObjectParser"
 * @author J
 *
 */
public class DefaultControllerObjectGetter implements ControllerObjectGetter {

	@Override
	public Object getObjet(MappingMeta mappingMeta) throws Exception {
		Class<?> controllerClass=mappingMeta.getClazz();
		String controllerClassName=controllerClass.getName();
		String testControllerClassName="test."+controllerClassName;
		return JClassUtils.newObject(JClassUtils.load(testControllerClassName,controllerClass.getClassLoader()));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
