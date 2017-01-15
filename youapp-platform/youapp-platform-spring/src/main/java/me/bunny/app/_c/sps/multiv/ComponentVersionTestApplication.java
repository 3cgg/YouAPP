package me.bunny.app._c.sps.multiv;

public class ComponentVersionTestApplication extends ComponentVersionApplication {
	
	public ComponentVersionTestApplication() {
		this.app="YouAPP";
		this.component="COM-TEST";
		this.version="1.0";
		this.urlPrefix="";
	}
	

	public ComponentVersionTestApplication(String app, String component,
			String version, String urlPrefix) {
		this.app = app;
		this.component = component;
		this.version = version;
		this.urlPrefix = urlPrefix;
	}
	
	
	
}
