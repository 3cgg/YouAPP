/**
 * 
 */
package j.jave.platform.standalone.server.controller;


/**
 * @author J
 */
public class MappingMeta {
	
	/**
	 * name of {@link JControllerService}
	 */
	private String controllerName;
	
	/**
	 * method name. 
	 */
	private String methodName;
	
	/**
	 * URL path. consist of name of {@link JControllerService} and method name,   like as '/login.loginaction/index' .
	 * @see  {@link JControllerService}
	 */
	private String  path;

	/**
	 * class , restricted by {@link JControllerService}
	 */
	private Class<?> clazz;
	
	private MethodParamMeta[] methodParams;
	
	private JControllerServiceFactory controllerServiceFactory;

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

	public JControllerServiceFactory getControllerServiceFactory() {
		return controllerServiceFactory;
	}

	public void setControllerServiceFactory(
			JControllerServiceFactory controllerServiceFactory) {
		this.controllerServiceFactory = controllerServiceFactory;
	}

}
