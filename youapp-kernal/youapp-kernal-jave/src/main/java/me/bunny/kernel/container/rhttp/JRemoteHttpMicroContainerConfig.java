package me.bunny.kernel.container.rhttp;

import me.bunny.kernel.container.JMicroContainerConfig;

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
