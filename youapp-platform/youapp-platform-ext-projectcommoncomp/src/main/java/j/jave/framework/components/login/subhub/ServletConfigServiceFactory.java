/**
 * 
 */
package j.jave.framework.components.login.subhub;

import j.jave.framework.components.core.servicehub.SpringServiceFactorySupport;

import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service(value="j.jave.framework.components.login.subhub.ServletConfigServiceFactory")
public class ServletConfigServiceFactory extends SpringServiceFactorySupport<ServletConfigService> {
	
	public ServletConfigServiceFactory(){
		this(ServletConfigService.class);
	}
	/**
	 * @param registClass
	 */
	public ServletConfigServiceFactory(Class<ServletConfigService> registClass) {
		super(registClass);
	}
	
	@Override
	public ServletConfigService getService() {
		return getBeanByName("j.jave.framework.components.login.subhub.ServletConfigServiceImpl");
	}

}
