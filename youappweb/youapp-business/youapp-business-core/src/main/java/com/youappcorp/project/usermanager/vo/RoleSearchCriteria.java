/**
 * 
 */
package com.youappcorp.project.usermanager.vo;

import j.jave.platform.data.web.model.BaseCriteria;

/**
 * @author J
 */
public class RoleSearchCriteria extends BaseCriteria{
	
	private String roleCode;
	
	private String roleName;
	
	private String description;

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
