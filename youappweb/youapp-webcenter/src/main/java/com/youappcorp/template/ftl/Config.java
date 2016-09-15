package com.youappcorp.template.ftl;

public class Config {

	private String modelPath;
	
	private Class<? extends InternalConfigStrategy> internalConfigStrategyClass
	=DefaultInternalConfigStrategy.class;

	public String getModelPath() {
		return modelPath;
	}

	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}

	public Class<? extends InternalConfigStrategy> getInternalConfigStrategyClass() {
		return internalConfigStrategyClass;
	}

	public void setInternalConfigStrategyClass(
			Class<? extends InternalConfigStrategy> internalConfigStrategyClass) {
		this.internalConfigStrategyClass = internalConfigStrategyClass;
	}
	
}
