package j.jave.framework.components.web.multi.platform.filter;

import j.jave.framework.commons.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.framework.commons.json.JJSON;
import j.jave.framework.commons.memcached.eventdriven.JMemcachedDisGetEvent;
import j.jave.framework.commons.utils.JStringUtils;
import j.jave.framework.components.web.model.JHttpContext;
import j.jave.framework.components.web.multi.platform.support.FilterResponse;
import j.jave.framework.components.web.subhub.loginaccess.LoginAccessService;
import j.jave.framework.components.web.support.JFilter;
import j.jave.framework.components.web.utils.JHttpUtils;

import java.io.IOException;

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
 * the filter may follow from JJSPLoginFilter, but the filter also intercept those request from mobile platform such as Android, IOS
 * <p> Common resource such as  Javascript , CSS or Image etc never be intercepted. 
 * So recommend configure the filter mapping using sample below:
 * <pre>
 * &lt;filter-mapping>
		&lt;filter-name>ResourceAccessFilter&lt;/filter-name>
		&lt;servlet-name>JJSPServiceServlet&lt;/servlet-name>
		&lt;servlet-name>JMobileServiceServlet&lt;/servlet-name>
		&lt;dispatcher>FORWARD&lt;/dispatcher>
		&lt;dispatcher>REQUEST&lt;/dispatcher>
		&lt;dispatcher>INCLUDE&lt;/dispatcher>
	&lt;/filter-mapping>    
 * @author J
 */
public class ResourceAccessFilter implements JFilter{

	private static final Logger LOGGER=LoggerFactory.getLogger(ResourceAccessFilter.class);
	
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
			
			// common resource , if path info is null or empty never intercepted by custom servlet.
			String pathInfo=JHttpUtils.getPathInfo(req);
			 
			if(!loginAccessService.isNeedLoginRole(pathInfo)){
				// 资源不需要登录权限
				chain.doFilter(request, response);
				return ;
			}
			
			String clientTicket=JHttpUtils.getTicket(req);
			
			// IF LOGINED, need check whether has an access to the resource
			if(JStringUtils.isNotNullOrEmpty(clientTicket)){
				JHttpContext context=serviceHubDelegate.addImmediateEvent(new JMemcachedDisGetEvent(this, clientTicket), JHttpContext.class);
				if(context!=null){
					boolean authorized=loginAccessService.authorizeOnUserId(pathInfo, context.getUser().getId());
					authorized=true;
					if(!authorized){
						FilterResponse filterResponse=FilterResponse.newNoAccess();
						filterResponse.setObject("have no access to the resource.");
						response.getOutputStream().write(JJSON.get().format(filterResponse).getBytes("utf-8"));
						return ;
					}
				}
				else{
					FilterResponse filterResponse=FilterResponse.newNoLogin();
					filterResponse.setObject("login user information [ticket:"+clientTicket+"] miss.");
					response.getOutputStream().write(JJSON.get().format(filterResponse).getBytes("utf-8"));
					return ;
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
		
	}
	
}
