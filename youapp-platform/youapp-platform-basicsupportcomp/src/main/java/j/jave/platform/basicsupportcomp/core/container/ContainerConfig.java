package j.jave.platform.basicsupportcomp.core.container;

import java.net.URL;

import org.springframework.context.ApplicationContext;

public class ContainerConfig  implements JIdentifier{
	
	private String unique;
	
	private String name;
	
	/**
	 * the root application/or parent application
	 */
	private ApplicationContext applicationContext;
	
	/**
	 * loaded jar
	 */
	private URL[]  jarUrls;

	public String getUnique() {
		return unique;
	}

	public void setUnique(String unique) {
		this.unique = unique;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String unique() {
		return unique;
	}

	@Override
	public String name() {
		return name;
	}

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
