package me.bunny.app._c.sps.multiv;

import me.bunny.kernel.container.JIdentifier;


public abstract class ComponentVersionApplication  implements JIdentifier{

	protected String version;
	
	protected String component;
	
	protected String app;
	
	protected String urlPrefix;
	
	/**
	 * the friendly url that is the same functionality as {@link #unique()}
	 */
	private String friendlyUrl;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getUrlPrefix() {
		return urlPrefix;
	}

	public void setUrlPrefix(String urlPrefix) {
		this.urlPrefix = urlPrefix;
	}
	
	@Override
	public String unique() {
		return ComponentVersionSpringApplicationSupport.unique(
				this.app, this.component,this.version);
	}
	@Override
	public String name() {
		return unique();
	}

	public String getFriendlyUrl() {
		return friendlyUrl;
	}

	public void setFriendlyUrl(String friendlyUrl) {
		this.friendlyUrl = friendlyUrl;
	}
	
}
