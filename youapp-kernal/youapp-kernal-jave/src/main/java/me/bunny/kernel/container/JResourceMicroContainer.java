package me.bunny.kernel.container;

import java.net.URI;

public class JResourceMicroContainer implements JMicroContainer {

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
		return true;
	}

	@Override
	public Object execute(URI uri, Object object) {
		return resourceRunner.execute(uri, object);
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
