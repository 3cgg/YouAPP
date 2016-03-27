/**
 * 
 */
package j.jave.platform.basicwebcomp.web.cache.resource.coderef;

import j.jave.platform.basicsupportcomp.core.servicehub.SpringServiceFactorySupport;

import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service(value="j.jave.platform.basicwebcomp.web.cache.resource.coderef.CodeRefCacheServiceFactory")
public class CodeRefCacheServiceFactory extends SpringServiceFactorySupport<CodeRefCacheService> {
	
	@Override
	public CodeRefCacheService getService() {
		return getBeanByName(CodeRefCacheServiceImpl.class.getName());
	}
	
	
}
