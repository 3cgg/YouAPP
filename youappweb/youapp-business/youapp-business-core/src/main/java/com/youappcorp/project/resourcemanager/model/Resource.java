/**
 * 
 */
package com.youappcorp.project.resourcemanager.model;

import j.jave.kernal.jave.model.support.JColumn;
import j.jave.kernal.jave.model.support.JSQLType;
import j.jave.kernal.jave.model.support.JTable;
import j.jave.platform.jpa.springjpa.JJpaBaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author J
 */
@JTable(name="RESOURCES")
@Table(name="RESOURCES")
@Entity
public class Resource extends JJpaBaseModel {

	@JColumn(name="URL",type=JSQLType.VARCHAR,length=128)
	private String url;
	
	@JColumn(name="FRIENDLY_URL",type=JSQLType.VARCHAR,length=128)
	private String friendlyUrl;
	
	@JColumn(name="CACHED",type=JSQLType.VARCHAR,length=1)
	private String cached;
	
	@JColumn(name="DESCRIPTION",type=JSQLType.VARCHAR,length=256)
	private String description;

	@Column(name="URL")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name="FRIENDLY_URL")
	public String getFriendlyUrl() {
		return friendlyUrl;
	}

	public void setFriendlyUrl(String friendlyUrl) {
		this.friendlyUrl = friendlyUrl;
	}

	@Column(name="CACHED")
	public String getCached() {
		return cached;
	}

	public void setCached(String cached) {
		this.cached = cached;
	}
	
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
