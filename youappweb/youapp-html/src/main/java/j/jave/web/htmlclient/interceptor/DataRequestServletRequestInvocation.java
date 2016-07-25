package j.jave.web.htmlclient.interceptor;

import j.jave.web.htmlclient.request.RequestVO;


/**
 * 
 * @author J
 */
public interface DataRequestServletRequestInvocation extends ServletRequestInvocation {
	
	RequestVO getRequestVO();
	
	void setRequestVO(RequestVO requestVO);

}
