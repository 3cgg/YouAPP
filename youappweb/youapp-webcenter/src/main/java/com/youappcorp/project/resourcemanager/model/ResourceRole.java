package com.youappcorp.project.resourcemanager.model;

import j.jave.platform.jpa.springjpa.JJpaBaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.youappcorp.project.usermanager.model.Role;

@Table(name="RESOURCES_ROLES")
@Entity
public class ResourceRole extends JJpaBaseModel {
	
	/**
	 * ID reference to RESOURCE table. 
	 */
	private String resourceId;

	/**
	 * ROLE ID 
	 */
	private String roleId;
	
	/**
	 * Y OR N
	 */
	private String enable;
	
	/**
	 * description. 
	 */
	private String description;
	
	private Role role;
	
	private Resource resource;

	@Transient
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Transient
	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	@Column(name="RESOURCE_ID")
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	@Column(name="ROLE_ID")
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="ENABLE")
	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}
	
}
