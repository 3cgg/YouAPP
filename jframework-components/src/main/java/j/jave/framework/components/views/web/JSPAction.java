package j.jave.framework.components.views.web;

import j.jave.framework.components.views.HTTPAction;

public abstract class JSPAction extends HTTPAction {

	
	protected String navigate(String action) {
		setAttribute("url", action); 
		return "/WEB-INF/jsp/navigate.jsp";
	}
	
	protected String error(String message) {
		setAttribute("message", message); 
		return "/WEB-INF/jsp/error.jsp";
	}
	
	protected String warning(String message) {
		setAttribute("message", message); 
		return "/WEB-INF/jsp/warning.jsp";
	}
	
	protected String success(String message) {
		setAttribute("message", message); 
		return "/WEB-INF/jsp/success.jsp";
	}
}
