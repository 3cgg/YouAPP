package com.youappcorp.template.ftl;

import java.util.HashMap;
import java.util.Map;

public class Config {

	/**
	 *set the directory that include some models or directly certain file of model.
	 */
	private String modelPath;
	
	/**
	 * set the module name
	 */
	private String moduleName;
	
	private String uiRelativePath;
	
	private Map<String,FieldConfig> fieldConfigs=new HashMap<String, Config.FieldConfig>();
	
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

	public String getUiRelativePath() {
		return uiRelativePath;
	}

	public void setUiRelativePath(String uiRelativePath) {
		this.uiRelativePath = uiRelativePath;
	}
	
	
	public void addUIField(FieldConfig fieldConfig){
		this.fieldConfigs.put(fieldConfig.getProperty(), fieldConfig);
	}
	
	public FieldConfig getUIField(String property){
		return fieldConfigs.get(property);
	}
	
	public static class FieldConfig{
		
		private String property;
		
		private String label;
		
		/**
		 * {@link KeyNames#FIELD_TYPE_NUMERIC} or {@link KeyNames#FIELD_TYPE_STRING} or 
		 * {@link KeyNames#FIELD_TYPE_DATE} ...   ;
		 */
		private String fieldType;


		public FieldConfig() {
		}
		
		public FieldConfig(String property, String label) {
			this.property = property;
			this.label = label;
		}
		

		public FieldConfig(String property, String label, String fieldType) {
			this.property = property;
			this.label = label;
			this.fieldType = fieldType;
		}

		public String getProperty() {
			return property;
		}

		public void setProperty(String property) {
			this.property = property;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public String getFieldType() {
			return fieldType;
		}

		public void setFieldType(String fieldType) {
			this.fieldType = fieldType;
		}
	}
	
}
