package j.jave.platform.basicwebcomp.web.youappmvc.filter;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.memcached.eventdriven.JMemcachedDisGetEvent;
import j.jave.platform.basicwebcomp.login.subhub.LoginAccessService;
import j.jave.platform.basicwebcomp.web.support.JFilter;
import j.jave.platform.basicwebcomp.web.youappmvc.HttpContext;
import j.jave.platform.basicwebcomp.web.youappmvc.utils.YouAppMvcUtils;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			String pathInfo=YouAppMvcUtils.getPathInfo(req);
			 
			if(!loginAccessService.isNeedLoginRole(pathInfo)){
				// 资源不需要登录权限
				chain.doFilter(request, response);
				return ;
			}
			
			String clientTicket=YouAppMvcUtils.getTicket(req);
			
			// IF LOGINED, need check whether has an access to the resource
			if(JStringUtils.isNotNullOrEmpty(clientTicket)){
				HttpContext context=serviceHubDelegate.addImmediateEvent(new JMemcachedDisGetEvent(this, clientTicket), HttpContext.class);
				if(context!=null){
					boolean authorized=loginAccessService.authorizeOnUserId(pathInfo, context.getUser().getId());
					authorized=true;
					if(!authorized){
						FilterResponse filterResponse=FilterResponse.newNoAccess();
						filterResponse.setData("have no access to the resource.");
						response.getOutputStream().write(JJSON.get().formatObject(filterResponse).getBytes("utf-8"));
						return ;
					}
				}
				else{
					FilterResponse filterResponse=FilterResponse.newNoLogin();
					filterResponse.setData("login user information [ticket:"+clientTicket+"] miss, refresh your broswer to re-login");
					response.getOutputStream().write(JJSON.get().formatObject(filterResponse).getBytes("utf-8"));
					YouAppMvcUtils.removeTicket(req, (HttpServletResponse) response);
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
