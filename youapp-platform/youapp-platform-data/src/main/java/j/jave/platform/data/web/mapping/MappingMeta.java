/**
 * 
 */
package j.jave.platform.data.web.mapping;

import j.jave.kernal.jave.support._package.JDefaultMethodMeta;
import j.jave.platform.data.common.MethodParamMeta;

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
	
}
