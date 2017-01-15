/**
 * 
 */
package com.youappcorp.project.resourcemanager.vo;

import me.bunny.app._c.data.web.model.BaseCriteria;

/**
 * @author J
 */
public class ResourceSearchCriteria extends BaseCriteria{

	private String url;
	
	private String friendlyUrl;
	
	private String cached;
	
	private String description;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getFriendlyUrl() {
		return friendlyUrl;
	}

	public void setFriendlyUrl(String friendlyUrl) {
		this.friendlyUrl = friendlyUrl;
	}

	public String getCached() {
		return cached;
	}

	public void setCached(String cached) {
		this.cached = cached;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


}
