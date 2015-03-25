package j.jave.framework.components.views.model;

import j.jave.framework.model.JBaseModel;

public class ResourceAction extends JBaseModel {
	
	/**
	 * ID reference to RESOURCE table. 
	 */
	private String resourceId;
	
	/**
	 * URL 
	 */
	private String url;
	
	/**
	 * service method.  i.e.   loginService.login. 
	 */
	private String action;
	
	/**
	 * description. 
	 */
	private String description;

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
}
