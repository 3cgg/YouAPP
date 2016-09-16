package com.youappcorp.template.ftl;

import j.jave.kernal.jave.model.JModel;
import j.jave.kernal.jave.utils.JStringUtils;

public class BaseTemplateModel implements JModel{

	private String classPackage;
	
	private String className;
	
	private String simpleClassName;
	
	private String variableName;

	public String getClassPackage() {
		return classPackage;
	}

	public void setClassPackage(String classPackage) {
		this.classPackage = classPackage;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSimpleClassName() {
		return simpleClassName;
	}

	public void setSimpleClassName(String simpleClassName) {
		this.simpleClassName = simpleClassName;
	}

	public String getVariableName() {
		if(JStringUtils.isNullOrEmpty(variableName)){
			variableName=TemplateUtil.variableName(getSimpleClassName());
		}
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
}
