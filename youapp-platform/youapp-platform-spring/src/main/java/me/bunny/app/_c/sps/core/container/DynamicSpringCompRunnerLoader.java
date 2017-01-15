package me.bunny.app._c.sps.core.container;

import me.bunny.app._c.sps.multiv.ComponentVersionSpringApplicationSupport;
import me.bunny.app._c.sps.multiv.DynamicComponentVersionApplication;
import me.bunny.kernel._c.exception.JInitializationException;
import me.bunny.kernel.container.JMicroContainerConfig;
import me.bunny.kernel.container.JRunner;
import me.bunny.kernel.container.JRunnerLoader;

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
		DynamicComponentVersionApplication componentVersionApplication=null;
		try {
			componentVersionApplication = ComponentVersionSpringApplicationSupport.loadComponent(applicationContext, jarUrls);
		} catch (Exception e) {
			throw new JInitializationException(e);
		}
		DynamicSpringCompRunner springCompRunner=new DynamicSpringCompRunner(componentVersionApplication.getApplicationContext(),springCompMicroContainerConfig);
		return springCompRunner;
	};
}
