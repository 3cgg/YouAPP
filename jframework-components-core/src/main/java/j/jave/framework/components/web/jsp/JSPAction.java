package j.jave.framework.components.web.jsp;

import j.jave.framework.components.web.action.AbstractAction;

/**
 * JSP basic action. 
 * @author J
 */
public abstract class JSPAction extends AbstractAction {

	/**
	 * reload the URL. 
	 * @param action
	 * @return
	 */
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
