package com.youappcorp.template.ftl.ui.view;

import java.util.List;

import com.youappcorp.template.ftl.ui.UITemplateUIModel;


public class UIViewModel extends UITemplateUIModel{
	
	private List<UIViewField> viewFields;

	public List<UIViewField> getViewFields() {
		return viewFields;
	}

	public void setViewFields(List<UIViewField> viewFields) {
		this.viewFields = viewFields;
	}


}
