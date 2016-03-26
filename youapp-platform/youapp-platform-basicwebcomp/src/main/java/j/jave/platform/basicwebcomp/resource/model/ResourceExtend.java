package j.jave.platform.basicwebcomp.resource.model;

import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.model.support.JColumn;
import j.jave.kernal.jave.model.support.JSQLType;
import j.jave.kernal.jave.model.support.JTable;

@JTable(name="RESOURCES_EXTEND")
public class ResourceExtend extends JBaseModel{
	
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
	
	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public String getCached() {
		return cached;
	}

	public void setCached(String cached) {
		this.cached = cached;
	}

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
