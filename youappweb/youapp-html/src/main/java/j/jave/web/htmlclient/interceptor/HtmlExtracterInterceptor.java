package j.jave.web.htmlclient.interceptor;

import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.web.htmlclient.RequestParamNames;
import j.jave.web.htmlclient.ServletRequestContext;
import j.jave.web.htmlclient.request.RequestHtml;

import javax.servlet.http.HttpServletRequest;


/**
 * @author JIAZJ
 *
 */
public class HtmlExtracterInterceptor implements HtmlRequestServletRequestInterceptor {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(HtmlExtracterInterceptor.class);
	
	@Override
	public Object intercept(HtmlRequestServletRequestInvocation servletRequestInvocation) {
		HttpServletRequest req=servletRequestInvocation.getHttpServletRequest();
		String requestData=req.getParameter(RequestParamNames.REQUEST_DATA);
        
        if(LOGGER.isDebugEnabled()){
        	LOGGER.debug("the request data (html)-> "+requestData);
        }
        
        if(requestData!=null&&requestData.length()>0){
        	RequestHtml requestHtml=JJSON.get().parse(requestData, RequestHtml.class);
        	if(JStringUtils.isNotNullOrEmpty(requestHtml.getHtmlUrl())){
        		if(!requestHtml.getHtmlUrl().startsWith("/")){
        			requestHtml.setHtmlUrl("/"+requestHtml.getHtmlUrl());
        		}
        	}
        	requestHtml.setRequest(new ServletRequestContext(req));
        	servletRequestInvocation.setRequestHtml(requestHtml);
        }
        else{
        	throw new RuntimeException("request data (html) is missing.");
        }
        return servletRequestInvocation.proceed();

	}
	
	
}
