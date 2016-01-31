package j.jave.platform.basicwebcomp.web.youappmvc.multi.platform.filter;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.platform.basicwebcomp.web.cache.response.ResponseCacheModel;
import j.jave.platform.basicwebcomp.web.cache.response.ResponseEhcacheMemoryCacheService;
import j.jave.platform.basicwebcomp.web.support.JFilter;
import j.jave.platform.basicwebcomp.web.util.MemoryResponseWrapper;
import j.jave.platform.basicwebcomp.web.youappmvc.utils.JHttpUtils;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Filter on all requests, check if the response need be stored into memory or not.
 * The decider is {@link MemoryCachedService#isNeedCache(String)},
 * @author J
 * @see MemoryCachedService
 */
public class MemoryHTMLFilter implements JFilter{

	ResponseEhcacheMemoryCacheService requestResourceMemoryCacheService;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		requestResourceMemoryCacheService=JServiceHubDelegate.get().getService(this,ResponseEhcacheMemoryCacheService.class);
	}
	
		
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpServletRequest=(HttpServletRequest) request;
		String path=JHttpUtils.getPathInfo(httpServletRequest);
		//check if cached.
		if(requestResourceMemoryCacheService.isNeedCache(path)){ // need cached.
			ResponseCacheModel responseCachedResource=requestResourceMemoryCacheService.get(path);
			if(responseCachedResource==null){
				MemoryResponseWrapper responseWrapper = new MemoryResponseWrapper((HttpServletResponse)response); 
				chain.doFilter(request, responseWrapper);
				byte[] bytes=responseWrapper.getBytes();
				response.getOutputStream().write(bytes);
				
				ResponseCacheModel requestCachedResource=new ResponseCacheModel();
				requestCachedResource.setPath(path);
				requestCachedResource.setBytes(bytes);
				requestResourceMemoryCacheService.add(requestCachedResource);
			}
			else{
				response.getOutputStream().write(responseCachedResource.getBytes());
			}
		}
		// no need cached.
		else{
			chain.doFilter(request, response);
		}
		
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
}
