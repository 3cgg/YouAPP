package j.jave.kernal.jave.support._package;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class JDefaultMethodMeta implements JMethodMeta {

	private String methodName;
	
	private JDefaultParamMeta[] paramMetas;
	
	private Annotation[] annotations;
	
	private int access;
	
	/**
	 * the scanning class from the method is hit ,  not declared class of the method
	 */
	private Class<?> clazz;
	
	private Method method;

	public Annotation[] getAnnotations() {
		return annotations;
	}

	public void setAnnotations(Annotation[] annotations) {
		this.annotations = annotations;
	}

	public int getAccess() {
		return access;
	}

	public void setAccess(int access) {
		this.access = access;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public JDefaultParamMeta[] getParamMetas() {
		return paramMetas;
	}

	public void setParamMetas(JDefaultParamMeta[] paramMetas) {
		this.paramMetas = paramMetas;
	}

	/**
	 * the scanning class from the field is hit ,  not declared class of the field
	 * @return
	 */
	public Class<?> getClazz() {
		return clazz;
	}

	/**
	 * @param clazz the scanning class from the field is hit ,  not declared class of the field
	 */
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}
	
}
