/**
 * 
 */
package com.youappcorp.project.usermanager.model;

import j.jave.kernal.jave.model.support.JColumn;
import j.jave.kernal.jave.model.support.JSQLType;
import j.jave.kernal.jave.model.support.JTable;
import j.jave.platform.jpa.springjpa.JJpaBaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author J
 */
@JTable(name="ROLES_GROUPS")
@Entity
@Table(name="ROLES_GROUPS")
public class RoleGroup extends JJpaBaseModel {

	@JColumn(name="ROLEID",type=JSQLType.VARCHAR,length=32)
	private String roleId;
	
	@JColumn(name="GROUPID",type=JSQLType.VARCHAR,length=32)
	private String groupId;
	
	@JColumn(name="DESCRIPTION",type=JSQLType.VARCHAR,length=256)
	private String description;
	
	private Role role;
	
	private Group group;

	@Transient
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Transient
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@Column(name="ROLEID")
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name="GROUPID")
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
