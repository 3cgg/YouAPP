package j.jave.platform.webcomp.web.youappmvc.bind;

import j.jave.platform.data.common.MethodParamObject;
import j.jave.platform.webcomp.web.youappmvc.HttpContext;
import me.bunny.kernel.jave.support.databind.JDataBindingException;

public class HttpContextWithInnerProtocolDataBinderAdapter implements ObjectTransModelBinder{

	private HttpContext httpContext;
	
	public HttpContextWithInnerProtocolDataBinderAdapter(HttpContext httpContext){
		this.httpContext=httpContext;
	}
	
	public void bind(MethodParamObject methodParamObject)throws JDataBindingException{
		ObjectTransModelBinderFactory.get(httpContext).bind(methodParamObject);
	}

	@Override
	public void setHttpContext(HttpContext httpContext) {
		this.httpContext=httpContext;
	}
	
}
