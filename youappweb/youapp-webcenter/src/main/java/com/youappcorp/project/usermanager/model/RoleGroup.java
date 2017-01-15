/**
 * 
 */
package com.youappcorp.project.usermanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import me.bunny.app._c.jpa.springjpa.JJpaBaseModel;

/**
 * @author J
 */
@Entity
@Table(name="ROLES_GROUPS")
public class RoleGroup extends JJpaBaseModel {

	private String roleId;
	
	private String groupId;
	
	private String description;

	@Column(name="ROLE_ID")
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name="GROUP_ID")
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
