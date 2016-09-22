package com.youappcorp.template.ftl;

import java.util.List;


public class UIListModel extends BaseTemplateUIModel{
	
	private List<UIListCriteriaField> criteriaFields;
	
	private List<UIListTableField> tableFields;
	
	private boolean checkbox;

	public List<UIListCriteriaField> getCriteriaFields() {
		return criteriaFields;
	}

	public void setCriteriaFields(List<UIListCriteriaField> criteriaFields) {
		this.criteriaFields = criteriaFields;
	}

	public List<UIListTableField> getTableFields() {
		return tableFields;
	}

	public void setTableFields(List<UIListTableField> tableFields) {
		this.tableFields = tableFields;
	}

	public boolean isCheckbox() {
		return checkbox;
	}

	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
	}
	
}
