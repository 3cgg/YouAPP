/**
 * 
 */
package j.jave.platform.webcomp.web.cache.resource.coderef;

import org.springframework.stereotype.Service;

import me.bunny.app._c.sps.core.servicehub.SpringServiceFactorySupport;

/**
 * @author J
 */
@Service(value="CodeRefCacheServiceFactory")
public class CodeRefCacheServiceFactory extends SpringServiceFactorySupport<CodeRefCacheService> {
	
	@Override
	public CodeRefCacheService getService() {
		return getBeanByName(CodeRefCacheServiceImpl.BEAN_NAME);
	}
	
	
}
