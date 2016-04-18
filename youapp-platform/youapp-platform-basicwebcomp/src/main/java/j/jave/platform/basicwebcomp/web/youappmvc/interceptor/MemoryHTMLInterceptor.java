package j.jave.platform.basicwebcomp.web.youappmvc.interceptor;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.io.JFile;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.platform.basicwebcomp.web.cache.response.ResponseCacheModel;
import j.jave.platform.basicwebcomp.web.cache.response.ResponseEhcacheCacheService;
import j.jave.platform.basicwebcomp.web.model.ResponseModel;
import j.jave.platform.basicwebcomp.web.model.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
		HttpServletRequest request= servletRequestInvocation.getHttpServletRequest();
		HttpServletResponse response=servletRequestInvocation.getHttpServletResponse();
		try{
			
			String path=servletRequestInvocation.getMappingPath();
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
//					HttpServletResponseUtil.writeBytesDirectly((HttpServletRequest)request, (HttpServletResponse)response, responseCachedResource.getBytes());
					return responseCachedResource.getObject();
				}
			}
			// no need cached.
			else{
				return servletRequestInvocation.proceed();
			}
		
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e); 
			return ServletExceptionUtil.exception(request, response, e);
		}
	}
	
}
