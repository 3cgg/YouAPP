package j.jave.framework.components.web.mobile;

import j.jave.framework.components.web.multi.platform.filter.JLoginFilter;
import j.jave.framework.components.web.multi.platform.support.APPFilterConfig;
import j.jave.framework.components.web.multi.platform.support.FilterResponse;
import j.jave.framework.components.web.support.JFilter;
import j.jave.framework.json.JJSON;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * the filter is only for Mobile , i.e. generally it's for the request sent from the <strong>Android or IOS</strong>.
 * for any other platforms such as Browser, from which the request never be intercepted. 
 * as so , you need be aware of the <strong>&lt;url-pattern></strong> , and
 * <strong>Note that some request based on servlet path need be forward. so you may configure servlet path ( {@link APPFilterConfig#SERVICE_ON_SERVLET_PATH} ),
 * <pre>
 * &lt;filter>
		&lt;filter-name>JMobileLoginFilter&lt;/filter-name>
		&lt;filter-class>j.jave.framework.components.web.mobile.JMobileLoginFilter&lt;/filter-class>
	&lt;/filter>
	&lt;filter-mapping>
		&lt;filter-name>JMobileLoginFilter&lt;/filter-name>
		&lt;servlet-name>JMobileServiceServlet&lt;/servlet-name>
		&lt;dispatcher>FORWARD&lt;/dispatcher>
		&lt;dispatcher>REQUEST&lt;/dispatcher>
	&lt;/filter-mapping>
 *</pre>
 * @author J
 * @see {@link APPFilterConfig}
 */
public class JMobileLoginFilter extends JLoginFilter implements JFilter ,APPFilterConfig {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
		//add below 
	}
	
	@Override
	protected void handlerNoLogin(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain) throws Exception {
		FilterResponse filterResponse= FilterResponse.newNoLogin();
		MobileResult mobileResult=MobileResult.newMessage();
		mobileResult.setData(filterResponse);
		response.getOutputStream().write(JJSON.get().format(mobileResult).getBytes("utf-8"));
	}
	
	@Override
	protected void handlerDuplicateLogin(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain) throws Exception {
		FilterResponse filterResponse= FilterResponse.newDuplicateLogin();
		MobileResult mobileResult=MobileResult.newMessage();
		mobileResult.setData(filterResponse);
		response.getOutputStream().write(JJSON.get().format(mobileResult).getBytes("utf-8"));
	}
	
	@Override
	public void destroy() {
		//add above
		
		super.destroy();
	}

}
