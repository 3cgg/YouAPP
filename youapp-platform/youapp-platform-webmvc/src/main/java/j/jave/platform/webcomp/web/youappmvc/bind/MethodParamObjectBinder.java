package j.jave.platform.webcomp.web.youappmvc.bind;

import j.jave.platform.data.common.MethodParamObject;
import j.jave.platform.webcomp.web.youappmvc.HttpContext;
import me.bunny.kernel.jave.support.JDataBinder;
import me.bunny.kernel.jave.support.databind.JDataBindingException;

public interface MethodParamObjectBinder extends JDataBinder {
	
	public void bind(MethodParamObject methodParamObject) throws JDataBindingException;
	
	void setHttpContext(HttpContext httpContext);
	
}
