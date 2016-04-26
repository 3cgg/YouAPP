package j.jave.kernal.container.rhttp;

import j.jave.kernal.container.ContainerConfig;

public class JRemoteHttpContainerConfig extends ContainerConfig{
	
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
