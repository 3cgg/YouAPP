package com.youappcorp.template.ftl;

import j.jave.kernal.jave.model.JModel;

import java.lang.reflect.Field;

public class ModelField implements JModel{

	private String property;
	
	private String getterMethodName;
	
	private String setterMethodName;

	private String column;
	
	private Field field;
	
	private String fieldType;
	
	private ModelField source;
	
	private String sourceType;
	
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

}
