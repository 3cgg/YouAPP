package j.jave.kernal.container.rhttp;

import j.jave.kernal.container.JRunner;
import j.jave.kernal.container.MicroContainer;
import j.jave.kernal.container.MicroContainerConfig;

import java.net.URI;

class JRemoteHttpMicroContainer implements MicroContainer{

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
		return false;
	}

	@Override
	public Object execute(URI uri, Object object) {
		return null;
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
	public MicroContainerConfig getContainerConfig() {
		return remoteHttpMicroContainerConfig;
	}

	@Override
	public void setContainerConfig(MicroContainerConfig containerConfig) {
		this.remoteHttpMicroContainerConfig= (JRemoteHttpMicroContainerConfig)containerConfig;
	}
	
//	static final String getGetRequest(String unique,String path){
//		return ControllerRunner.getGetRequest(unique, path);
//	}
//
//	static final String getPutRequest(String unique,String path){
//		return ControllerRunner.getPutRequest(unique, path);
//	}
//	
//	static final String getExistRequest(String unique,String path){
//		return ControllerRunner.getExistRequest(unique, path);
//	}
	
	
}
