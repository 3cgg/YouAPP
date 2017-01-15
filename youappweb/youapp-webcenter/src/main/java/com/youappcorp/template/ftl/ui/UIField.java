package com.youappcorp.template.ftl.ui;

import com.youappcorp.template.ftl.KeyNames;
import com.youappcorp.template.ftl.ModelField;

import me.bunny.kernel._c.model.JModel;

public class UIField implements JModel {

	private String property;
	
	private String getterMethodName;
	
	private String setterMethodName;

	private String column;
	
	private String label;
	
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
	 * amount of columns , like "col-sm-5"
	 */
	private int colNum=5;
	
	/**
	 * see display:block;
	 */
	private boolean block;
	
	/**
	 * exists when {@link #sourceType} is {@link KeyNames#SOURCE_TYPE_APPEND} ,
	 * like someTimeStart is related to someTimeEnd
	 */
	private String relatedProperty;

	private UIFieldSpec fieldSpec;
	
	/**
	 * the field must not be rendered in the page view.
	 */
	private boolean virtual;
	
	private boolean hidden;

	public boolean isVirtual() {
		return virtual;
	}

	public void setVirtual(boolean virtual) {
		this.virtual = virtual;
	}
	
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

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
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

	public ModelField getSource() {
		return source;
	}

	public void setSource(ModelField source) {
		this.source = source;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public int getColNum() {
		return colNum;
	}

	/**
	 * amount of columns , like "col-sm-5"
	 */
	public void setColNum(int colNum) {
		this.colNum = colNum;
	}

	/**
	 * see display:block;
	 */
	public boolean isBlock() {
		return block;
	}

	/**
	 * see display:block;
	 */
	public void setBlock(boolean block) {
		this.block = block;
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

	public UIFieldSpec getFieldSpec() {
		return fieldSpec;
	}

	public void setFieldSpec(UIFieldSpec fieldSpec) {
		this.fieldSpec = fieldSpec;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	
}
