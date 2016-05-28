/**
 * 
 */
package j.jave.platform.standalone.server.controller;


/**
 * @author J
 */
public class MappingMeta {
	
	/**
	 * name of {@link ControllerService}
	 */
	private String controllerName;
	
	/**
	 * method name. 
	 */
	private String methodName;
	
	/**
	 * URL path. consist of name of {@link ControllerService} and method name,   like as '/login.loginaction/index' .
	 * @see  {@link ControllerService}
	 */
	private String  path;

	/**
	 * class , restricted by {@link ControllerService}
	 */
	private Class<?> clazz;
	
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

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public MethodParamMeta[] getMethodParams() {
		return methodParams;
	}

	public void setMethodParams(MethodParamMeta[] methodParams) {
		this.methodParams = methodParams;
	}

	public ControllerServiceFactory getControllerServiceFactory() {
		return controllerServiceFactory;
	}

	public void setControllerServiceFactory(
			ControllerServiceFactory controllerServiceFactory) {
		this.controllerServiceFactory = controllerServiceFactory;
	}

}
