package j.jave.kernal.container.rhttp;

import j.jave.kernal.container.JExecutableURIGenerator;
import j.jave.kernal.container.JRunner;
import j.jave.kernal.container.JMicroContainer;
import j.jave.kernal.container.JMicroContainerConfig;

import java.net.URI;

class JRemoteHttpMicroContainer implements JMicroContainer,JExecutableURIGenerator{

	private JRemoteHttpContainerConfig remoteHttpContainerConfig;
	
	private JRemoteHttpMicroContainerConfig remoteHttpMicroContainerConfig;
	
	private JRemoteHttpRunner remoteHttpRunner;
	
	private String name;
	
	private String unique;
	
	public JRemoteHttpMicroContainer(JRemoteHttpContainerConfig remoteHttpContainerConfig,
			JRemoteHttpMicroContainerConfig remoteHttpMicroContainerConfig
			) {
		this.remoteHttpContainerConfig=remoteHttpContainerConfig;
		this.remoteHttpMicroContainerConfig=remoteHttpMicroContainerConfig;
		this.name=remoteHttpContainerConfig.name();
		this.unique=remoteHttpContainerConfig.unique();
	}
	
	@Override
	public boolean accept(URI uri) {
		return this.remoteHttpRunner.accept(uri);
	}

	@Override
	public Object execute(URI uri, Object object) {
		return this.remoteHttpRunner.execute(uri, object);
	}

	@Override
	public String unique() {
		return this.unique;
	}

	@Override
	public String name() {
		return this.name;
	}

	@Override
	public void initialize() {
		JRemoteHttpRunnerLoader remoteHttpRunnerLoader=new JRemoteHttpRunnerLoader(remoteHttpMicroContainerConfig);
		this.remoteHttpRunner=(JRemoteHttpRunner) remoteHttpRunnerLoader.load(remoteHttpMicroContainerConfig);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JRunner getRunner() {
		return remoteHttpRunner;
	}

	@Override
	public void setRunner(JRunner runner) {
		this.remoteHttpRunner= (JRemoteHttpRunner) runner;
	}

	@Override
	public JMicroContainerConfig getContainerConfig() {
		return remoteHttpMicroContainerConfig;
	}

	@Override
	public void setContainerConfig(JMicroContainerConfig containerConfig) {
		this.remoteHttpMicroContainerConfig= (JRemoteHttpMicroContainerConfig)containerConfig;
	}

	@Override
	public String getGetRequestURI(String unique, String path) {
		return remoteHttpRunner.getGetRequestURI(unique, path);
	}

	@Override
	public String getPutRequestURI(String unique, String path) {
		return remoteHttpRunner.getPutRequestURI(unique, path);
	}

	@Override
	public String getDeleteRequestURI(String unique, String path) {
		return remoteHttpRunner.getDeleteRequestURI(unique, path);
	}

	@Override
	public String getExistRequestURI(String unique, String path) {
		return remoteHttpRunner.getExistRequestURI(unique, path);
	}
	
}
