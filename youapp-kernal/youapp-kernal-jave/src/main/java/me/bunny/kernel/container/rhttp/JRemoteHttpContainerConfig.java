package me.bunny.kernel.container.rhttp;

import me.bunny.kernel.container.JContainerConfig;

public class JRemoteHttpContainerConfig extends JContainerConfig{
	
	/**
	 * the target host for the special APP
	 */
	private String host;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
}
