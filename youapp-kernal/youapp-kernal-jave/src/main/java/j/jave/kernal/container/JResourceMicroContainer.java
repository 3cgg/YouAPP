package j.jave.kernal.container;

import java.net.URI;

public class JResourceMicroContainer implements JMicroContainer,
		JExecutableURIGenerator {

	private JResourceRunner resourceRunner;
	
	private JResourceMicroContainerConfig resourceMicroContainerConfig;
	
	private String unique;
	
	private String name;
	
	public JResourceMicroContainer(JResourceMicroContainerConfig resourceMicroContainerConfig) {
		this.resourceMicroContainerConfig=resourceMicroContainerConfig;
		this.name=resourceMicroContainerConfig.getName();
		this.unique=resourceMicroContainerConfig.unique();
	}
	
	@Override
	public boolean accept(URI uri) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object execute(URI uri, Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String unique() {
		return unique;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public void initialize() {
		JResourceRunnerLoader resourceRunnerLoader
		=new JResourceRunnerLoader();
		this.resourceRunner=resourceRunnerLoader.load(resourceMicroContainerConfig);
	}

	@Override
	public void destroy() {

	}

	@Override
	public void restart() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getGetRequestURI(String unique, String path) {
		return resourceRunner.getGetRequestURI(unique, path);
	}

	@Override
	public String getPutRequestURI(String unique, String path) {
		return resourceRunner.getPutRequestURI(unique, path);
	}

	@Override
	public String getDeleteRequestURI(String unique, String path) {
		return resourceRunner.getDeleteRequestURI(unique, path);
	}

	@Override
	public String getExistRequestURI(String unique, String path) {
		return resourceRunner.getExistRequestURI(unique, path);
	}

	@Override
	public JRunner getRunner() {
		return resourceRunner;
	}

	@Override
	public void setRunner(JRunner runner) {
		this.resourceRunner=(JResourceRunner) runner;
	}

	@Override
	public JMicroContainerConfig getContainerConfig() {
		return resourceMicroContainerConfig;
	}

	@Override
	public void setContainerConfig(JMicroContainerConfig containerConfig) {
		this.resourceMicroContainerConfig=(JResourceMicroContainerConfig) containerConfig;
	}

}
