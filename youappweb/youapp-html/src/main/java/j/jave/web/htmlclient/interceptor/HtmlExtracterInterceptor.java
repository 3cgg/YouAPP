package j.jave.web.htmlclient.interceptor;

import java.util.Collections;

import j.jave.web.htmlclient.RequestParamNames;
import j.jave.web.htmlclient.ServletRequestContext;
import j.jave.web.htmlclient.request.RequestHtml;
import me.bunny.kernel._c.json.JJSON;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel._c.utils.JStringUtils;

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
        	
        	//processing parameter
        	String uri=requestHtml.getHtmlUrl();
			String paramMark="?param=";
			int paramMarkIndex=-1;
			if((paramMarkIndex=uri.indexOf(paramMark))!=-1){
				String viewUrl=uri.substring(0,paramMarkIndex);
				String viewParam=uri.substring(paramMarkIndex+paramMark.length());
				requestHtml.setViewParam(viewParam);
				requestHtml.setViewUrl(viewUrl);
			}
			else{
				requestHtml.setViewParam(JJSON.get().formatObject(Collections.EMPTY_MAP));
				requestHtml.setViewUrl(uri);
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
