package com.youappcorp.template.ftl;

public class DefaultInternalConfig implements InternalConfig{

	private String modelPackage;
	
	public DefaultInternalConfig(String modelPath) {
		String split="/src/main/java/";
		modelPackage=modelPath.split(split)[1].replace('/', '.');
	}
	
	@Override
	public String basePackage() {
		return modelPackage.substring(0, modelPackage.lastIndexOf('.'));
	}

	@Override
	public String servicePackage() {
		return basePackage()+".service";
	}

	@Override
	public String repoPackage() {
		return basePackage()+".jpa";
	}

	@Override
	public String voPackage() {
		return basePackage()+".vo";
	}

	@Override
	public String controllerPackage() {
		return basePackage()+".controller";
	}

	@Override
	public String modelPackage() {
		return modelPackage;
	}

}
