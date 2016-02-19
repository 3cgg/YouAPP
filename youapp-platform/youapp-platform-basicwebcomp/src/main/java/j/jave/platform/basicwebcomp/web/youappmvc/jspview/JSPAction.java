package j.jave.platform.basicwebcomp.web.youappmvc.jspview;

import j.jave.platform.basicwebcomp.web.youappmvc.action.ActionSupport;
import j.jave.platform.basicwebcomp.web.youappmvc.model.JQueryDataTablePage;

/**
 * JSP basic action. 
 * @author J
 */
public abstract class JSPAction extends ActionSupport {

	/**
	 * define navigate JSP ,  like as 
	 * '&lt;div id="url">${url}&lt;/div>'.
	 * browser will reload the url ,without Ajax.
	 * @return
	 */
	protected String getNavigateJSP(){
		return "/WEB-INF/jsp/navigate.jsp";
	}
	
	/**
	 * define error JSP page.
	 * @return
	 */
	protected String getErrorJSP(){
		return "/WEB-INF/jsp/error.jsp";
	}
	
	/**
	 * define warning JSP page.
	 * @return
	 */
	protected String getWarningJSP(){
		return "/WEB-INF/jsp/warning.jsp";
	}
	
	/**
	 * define success JSP page. 
	 * @return
	 */
	protected String getSuccessJSP(){
		return "/WEB-INF/jsp/success.jsp";
	}
	
	/**
	 * define for test page , including all page parts , html -> head-> body , without AJAX
	 * @return
	 */
	protected String getTestPageJSP(){
		return "/WEB-INF/jsp/index-test-not-product.jsp";
	}
	
	/**
	 * reload the URL. 
	 * @param action
	 * @return
	 */
	protected String navigate(String action) {
		setAttribute("url", action); 
		return getNavigateJSP();
	}
	
	/**
	 * show error info.
	 * @param message
	 * @return
	 */
	protected String error(String message) {
		setAttribute("message", message); 
		return getErrorJSP();
	}
	
	/**
	 * show warning info.
	 * @param message
	 * @return
	 */
	protected String warning(String message) {
		setAttribute("message", message); 
		return getWarningJSP();
	}
	
	/**
	 * show success info.
	 * @param message
	 * @return
	 */
	protected String success(String message) {
		setAttribute("message", message); 
		return getSuccessJSP();
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
		return getTestPageJSP();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
