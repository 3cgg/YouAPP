package me.bunny.kernel._c.support._package;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class JDefaultMethodMeta implements JMethodMeta {

	private String methodName;
	
	private JDefaultParamMeta[] paramMetas=new JDefaultParamMeta[0];
	
	private Annotation[] annotations;
	
	private int access;
	
	/**
	 * the scanning class from the method is hit ,  not declared class of the method
	 */
	private Class<?> clazz;
	
	private Method method;

	
	public JDefaultMethodMeta(){}
	
	public JDefaultMethodMeta(Class<?> clazz,Method method){
		this.method=method;
		this.access=method.getModifiers();
		this.annotations=method.getAnnotations();
		this.clazz=clazz;
		this.methodName=method.getName();
		
		Parameter[] parameters=method.getParameters();
		JDefaultParamMeta[] defaultParamMetas=new JDefaultParamMeta[parameters.length];
		for(int i=0;i<parameters.length;i++){
			Parameter parameter=parameters[i];
			JDefaultParamMeta defaultParamMeta=new JDefaultParamMeta();
			defaultParamMeta.setIndex(i);
			defaultParamMeta.setName(parameter.getName());
			defaultParamMeta.setType(parameter.getType());
			defaultParamMetas[i]=defaultParamMeta;
		}
		this.paramMetas=defaultParamMetas;
	}
	
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
	 * the scanning class from the method is hit ,  not declared class of the method
	 * @return
	 */
	public Class<?> getClazz() {
		return clazz;
	}

	/**
	 * @param clazz the scanning class from the method is hit ,  not declared class of the method
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
	
	public Class<?>[] getParameterTypes(){
		Class<?>[] paramTypes=new Class<?>[paramMetas.length];
		int i=0;
		for(JDefaultParamMeta defaultParamMeta:paramMetas){
			paramTypes[i++]=defaultParamMeta.getType();
		}
		return paramTypes;
	}
	
}
