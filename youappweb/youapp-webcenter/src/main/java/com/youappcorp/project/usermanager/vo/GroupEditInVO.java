/**
 * 
 */
package com.youappcorp.project.usermanager.vo;

import me.bunny.app._c.data.web.model.JInputModel;

/**
 * @author J
 */
public class GroupEditInVO implements JInputModel {
	
	private String id;
	
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
