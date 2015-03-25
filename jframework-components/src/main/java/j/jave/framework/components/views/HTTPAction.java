package j.jave.framework.components.views;

import j.jave.framework.components.core.context.ServiceContext;
import j.jave.framework.components.login.model.User;
import j.jave.framework.utils.JUtils;

public class HTTPAction implements Action {
	
	protected HTTPContext httpContext;
	
	public static final String CREATE_SUCCESS="保存成功";
	public static final String DELETE_SUCCESS="删除成功";
	public static final String EDIT_SUCCESS="更新成功";

	public HTTPContext getHttpContext() {
		return httpContext;
	}

	public void setHttpContext(HTTPContext httpContext) {
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
	
	protected User getSessionUser(){
		return httpContext.getUser();
	}
	
	
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
	
	protected ServiceContext getServiceContext(){
		ServiceContext context=new ServiceContext();
		context.setUser(getSessionUser());
		return context;
	}
	protected void setSuccessMessage(String message){
		if(JUtils.isNullOrEmpty(message)){
			message="操作成功";
		}
		setAttribute("successAlertMessage", message);
	}
	
}
