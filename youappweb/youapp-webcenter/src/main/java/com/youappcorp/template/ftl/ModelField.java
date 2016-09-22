package com.youappcorp.template.ftl;

import j.jave.kernal.jave.model.JModel;

import java.lang.reflect.Field;

public class ModelField implements JModel{

	private String property;
	
	private String getterMethodName;
	
	private String setterMethodName;

	private String column;
	
	private Field field;
	
	/**
	 * {@link KeyNames#FIELD_TYPE_NUMERIC} or {@link KeyNames#FIELD_TYPE_STRING} or 
	 * {@link KeyNames#FIELD_TYPE_DATE} ...   ;
	 */
	private String fieldType;
	
	private ModelField source;
	
	/**
	 * {@link KeyNames#SOURCE_TYPE_APPEND} or 
	 * {@link KeyNames#SOURCE_TYPE_CLASS}  ...
	 */
	private String sourceType;
	
	/**
	 * exists when {@link #sourceType} is {@link KeyNames#SOURCE_TYPE_APPEND} ,
	 * like someTimeStart is related to someTimeEnd
	 */
	private String relatedProperty;
	
	
	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getGetterMethodName() {
		return getterMethodName;
	}

	public void setGetterMethodName(String getterMethodName) {
		this.getterMethodName = getterMethodName;
	}

	public String getSetterMethodName() {
		return setterMethodName;
	}

	public void setSetterMethodName(String setterMethodName) {
		this.setterMethodName = setterMethodName;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getSourceType() {
		return sourceType;
	}

	/**
	 * {@link KeyNames#SOURCE_TYPE_APPEND} or 
	 * {@link KeyNames#SOURCE_TYPE_CLASS}  ...
	 * @param sourceType
	 */
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public ModelField getSource() {
		return source;
	}

	public void setSource(ModelField source) {
		this.source = source;
	}

	/**
	 * exists when {@link #sourceType} is {@link KeyNames#SOURCE_TYPE_APPEND} ,
	 * like someTimeStart is related to someTimeEnd
	 */
	public String getRelatedProperty() {
		return relatedProperty;
	}

	/**
	 * exists when {@link #sourceType} is {@link KeyNames#SOURCE_TYPE_APPEND} ,
	 * like someTimeStart is related to someTimeEnd
	 */
	public void setRelatedProperty(String relatedProperty) {
		this.relatedProperty = relatedProperty;
	}
	
}
