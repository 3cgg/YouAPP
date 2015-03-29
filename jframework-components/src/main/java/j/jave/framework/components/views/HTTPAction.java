package j.jave.framework.components.views;

import j.jave.framework.components.core.context.ServiceContext;
import j.jave.framework.components.login.model.User;
import j.jave.framework.utils.JUtils;

public abstract class HTTPAction implements Action {
	
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
