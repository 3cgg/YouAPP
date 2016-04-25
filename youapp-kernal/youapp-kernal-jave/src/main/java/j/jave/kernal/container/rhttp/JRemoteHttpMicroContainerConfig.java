package j.jave.kernal.container.rhttp;

import j.jave.kernal.container.MicroContainerConfig;

class JRemoteHttpMicroContainerConfig extends MicroContainerConfig {
	
	private JRemoteHttpContainerConfig remoteHttpContainerConfig;

	public JRemoteHttpContainerConfig getRemoteHttpContainerConfig() {
		return remoteHttpContainerConfig;
	}

	public void setRemoteHttpContainerConfig(
			JRemoteHttpContainerConfig remoteHttpContainerConfig) {
		this.remoteHttpContainerConfig = remoteHttpContainerConfig;
	}
	
}
