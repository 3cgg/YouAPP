package j.jave.platform.basicsupportcomp.core.container;

import j.jave.kernal.container.JRunner;
import j.jave.kernal.container.JRunnerLoader;
import j.jave.kernal.container.MicroContainerConfig;
import j.jave.platform.multiversioncompsupportcomp.DynamicComponentVersionApplication;
import j.jave.platform.multiversioncompsupportcomp.JComponentVersionSpringApplicationSupport;

import java.net.URL;

import org.springframework.context.ApplicationContext;

public class JDynamicSpringCompRunnerLoader implements JRunnerLoader{

	private ApplicationContext applicationContext;
	
	private URL[]  jarUrls;
	
	SpringCompMicroContainerConfig springCompMicroContainerConfig;
	
	public JDynamicSpringCompRunnerLoader(ApplicationContext applicationContext,URL[]  jarUrls ,
			SpringCompMicroContainerConfig springCompMicroContainerConfig
			) {
		this.applicationContext=applicationContext;
		this.jarUrls=jarUrls;
		this.springCompMicroContainerConfig=springCompMicroContainerConfig;
	}
	
	public JRunner load(MicroContainerConfig envContainerConfig) {
		DynamicComponentVersionApplication componentVersionApplication=  JComponentVersionSpringApplicationSupport.loadComponent(applicationContext, jarUrls);
		JDynamicSpringCompRunner springCompRunner=new JDynamicSpringCompRunner(componentVersionApplication.getApplicationContext(),springCompMicroContainerConfig);
		return springCompRunner;
	};
}
