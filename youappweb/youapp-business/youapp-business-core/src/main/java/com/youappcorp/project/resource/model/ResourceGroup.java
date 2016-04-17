package com.youappcorp.project.resource.model;

import j.jave.kernal.jave.model.support.JColumn;
import j.jave.kernal.jave.model.support.JSQLType;
import j.jave.kernal.jave.model.support.JTable;
import j.jave.platform.basicwebcomp.spirngjpa.JJpaBaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.youappcorp.project.usermanager.model.Group;

@JTable(name="RESOURCES_GROUPS")
@Table(name="RESOURCES_GROUPS")
@Entity
public class ResourceGroup extends JJpaBaseModel {
	
	/**
	 * ID reference to RESOURCE table. 
	 */
	@JColumn(name="RESOURCEID",type=JSQLType.VARCHAR,length=32)
	private String resourceId;

	/**
	 * GRPUP ID 
	 */
	@JColumn(name="GROUPID",type=JSQLType.VARCHAR,length=32)
	private String groupId;
	
	/**
	 * description. 
	 */
	@JColumn(name="DESCRIPTION",type=JSQLType.VARCHAR,length=256)
	private String description;

	/**
	 * Y OR N
	 */
	@JColumn(name="ENABLE",type=JSQLType.VARCHAR,length=1)
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

	@Column(name="RESOURCEID")
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
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

	@Column(name="ENABLE")
	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	
	
	
}
