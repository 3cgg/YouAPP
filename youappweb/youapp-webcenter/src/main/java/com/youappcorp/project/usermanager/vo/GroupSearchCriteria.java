/**
 * 
 */
package com.youappcorp.project.usermanager.vo;

import j.jave.platform.data.web.model.BaseCriteria;

/**
 * @author J
 */
public class GroupSearchCriteria extends BaseCriteria{
	
	private String groupCode;
	
	private String groupName;
	
	private String description;

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
