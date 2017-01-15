/**
 * 
 */
package me.bunny.modular._p.streaming.netty.controller;

import me.bunny.kernel._c.support._package.JDefaultMethodMeta;


/**
 * @author J
 */
public class MappingMeta extends JDefaultMethodMeta{
	
	/**
	 * name of {@link Controller}
	 */
	private String controllerName;
	
	/**
	 * URL path. consist of name of {@link Controller} and method name,   like as '/login.loginaction/index' .
	 * @see  {@link Controller}
	 */
	private String  path;
	
	private MethodParamMeta[] methodParams;
	
	private ControllerServiceFactory controllerServiceFactory;

	public Class<?>[] getMethodParamClasses(){
		Class<?>[] clazzs=new Class<?>[methodParams.length];
		for(int i=0;i<methodParams.length;i++){
			clazzs[i]=methodParams[i].getType();
		}
		return clazzs;
	}
	
	public String getControllerName() {
		return controllerName;
	}

	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public MethodParamMeta[] getMethodParams() {
		return methodParams;
	}

	public void setMethodParams(MethodParamMeta[] methodParams) {
		this.methodParams = methodParams;
		super.setParamMetas(methodParams);
	}

	public ControllerServiceFactory getControllerServiceFactory() {
		return controllerServiceFactory;
	}

	public void setControllerServiceFactory(
			ControllerServiceFactory controllerServiceFactory) {
		this.controllerServiceFactory = controllerServiceFactory;
	}

}
