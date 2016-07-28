package j.jave.platform.webcomp.web.youappmvc.interceptor;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.io.JFile;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.platform.webcomp.web.cache.response.ResponseCacheModel;
import j.jave.platform.webcomp.web.cache.response.ResponseEhcacheCacheService;
import j.jave.platform.webcomp.web.model.ResponseModel;
import j.jave.platform.webcomp.web.model.ResponseStatus;
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
