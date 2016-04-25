package j.jave.kernal.container.rhttp;

import j.jave.kernal.container.ContainerConfig;

public class JRemoteHttpContainerConfig extends ContainerConfig{
	
	/**
	 * the context path for the special APP
	 */
	private String contextPath;

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	
}
