package com.youappcorp.template.ftl.ui.edit;

import java.util.List;

import com.youappcorp.template.ftl.ui.UITemplateUIModel;


public class UIEditModel extends UITemplateUIModel{
	
	private List<UIEditField> editFields;

	public List<UIEditField> getEditFields() {
		return editFields;
	}

	public void setEditFields(List<UIEditField> editFields) {
		this.editFields = editFields;
	}

	
	

}
