package j.jave.platform.webcomp.web.youappmvc.bind;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.support.databind.JDataBindingException;
import j.jave.kernal.jave.support.parser.JDefaultSimpleDataParser;
import j.jave.platform.data.common.MethodParamMeta;
import j.jave.platform.data.common.MethodParamObject;
import j.jave.platform.webcomp.web.youappmvc.HttpContext;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

public class HttpContextDataBinder implements MethodParamObjectBinder{

	private HttpContext httpContext;
	
	public HttpContextDataBinder(HttpContext httpContext){
		this.httpContext=httpContext;
	}
	
	public void bind(MethodParamObject methodParamObject) throws JDataBindingException{
		
		Annotation[] annotations=methodParamObject.getMethodParamMeta().getAnnotations();
		String prefix=null;
		for(Annotation annotation:annotations){
			if(RequestParam.class.isInstance(annotation)){
				prefix=((RequestParam)annotation).value();
				break;
			}
		}
		MethodParamMeta methodParamMeta= methodParamObject.getMethodParamMeta();
		Class<?> clazz=methodParamMeta.getType();
		String paramName=methodParamMeta.getName();
		JDefaultSimpleDataParser dataParser=JDefaultSimpleDataParser.build(JConfiguration.get());
		try{
			if(Map.class.isAssignableFrom(clazz)){
				methodParamObject.setObject(httpContext.getParameters());
			}
			else if(JClassUtils.isSimpleTypeArray(clazz)){
				methodParamObject.setObject(dataParser.parse(clazz, httpContext.getParameterValue(paramName)));
			}
			else if(isSimpleType(clazz)){
				methodParamObject.setObject(dataParser.parse(clazz, httpContext.getParameterValue(paramName)));
			}
			else{
				SimpleRequestParamBinder requestParamPopulate=new SimpleRequestParamBinder(prefix, httpContext);
				newObject(methodParamObject);
				requestParamPopulate.bind(methodParamObject);
			}
		}catch(Exception e){
			throw new JDataBindingException(e);
		}
	}
	
	
	private void newObject(MethodParamObject methodParamObject){
		MethodParamMeta methodParamMeta= methodParamObject.getMethodParamMeta();
		Class<?> clazz=methodParamMeta.getType();
		methodParamObject.setObject(JClassUtils.newObject(clazz));
	}
	
	
	private boolean isSimpleType(Class<?> clazz){
		return JClassUtils.isSimpleType(clazz);
	}

	@Override
	public void setHttpContext(HttpContext httpContext) {
		this.httpContext=httpContext;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
