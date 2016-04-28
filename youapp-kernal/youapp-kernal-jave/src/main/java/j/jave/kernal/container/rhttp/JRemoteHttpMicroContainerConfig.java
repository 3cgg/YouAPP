package j.jave.kernal.container.rhttp;

import j.jave.kernal.container.JMicroContainerConfig;

class JRemoteHttpMicroContainerConfig extends JMicroContainerConfig {
	
	private JRemoteHttpContainerConfig remoteHttpContainerConfig;

	public JRemoteHttpContainerConfig getRemoteHttpContainerConfig() {
		return remoteHttpContainerConfig;
	}

	public void setRemoteHttpContainerConfig(
			JRemoteHttpContainerConfig remoteHttpContainerConfig) {
		this.remoteHttpContainerConfig = remoteHttpContainerConfig;
	}
	
}
