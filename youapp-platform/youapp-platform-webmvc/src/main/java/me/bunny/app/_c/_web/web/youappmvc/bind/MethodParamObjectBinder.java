package me.bunny.app._c._web.web.youappmvc.bind;

import me.bunny.app._c._web.web.youappmvc.HttpContext;
import me.bunny.app._c.data.common.MethodParamObject;
import me.bunny.kernel._c.support.JDataBinder;
import me.bunny.kernel._c.support.databind.JDataBindingException;

public interface MethodParamObjectBinder extends JDataBinder {
	
	public void bind(MethodParamObject methodParamObject) throws JDataBindingException;
	
	void setHttpContext(HttpContext httpContext);
	
}
