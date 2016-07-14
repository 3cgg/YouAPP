package j.jave.kernal.container.rhttp;

import j.jave.kernal.container.JContainerConfig;

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
