package j.jave.platform.webcomp.web.youappmvc.bind;

import j.jave.kernal.jave.support.JDataBinder;
import j.jave.kernal.jave.support.databind.JDataBindingException;
import j.jave.platform.data.common.MethodParamObject;
import j.jave.platform.webcomp.web.youappmvc.HttpContext;

public interface MethodParamObjectBinder extends JDataBinder {
	
	public void bind(MethodParamObject methodParamObject) throws JDataBindingException;
	
	void setHttpContext(HttpContext httpContext);
	
}
