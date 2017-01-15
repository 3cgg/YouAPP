package me.bunny.app._c.sps.core.container;

import java.net.URL;

public class DynamicSpringContainerConfig extends SpringContainerConfig{
	/**
	 * loaded jar
	 */
	private URL[]  jarUrls;

	public URL[] getJarUrls() {
		return jarUrls;
	}

	public void setJarUrls(URL[] jarUrls) {
		this.jarUrls = jarUrls;
	}
	
}
