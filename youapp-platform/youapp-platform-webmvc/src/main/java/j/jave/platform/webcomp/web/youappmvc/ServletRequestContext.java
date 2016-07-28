package j.jave.platform.webcomp.web.youappmvc;

import javax.servlet.http.HttpServletRequest;

public class ServletRequestContext implements RequestContext{

	private transient HttpServletRequest request;
	
	public ServletRequestContext(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public String getParameter(String name){
		return request.getParameter(name);
	}
	
	public String[] getParameterValues(String name){
		return request.getParameterValues(name);
	}
	
	
	
}
