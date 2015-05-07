package j.jave.framework.components.web.jsp;

import j.jave.framework.components.core.model.JQueryDataTablePage;
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
	
	
	/**
	 * the method supports the {@code JQuery Data Table. }
	 */
	@Override
	protected JQueryDataTablePage parseJPage() {
		String sEcho=getParameter("sEcho");
		int iDisplayStart=Integer.parseInt(getParameter("iDisplayStart"));
		int iDisplayLength=Integer.parseInt(getParameter("iDisplayLength"));
		
		JQueryDataTablePage page=new JQueryDataTablePage();
		page.setsEcho(sEcho);
		page.setPageSize(iDisplayLength);
		int pageNum=iDisplayStart/iDisplayLength;
		page.setCurrentPageNum(pageNum+1);
		page.setSortColumn(getParameter("sortColumn"));
		page.setSortType(getParameter("sortType"));
		return page;
		
	}
	
	
	/**
	 * only for test function.
	 * @param jsp
	 * @return
	 */
	public String getTestPage(String jsp){
		setAttribute("jspFile", jsp);
		return "/WEB-INF/jsp/index-test-not-product.jsp";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
