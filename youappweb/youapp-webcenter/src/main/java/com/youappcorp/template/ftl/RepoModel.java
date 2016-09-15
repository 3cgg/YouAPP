package com.youappcorp.template.ftl;

public class RepoModel {

	private String repoPackage;
	
	private String modelFullClassName;
	
	private String modelSimpleClassName;

	public String getRepoPackage() {
		return repoPackage;
	}

	public void setRepoPackage(String repoPackage) {
		this.repoPackage = repoPackage;
	}

	public String getModelFullClassName() {
		return modelFullClassName;
	}

	public void setModelFullClassName(String modelFullClassName) {
		this.modelFullClassName = modelFullClassName;
	}

	public String getModelSimpleClassName() {
		return modelSimpleClassName;
	}

	public void setModelSimpleClassName(String modelSimpleClassName) {
		this.modelSimpleClassName = modelSimpleClassName;
	}
}
