/**
 * 
 */
package j.jave.framework.components.web.jsp.tag;

import j.jave.framework.components.web.jsp.ServletRenderContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/**
 * @author J
 */
public class ServletRenderContextTag implements Tag{

	 private PageContext pageContext;
	 
	 private Tag parent;
	 
	 private String className;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		try{
			HttpServletRequest request=(HttpServletRequest) pageContext.getRequest();
			ServletRenderContext context=(ServletRenderContext) Thread.currentThread().getContextClassLoader().loadClass(className).newInstance();
			context.changeRenderContext(request);
		}catch(Exception e){
			throw new JspException(e);
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
