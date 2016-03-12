package j.jave.platform.basicwebcomp.web.youappmvc.action;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.exception.JOperationNotSupportedException;
import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.login.subhub.SessionUser;
import j.jave.platform.basicwebcomp.web.support.ControllerSupport;
import j.jave.platform.basicwebcomp.web.youappmvc.HttpContext;
import j.jave.platform.basicwebcomp.web.youappmvc.service.PageableService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * basic action for view controller.
 * @author J
 */
public abstract class ActionSupport extends ControllerSupport implements Action {
	
	protected final Logger LOGGER=LoggerFactory.getLogger(getClass());
	
	public static final ThreadLocal<HttpContext> httpContext=new ThreadLocal<HttpContext>();
	
	protected PageableService pageableService=JServiceHubDelegate.get().getService(this, PageableService.class);
	
//	/**
//	 * {@link #setHttpContext(HttpContext)}
//	 */
//	public static final String httpContextCallBackName="setHttpContext";
	
	public static final String CREATE_SUCCESS="保存成功";
	public static final String DELETE_SUCCESS="删除成功";
	public static final String EDIT_SUCCESS="更新成功";

	public void removeHttpContext(){
		httpContext.remove();
	}
	
	public HttpContext getHttpContext() {
		return httpContext.get();
	}

	public void setHttpContext(HttpContext httpContext) {
		ActionSupport.httpContext.set(httpContext);
	}
	
	protected void setAttribute(String key,Object obj){
		httpContext.get().setAttribute(key, obj);
	}
	
	public String getParameter(String key){
		return httpContext.get().getParameter(key);
	}
	
	public void setCookie(String key,String value){
		httpContext.get().setCookie(key, value);
	}
	
	public void setCookie(String key,String value,int maxAge){
		httpContext.get().setCookie(key, value, maxAge);
	}
	
	public void deleteCookie(String key){
		httpContext.get().deleteCookie(key);
	}
	
	protected SessionUser getSessionUser(){
		return httpContext.get().getUser();
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
	protected JPageable parseJPage(){
		throw new JOperationNotSupportedException("Not supported,check if the sub-class implements the method.");
	} 
	
	public ServiceContext getServiceContext(){
		return new ServiceContext();
	}
	
	
}
