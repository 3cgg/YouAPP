package j.jave.web.htmlclient.interceptor;

import j.jave.web.htmlclient.request.RequestHtml;


/**
 * 
 * @author J
 */
public interface HtmlRequestServletRequestInvocation extends ServletRequestInvocation {

	RequestHtml getRequestHtml();
	
	void setRequestHtml(RequestHtml requestHtml);
}
