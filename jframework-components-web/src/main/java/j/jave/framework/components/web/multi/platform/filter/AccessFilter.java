package j.jave.framework.components.web.multi.platform.filter;

import j.jave.framework.components.web.action.HTTPContext;
import j.jave.framework.components.web.subhub.loginaccess.LoginAccessService;
import j.jave.framework.components.web.utils.HTTPUtils;
import j.jave.framework.servicehub.JServiceHubDelegate;
import j.jave.framework.servicehub.memcached.JMemcachedDisGetEvent;
import j.jave.framework.utils.JStringUtils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * filter on all request , check if the request is authorized on the end-user.
 * the filter is following from JJSPLoginFilter
 * @author J
 */
public class AccessFilter implements Filter{

	private static final Logger LOGGER=LoggerFactory.getLogger(AccessFilter.class);
	
	private LoginAccessService loginAccessService=JServiceHubDelegate.get().getService(this,LoginAccessService.class);
	
	private JServiceHubDelegate serviceHubDelegate=JServiceHubDelegate.get();
	
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
		
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		try{
			HttpServletRequest req=(HttpServletRequest) request;
			String pathInfo=HTTPUtils.getPathInfo(req);
			
			// common resource , never intercepted by servlet. 
			if(JStringUtils.isNullOrEmpty(pathInfo)){
				chain.doFilter(request, response);
				return ;
			}
			
			String clientTicket=HTTPUtils.getTicket(req);
			
			if(!loginAccessService.isNeedLoginRole(pathInfo)){
				// 资源不需要登录权限
				chain.doFilter(request, response);
				return ;
			}
			
			// IF LOGINED.
			if(JStringUtils.isNotNullOrEmpty(clientTicket)){
				HTTPContext context=serviceHubDelegate.addImmediateEvent(new JMemcachedDisGetEvent(this, clientTicket), HTTPContext.class);
				if(context!=null){
					boolean authorized=loginAccessService.authorizeOnUserId(pathInfo, context.getUser().getId());
					authorized=true;
					if(!authorized){
						response.getOutputStream().write("have no access to the resource.".getBytes("utf-8")); 
						return ;
					}
				}
				else{
					throw new ServletException("login user information miss.");
				}
			}
			chain.doFilter(request, response);
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e); 
			throw new ServletException(e);
		}
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
}
