/**
 * 
 */
package com.youappcorp.project.resourcemanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import me.bunny.app._c.jpa.springjpa.JJpaBaseModel;

/**
 * @author J
 */
@Table(name="RESOURCES")
@Entity
public class Resource extends JJpaBaseModel {

	private String url;
	
	private String friendlyUrl;
	
	private String cached;
	
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
