package j.jave.platform.basicsupportcomp.core.container;

import j.jave.kernal.container.JRunner;
import j.jave.kernal.container.MicroContainer;
import j.jave.kernal.container.MicroContainerConfig;
import j.jave.platform.multiversioncompsupportcomp.ComponentVersionApplication;

import java.net.URI;

import org.springframework.context.ApplicationContext;

public class SpringCompMicroContainer implements MicroContainer{

	private SpringContainerConfig springContainerConfig;
	
	private SpringCompMicroContainerConfig springCompMicroContainerConfig;
	
	private ComponentVersionApplication componentVersionApplication;
	
	private JSpringCompRunner springCompRunner;
	
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
			JDynamicSpringCompRunnerLoader springCompRunnerLoader=new JDynamicSpringCompRunnerLoader(
					dynamicSpringContainerConfig.getApplicationContext(), dynamicSpringContainerConfig.getJarUrls(),springCompMicroContainerConfig);
			springCompRunner=(JSpringCompRunner) springCompRunnerLoader.load(springCompMicroContainerConfig);
		}
		else{
			springCompRunner=new JSpringCompRunner(springContainerConfig.getApplicationContext(),
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
		this.springCompRunner=(JSpringCompRunner) runner;
	}

	@Override
	public MicroContainerConfig getContainerConfig() {
		return springCompMicroContainerConfig;
	}

	@Override
	public void setContainerConfig(MicroContainerConfig containerConfig) {
		this.springCompMicroContainerConfig=(SpringCompMicroContainerConfig) containerConfig;
	}

	public ApplicationContext getApplicationCotext() {
		return springCompRunner.getApplicationContext();
	}
	
	public ComponentVersionApplication getComponentVersionApplication() {
		return springCompRunner.getComponentVersionApplication();
	}
	
	public static final String getGetRequest(String unique,String beanName){
		return JSpringCompRunner.getGetRequest(unique, beanName);
	}
}
