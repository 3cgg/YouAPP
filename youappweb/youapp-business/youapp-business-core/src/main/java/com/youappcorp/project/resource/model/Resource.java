/**
 * 
 */
package com.youappcorp.project.resource.model;

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
@JTable(name="RESOURCES")
@Table(name="RESOURCES")
@Entity
public class Resource extends JJpaBaseModel {

	@JColumn(name="URL",type=JSQLType.VARCHAR,length=128)
	private String url;
	
	
	@JColumn(name="DESCRIPTION",type=JSQLType.VARCHAR,length=256)
	private String description;
	
	private ResourceExtend resourceExtend;

	@Transient
	public ResourceExtend getResourceExtend() {
		return resourceExtend;
	}


	public void setResourceExtend(ResourceExtend resourceExtend) {
		this.resourceExtend = resourceExtend;
	}


	@Column(name="URL")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
