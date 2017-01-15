package com.youappcorp.project.resourcemanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.youappcorp.project.usermanager.model.Group;

import me.bunny.app._c.jpa.springjpa.JJpaBaseModel;

@Table(name="RESOURCES_GROUPS")
@Entity
public class ResourceGroup extends JJpaBaseModel {
	
	/**
	 * ID reference to RESOURCE table. 
	 */
	private String resourceId;

	/**
	 * GRPUP ID 
	 */
	private String groupId;
	
	/**
	 * description. 
	 */
	private String description;

	/**
	 * Y OR N
	 */
	private String enable;
	
	
	private Resource resource;
	
	private Group group;
	
	@Transient
	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	@Transient
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@Column(name="RESOURCE_ID")
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
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

	@Column(name="ENABLE")
	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	
	
	
}
