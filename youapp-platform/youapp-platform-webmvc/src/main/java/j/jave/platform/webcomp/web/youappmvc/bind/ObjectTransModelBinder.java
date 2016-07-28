package j.jave.platform.webcomp.web.youappmvc.bind;

import j.jave.kernal.jave.support.JDataBinder;
import j.jave.platform.data.common.MethodParamObject;
import j.jave.platform.webcomp.web.youappmvc.HttpContext;

public interface ObjectTransModelBinder extends JDataBinder {
	
	public void bind(MethodParamObject methodParamObject);
	
	void setHttpContext(HttpContext httpContext);
	
}
