/**
 * 
 */
package com.youappcorp.project.usermanager.model;

import j.jave.kernal.jave.model.support.JColumn;
import j.jave.kernal.jave.model.support.JSQLType;
import j.jave.kernal.jave.model.support.JTable;
import j.jave.platform.basicwebcomp.spirngjpa.JJpaBaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author J
 */
@JTable(name="ROLES")
@Entity
@Table(name="ROLES")
public class Role extends JJpaBaseModel {

	@JColumn(name="ROLECODE",type=JSQLType.VARCHAR,length=32)
	private String roleCode;
	
	@JColumn(name="ROLENAME",type=JSQLType.VARCHAR,length=64)
	private String roleName;
	
	@JColumn(name="DESCRIPTION",type=JSQLType.VARCHAR,length=256)
	private String description;

	@Column(name="ROLECODE")
	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	@Column(name="ROLENAME")
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
