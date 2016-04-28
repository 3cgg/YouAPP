package j.jave.kernal.container.rhttp;

import j.jave.kernal.container.JRunner;
import j.jave.kernal.container.JRunnerLoader;
import j.jave.kernal.container.JMicroContainerConfig;

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
