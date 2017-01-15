package me.bunny.kernel.container.rhttp;

import me.bunny.kernel.container.JMicroContainerConfig;
import me.bunny.kernel.container.JRunner;
import me.bunny.kernel.container.JRunnerLoader;

public class JRemoteHttpRunnerLoader implements JRunnerLoader{

	private JRemoteHttpMicroContainerConfig remoteHttpMicroContainerConfig;
	
	public JRemoteHttpRunnerLoader(JRemoteHttpMicroContainerConfig remoteHttpMicroContainerConfig) {
		this.remoteHttpMicroContainerConfig=remoteHttpMicroContainerConfig;
	}
	
	@Override
	public JRunner load(JMicroContainerConfig microContainerConfig) {
		JRemoteHttpRunner remoteHttpRunner=new JRemoteHttpRunner(remoteHttpMicroContainerConfig);
		return remoteHttpRunner;
	}

}
