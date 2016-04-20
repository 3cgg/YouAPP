package j.jave.platform.basicsupportcomp.core.container;

import j.jave.kernal.container.ContainerConfig;

import java.net.URL;

import org.springframework.context.ApplicationContext;

public class SpringContainerConfig extends ContainerConfig{
	
	/**
	 * the root application/or parent application
	 */
	private ApplicationContext applicationContext;
	
	/**
	 * loaded jar
	 */
	private URL[]  jarUrls;
	
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public URL[] getJarUrls() {
		return jarUrls;
	}

	public void setJarUrls(URL[] jarUrls) {
		this.jarUrls = jarUrls;
	}
	
}
