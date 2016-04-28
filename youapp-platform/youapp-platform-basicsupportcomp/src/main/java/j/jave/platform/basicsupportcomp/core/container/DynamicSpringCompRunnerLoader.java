package j.jave.platform.basicsupportcomp.core.container;

import j.jave.kernal.container.JRunner;
import j.jave.kernal.container.JRunnerLoader;
import j.jave.kernal.container.JMicroContainerConfig;
import j.jave.platform.multiversioncompsupportcomp.DynamicComponentVersionApplication;
import j.jave.platform.multiversioncompsupportcomp.ComponentVersionSpringApplicationSupport;

import java.net.URL;

import org.springframework.context.ApplicationContext;

public class DynamicSpringCompRunnerLoader implements JRunnerLoader{

	private ApplicationContext applicationContext;
	
	private URL[]  jarUrls;
	
	SpringCompMicroContainerConfig springCompMicroContainerConfig;
	
	public DynamicSpringCompRunnerLoader(ApplicationContext applicationContext,URL[]  jarUrls ,
			SpringCompMicroContainerConfig springCompMicroContainerConfig
			) {
		this.applicationContext=applicationContext;
		this.jarUrls=jarUrls;
		this.springCompMicroContainerConfig=springCompMicroContainerConfig;
	}
	
	public JRunner load(JMicroContainerConfig envContainerConfig) {
		DynamicComponentVersionApplication componentVersionApplication=  ComponentVersionSpringApplicationSupport.loadComponent(applicationContext, jarUrls);
		DynamicSpringCompRunner springCompRunner=new DynamicSpringCompRunner(componentVersionApplication.getApplicationContext(),springCompMicroContainerConfig);
		return springCompRunner;
	};
}
