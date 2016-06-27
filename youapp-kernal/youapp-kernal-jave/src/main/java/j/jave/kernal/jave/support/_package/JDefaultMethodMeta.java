package j.jave.kernal.jave.support._package;

import j.jave.kernal.jave.model.JModel;

import java.lang.annotation.Annotation;

public class JDefaultMethodMeta implements JModel {

	private String methodName;
	
	private JDefaultParamMeta[] paramMetas;
	
	private Annotation[] annotations;
	
	private int access;

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
	
}
