package com.youappcorp.template.ftl.ui.add;

import java.util.List;

import com.youappcorp.template.ftl.ui.UITemplateUIModel;


public class UIAddModel extends UITemplateUIModel{
	
	private List<UIAddField> addFields;

	public List<UIAddField> getAddFields() {
		return addFields;
	}

	public void setAddFields(List<UIAddField> addFields) {
		this.addFields = addFields;
	}

}
