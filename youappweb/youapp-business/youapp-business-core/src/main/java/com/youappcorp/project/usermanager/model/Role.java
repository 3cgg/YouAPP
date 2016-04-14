/**
 * 
 */
package com.youappcorp.project.usermanager.model;

import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.model.support.JColumn;
import j.jave.kernal.jave.model.support.JSQLType;
import j.jave.kernal.jave.model.support.JTable;

/**
 * @author J
 */
@JTable(name="ROLES")
public class Role extends JBaseModel {

	@JColumn(name="ROLECODE",type=JSQLType.VARCHAR,length=32)
	private String roleCode;
	
	@JColumn(name="ROLENAME",type=JSQLType.VARCHAR,length=64)
	private String roleName;
	
	@JColumn(name="DESCRIPTION",type=JSQLType.VARCHAR,length=256)
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