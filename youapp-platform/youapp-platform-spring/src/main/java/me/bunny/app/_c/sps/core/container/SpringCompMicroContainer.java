package me.bunny.app._c.sps.core.container;

import me.bunny.app._c.sps.multiv.ComponentVersionApplication;
import me.bunny.kernel.container.JExecutableURIGenerator;
import me.bunny.kernel.container.JMicroContainer;
import me.bunny.kernel.container.JMicroContainerConfig;
import me.bunny.kernel.container.JRunner;

import java.net.URI;

import org.springframework.context.ApplicationContext;

public class SpringCompMicroContainer implements JMicroContainer ,JExecutableURIGenerator{

	private SpringContainerConfig springContainerConfig;
	
	private SpringCompMicroContainerConfig springCompMicroContainerConfig;
	
	private ComponentVersionApplication componentVersionApplication;
	
	private SpringCompRunner springCompRunner;
	
	private String name;
	
	private String unique;
	
	public SpringCompMicroContainer(
			SpringContainerConfig springContainerConfig,
			SpringCompMicroContainerConfig springCompMicroContainerConfig,
			ComponentVersionApplication componentVersionApplication) {
		this.springContainerConfig=springContainerConfig;
		this.springCompMicroContainerConfig=springCompMicroContainerConfig;
		this.componentVersionApplication=componentVersionApplication;
		this.name=springCompMicroContainerConfig.getName();
		this.unique=springCompMicroContainerConfig.getUnique();
	}
	
	@Override
	public boolean accept(URI uri) {
		return springCompRunner.accept(uri);
	}

	@Override
	public Object execute(URI uri, Object object) {
		return springCompRunner.execute(uri, object);
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
		if(DynamicSpringContainerConfig.class.isInstance(springContainerConfig)){
			DynamicSpringContainerConfig dynamicSpringContainerConfig=(DynamicSpringContainerConfig)springContainerConfig;
			DynamicSpringCompRunnerLoader springCompRunnerLoader=new DynamicSpringCompRunnerLoader(
					dynamicSpringContainerConfig.getApplicationContext(), dynamicSpringContainerConfig.getJarUrls(),springCompMicroContainerConfig);
			springCompRunner=(SpringCompRunner) springCompRunnerLoader.load(springCompMicroContainerConfig);
		}
		else{
			springCompRunner=new SpringCompRunner(springContainerConfig.getApplicationContext(),
					componentVersionApplication, springCompMicroContainerConfig);
		}
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
		return springCompRunner;
	}

	@Override
	public void setRunner(JRunner runner) {
		this.springCompRunner=(SpringCompRunner) runner;
	}

	@Override
	public JMicroContainerConfig getContainerConfig() {
		return springCompMicroContainerConfig;
	}

	@Override
	public void setContainerConfig(JMicroContainerConfig containerConfig) {
		this.springCompMicroContainerConfig=(SpringCompMicroContainerConfig) containerConfig;
	}

	public ApplicationContext getApplicationCotext() {
		return springCompRunner.getApplicationContext();
	}
	
	public ComponentVersionApplication getComponentVersionApplication() {
		return springCompRunner.getComponentVersionApplication();
	}

	@Override
	public String getGetRequestURI(String unique, String path) {
		return springCompRunner.getGetRequestURI(unique, path);
	}

	@Override
	public String getPutRequestURI(String unique, String path) {
		return springCompRunner.getPutRequestURI(unique, path);
	}

	@Override
	public String getDeleteRequestURI(String unique, String path) {
		return springCompRunner.getDeleteRequestURI(unique, path);
	}

	@Override
	public String getExistRequestURI(String unique, String path) {
		return springCompRunner.getExistRequestURI(unique, path);
	}
}
