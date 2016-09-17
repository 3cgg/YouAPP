package com.youappcorp.template.ftl;

import java.util.List;

public class ModelModel extends BaseTemplateModel{
	
	private List<ModelField> modelFields;

	public List<ModelField> getModelFields() {
		return modelFields;
	}

	public void setModelFields(List<ModelField> modelFields) {
		this.modelFields = modelFields;
	}
	
	
}
