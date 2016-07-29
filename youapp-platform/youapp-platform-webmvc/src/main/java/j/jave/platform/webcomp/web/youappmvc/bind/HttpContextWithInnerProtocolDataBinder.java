package j.jave.platform.webcomp.web.youappmvc.bind;

import j.jave.kernal.jave.support.JDataBinder;
import j.jave.platform.data.common.MethodParamObject;
import j.jave.platform.webcomp.web.youappmvc.HttpContext;

public class HttpContextWithInnerProtocolDataBinder implements JDataBinder{

	private HttpContext httpContext;
	
	public HttpContextWithInnerProtocolDataBinder(HttpContext httpContext){
		this.httpContext=httpContext;
	}
	
	public void bind(MethodParamObject methodParamObject){
		ObjectTransModelBinderFactory.get(httpContext).bind(methodParamObject);
	}
	
}
