package j.jave.platform.sps.core.container;

import j.jave.kernal.container.JRunner;
import j.jave.kernal.container.JRunnerLoader;
import j.jave.kernal.container.JMicroContainerConfig;
import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.platform.sps.multiv.ComponentVersionSpringApplicationSupport;
import j.jave.platform.sps.multiv.DynamicComponentVersionApplication;

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
