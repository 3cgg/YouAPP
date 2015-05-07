package j.jave.framework.components.web.multi.platform.filter;

import j.jave.framework.components.core.servicehub.ServiceHubDelegate;
import j.jave.framework.components.memory.MemoryCachedService;
import j.jave.framework.components.memory.response.subhub.ResponseCachedResource;
import j.jave.framework.components.memory.response.subhub.ResponseEncacheMemoryCacheService;
import j.jave.framework.components.web.utils.HTTPUtils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * filter on all request , check if the response need be stored into memory.
 * @author J
 */
public class MemoryHTMLFilter implements Filter{

	MemoryCachedService<ResponseCachedResource> requestResourceMemoryCacheService;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		requestResourceMemoryCacheService=ServiceHubDelegate.get().getService(this,ResponseEncacheMemoryCacheService.class);
	}
	
		
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpServletRequest=(HttpServletRequest) request;
		String path=HTTPUtils.getPathInfo(httpServletRequest);
		//check if cached.
		if(requestResourceMemoryCacheService.isNeedCache(path)){ // need cached.
			ResponseCachedResource responseCachedResource=requestResourceMemoryCacheService.get(path);
			if(responseCachedResource==null){
				MemoryResponseWrapper responseWrapper = new MemoryResponseWrapper((HttpServletResponse)response); 
				chain.doFilter(request, responseWrapper);
				byte[] bytes=responseWrapper.getBytes();
				response.getOutputStream().write(bytes);
				
				ResponseCachedResource requestCachedResource=new ResponseCachedResource();
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
