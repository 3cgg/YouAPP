package com.youappcorp.template.ftl;

public class ServiceModel {

	private String servicePackage;
	
	private String serviceClassName;
	
	private String serviceSimpleClassName;
	
	private String variableName;
	
	private String pageMethodName;
	
	private String saveMethodName;
	
	private String updateMethodName;
	
	private String deleteMethodName;
	
	private String deleteByIdMethodName;
	
	private String getMethodName;

	public String getServicePackage() {
		return servicePackage;
	}

	public void setServicePackage(String servicePackage) {
		this.servicePackage = servicePackage;
	}

	public String getServiceClassName() {
		return serviceClassName;
	}

	public void setServiceClassName(String serviceClassName) {
		this.serviceClassName = serviceClassName;
	}

	public String getServiceSimpleClassName() {
		return serviceSimpleClassName;
	}

	public void setServiceSimpleClassName(String serviceSimpleClassName) {
		this.serviceSimpleClassName = serviceSimpleClassName;
	}

	public String getPageMethodName() {
		return pageMethodName;
	}

	public void setPageMethodName(String pageMethodName) {
		this.pageMethodName = pageMethodName;
	}

	public String getSaveMethodName() {
		return saveMethodName;
	}

	public void setSaveMethodName(String saveMethodName) {
		this.saveMethodName = saveMethodName;
	}

	public String getUpdateMethodName() {
		return updateMethodName;
	}

	public void setUpdateMethodName(String updateMethodName) {
		this.updateMethodName = updateMethodName;
	}

	public String getDeleteMethodName() {
		return deleteMethodName;
	}

	public void setDeleteMethodName(String deleteMethodName) {
		this.deleteMethodName = deleteMethodName;
	}

	public String getGetMethodName() {
		return getMethodName;
	}

	public void setGetMethodName(String getMethodName) {
		this.getMethodName = getMethodName;
	}

	public String getDeleteByIdMethodName() {
		return deleteByIdMethodName;
	}

	public void setDeleteByIdMethodName(String deleteByIdMethodName) {
		this.deleteByIdMethodName = deleteByIdMethodName;
	}

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	
	
}
