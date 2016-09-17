package com.youappcorp.template.ftl;

public class Config {

	/**
	 *set the directory that include some models or directly certain file of model.
	 */
	private String modelPath;
	
	/**
	 * set the module name
	 */
	private String moduleName;
	
	/**
	 * how to configure some settings 
	 */
	private Class<? extends InternalConfigStrategy> internalConfigStrategyClass
	=DefaultInternalConfigStrategy.class;

	/**
	 * how to parser model fields ( properties )
	 */
	private Class<? extends ModelFieldParser> modelFieldParserClass=EntityModelFieldParser.class;
	
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

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Class<? extends ModelFieldParser> getModelFieldParserClass() {
		return modelFieldParserClass;
	}

	public void setModelFieldParserClass(
			Class<? extends ModelFieldParser> modelFieldParserClass) {
		this.modelFieldParserClass = modelFieldParserClass;
	}
	
}
