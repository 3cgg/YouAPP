package com.youappcorp.project.resource.model;

import j.jave.kernal.jave.model.support.JColumn;
import j.jave.kernal.jave.model.support.JSQLType;
import j.jave.kernal.jave.model.support.JTable;
import j.jave.platform.basicwebcomp.spirngjpa.JJpaBaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@JTable(name="RESOURCES_EXTEND")
@Table(name="RESOURCES_EXTEND")
@Entity
public class ResourceExtend extends JJpaBaseModel{
	
	/**
	 * ID reference to RESOURCE table. 
	 */
	@JColumn(name="RESOURCEID",type=JSQLType.VARCHAR,length=32)
	private String resourceId;
	
	/**
	 * URL 
	 */
	@JColumn(name="URL",type=JSQLType.VARCHAR,length=128)
	private String url;
	
	/**
	 * service method.  i.e.   loginService.login. 
	 */
	@JColumn(name="ACTION",type=JSQLType.VARCHAR,length=64)
	private String action;
	
	/**
	 * cached the resource. 
	 */
	@JColumn(name="CACHED",type=JSQLType.VARCHAR,length=1)
	private String cached;
	
	/**
	 * description. 
	 */
	@JColumn(name="DESCRIPTION",type=JSQLType.VARCHAR,length=256)
	private String description;
	
	private Resource resource;
	
	@Transient
	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	@Column(name="CACHED")
	public String getCached() {
		return cached;
	}

	public void setCached(String cached) {
		this.cached = cached;
	}

	@Column(name="RESOURCEID")
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	@Column(name="URL")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name="ACTION")
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
}
