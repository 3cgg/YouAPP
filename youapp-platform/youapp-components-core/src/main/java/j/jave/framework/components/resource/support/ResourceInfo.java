/**
 * 
 */
package j.jave.framework.components.resource.support;

import org.springframework.stereotype.Controller;

/**
 * @author J
 */
public class ResourceInfo {
	
	/**
	 * name of {@link Controller}
	 */
	private String controllerName;
	
	/**
	 * method name. 
	 */
	private String methodName;
	
	/**
	 * URL path. consist of name of {@link Controller} and method name,   like as '/login.loginaction/index' .
	 * @see  {@link Controller}
	 */
	private String  path;

	/**
	 * class , restricted by {@link Controller}
	 */
	private Class<?> clazz;

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
	
	
}
