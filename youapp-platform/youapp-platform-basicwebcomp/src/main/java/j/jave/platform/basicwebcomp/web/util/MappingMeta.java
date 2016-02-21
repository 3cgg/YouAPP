/**
 * 
 */
package j.jave.platform.basicwebcomp.web.util;

import org.springframework.stereotype.Controller;

/**
 * @author J
 */
public class MappingMeta {
	
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
	
	private Class<?>[] methodParams;

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

	public Class<?>[] getMethodParams() {
		return methodParams;
	}

	public void setMethodParams(Class<?>[] methodParams) {
		this.methodParams = methodParams;
	}
	
	
}
