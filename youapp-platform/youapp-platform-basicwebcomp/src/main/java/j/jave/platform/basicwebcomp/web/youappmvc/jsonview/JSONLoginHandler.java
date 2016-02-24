package j.jave.platform.basicwebcomp.web.youappmvc.jsonview;

import j.jave.kernal.jave.json.JJSON;
import j.jave.platform.basicwebcomp.web.model.ResponseModel;
import j.jave.platform.basicwebcomp.web.youappmvc.filter.FilterResponse;
import j.jave.platform.basicwebcomp.web.youappmvc.filter.LoginFilter.LoginHandler;
import j.jave.platform.basicwebcomp.web.youappmvc.support.APPFilterConfig;

import javax.servlet.FilterChain;
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
public class JSONLoginHandler implements LoginHandler ,APPFilterConfig {
	
	
	@Override
	public void handleNoLogin(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain) throws Exception {
		FilterResponse filterResponse= FilterResponse.newNoLogin();
		ResponseModel mobileResult=ResponseModel.newMessage();
		mobileResult.setData(filterResponse);
		response.getOutputStream().write(JJSON.get().formatObject(mobileResult).getBytes("utf-8"));
	}
	
	@Override
	public void handleDuplicateLogin(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain) throws Exception {
		FilterResponse filterResponse= FilterResponse.newDuplicateLogin();
		ResponseModel mobileResult=ResponseModel.newMessage();
		mobileResult.setData(filterResponse);
		response.getOutputStream().write(JJSON.get().formatObject(mobileResult).getBytes("utf-8"));
	}

	@Override
	public void handleToLogin(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain) throws Exception {
	}
	
	
	

}
