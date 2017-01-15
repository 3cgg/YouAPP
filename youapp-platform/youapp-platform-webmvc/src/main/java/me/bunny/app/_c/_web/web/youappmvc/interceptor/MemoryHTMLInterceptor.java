package me.bunny.app._c._web.web.youappmvc.interceptor;

import me.bunny.app._c._web.web.cache.response.ResponseCacheModel;
import me.bunny.app._c._web.web.cache.response.ResponseEhcacheCacheService;
import me.bunny.app._c._web.web.model.ResponseModel;
import me.bunny.app._c._web.web.model.ResponseStatus;
import me.bunny.kernel._c.io.JFile;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;
/**
 * Filter on all requests, check if the response need be stored into memory or not.
 * The decider is {@link MemoryCachedService#isNeedCache(String)},
 * @author J
 * @see MemoryCachedService
 */
public class MemoryHTMLInterceptor implements ServletRequestInterceptor{

	private static final JLogger LOGGER=JLoggerFactory.getLogger(MemoryHTMLInterceptor.class);
	
	ResponseEhcacheCacheService requestResourceMemoryCacheService
	=JServiceHubDelegate.get().getService(this,ResponseEhcacheCacheService.class);
	
	@Override
	public Object intercept(ServletRequestInvocation servletRequestInvocation) {
		try{
			
			String path=servletRequestInvocation.getHttpContext().getVerMappingMeta().getMappingPath();
			//check if cached.
			if(requestResourceMemoryCacheService.isNeedCache(path)){ // need cached.
				ResponseCacheModel responseCachedResource=requestResourceMemoryCacheService.get(path);
				if(responseCachedResource==null){
					Object object=servletRequestInvocation.proceed();
					
					if(ResponseModel.class.isInstance(object)){
						ResponseModel responseModel=(ResponseModel) object;
						if(responseModel.getStatus()==ResponseStatus.SUCCESS){
							ResponseCacheModel requestCachedResource=new ResponseCacheModel();
							requestCachedResource.setPath(path);
							requestCachedResource.setObject(responseModel);
							requestResourceMemoryCacheService.add(requestCachedResource);
						}
					}
					else if(JFile.class.isInstance(object)){
						JFile file=(JFile) object;
						ResponseCacheModel requestCachedResource=new ResponseCacheModel();
						requestCachedResource.setPath(path);
						requestCachedResource.setObject(file);
						requestResourceMemoryCacheService.add(requestCachedResource);
					}
					return object;
				}
				else{
					return responseCachedResource.getObject();
				}
			}
			// no need cached.
			else{
				return servletRequestInvocation.proceed();
			}
		
		}catch(Throwable e){
			LOGGER.error(e.getMessage(), e); 
			return e;
		}
	}
	
}
