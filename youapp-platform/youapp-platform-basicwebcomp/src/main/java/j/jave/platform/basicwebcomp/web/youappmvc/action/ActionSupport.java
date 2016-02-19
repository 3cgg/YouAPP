package j.jave.platform.basicwebcomp.web.youappmvc.action;

import j.jave.kernal.jave.exception.JOperationNotSupportedException;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.basicwebcomp.login.subhub.SessionUser;
import j.jave.platform.basicwebcomp.web.youappmvc.model.HttpContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * basic action for view controller.
 * @author J
 */
public abstract class ActionSupport implements Action {
	
	protected final Logger LOGGER=LoggerFactory.getLogger(getClass());
	
	protected HttpContext httpContext;
	
//	/**
//	 * {@link #setHttpContext(HttpContext)}
//	 */
//	public static final String httpContextCallBackName="setHttpContext";
	
	public static final String CREATE_SUCCESS="保存成功";
	public static final String DELETE_SUCCESS="删除成功";
	public static final String EDIT_SUCCESS="更新成功";

	public HttpContext getHttpContext() {
		return httpContext;
	}

	public void setHttpContext(HttpContext httpContext) {
		this.httpContext = httpContext;
	}
	
	protected void setAttribute(String key,Object obj){
		httpContext.setAttribute(key, obj);
	}
	
	public String getParameter(String key){
		return httpContext.getParameter(key);
	}
	
	public void setCookie(String key,String value){
		httpContext.setCookie(key, value);
	}
	
	public void setCookie(String key,String value,int maxAge){
		httpContext.setCookie(key, value, maxAge);
	}
	
	public void deleteCookie(String key){
		httpContext.deleteCookie(key);
	}
	
	protected SessionUser getSessionUser(){
		return httpContext.getUser();
	}

	protected void setSuccessMessage(String message){
		if(JStringUtils.isNullOrEmpty(message)){
			message="操作成功";
		}
		setAttribute("successAlertMessage", message);
	}
	
	/**
	 * sub-class should implements the method.
	 * @return
	 */
	protected JPage parseJPage(){
		throw new JOperationNotSupportedException("Not supported,check if the sub-class implements the method.");
	} 
	
	
	
}
