/**
 * 
 */
package com.youappcorp.project.usermanager.model;


/**
 * @author J
 */
public class RoleRecord{
	
	private String id;
	
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
