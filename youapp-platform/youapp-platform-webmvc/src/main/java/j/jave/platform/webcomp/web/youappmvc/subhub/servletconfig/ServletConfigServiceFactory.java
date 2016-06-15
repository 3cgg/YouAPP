/**
 * 
 */
package j.jave.platform.webcomp.web.youappmvc.subhub.servletconfig;

import j.jave.kernal.jave.exception.JOperationNotSupportedException;
import j.jave.platform.sps.core.servicehub.SpringServiceFactorySupport;

/**
 * @author J
 */
public abstract class ServletConfigServiceFactory extends SpringServiceFactorySupport<ServletConfigService> {
	
	public ServletConfigServiceFactory() {
		throw new JOperationNotSupportedException("the empty constructor is not supported.");
	}
	
	public ServletConfigServiceFactory(Class<ServletConfigService> registClass) {
		super(registClass);
	}
	
	@Override
	public ServletConfigService getService() {
		return getBeanByName(DefaultServletConfigServiceImpl.class.getName());
	}

}
