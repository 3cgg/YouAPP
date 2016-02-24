/**
 * 
 */
package j.jave.platform.basicwebcomp.web.youappmvc.jspview.tag;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.platform.basicwebcomp.login.subhub.LoginAccessService;
import j.jave.platform.basicwebcomp.web.youappmvc.HttpContext;
import j.jave.platform.basicwebcomp.web.youappmvc.utils.YouAppMvcUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/**
 * @author J
 */
public class AccessTag implements Tag{

	 private PageContext pageContext;
	 
	 private Tag parent;
	 
	 private String resource;
	
	 private LoginAccessService loginAccessService=
			 JServiceHubDelegate.get().getService(this,LoginAccessService.class);
	 
	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {

		HttpServletRequest request=(HttpServletRequest) pageContext.getRequest();
		//String userName=pageContext.get
		HttpContext httpContext=YouAppMvcUtils.getHttpContext(request);
		boolean authorized=loginAccessService.authorizeOnUserId(resource, httpContext.getUser().getId());
		if(!authorized){
			return SKIP_BODY;
		}
		return EVAL_BODY_INCLUDE;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.Tag#getParent()
	 */
	@Override
	public Tag getParent() {
		return this.parent;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.Tag#release()
	 */
	@Override
	public void release() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.Tag#setPageContext(javax.servlet.jsp.PageContext)
	 */
	@Override
	public void setPageContext(PageContext arg0) {
		this.pageContext=arg0;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.Tag#setParent(javax.servlet.jsp.tagext.Tag)
	 */
	@Override
	public void setParent(Tag arg0) {
		this.parent=arg0;
	}

}
