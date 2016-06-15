package j.jave.platform.webcomp.web.youappmvc.bind;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.support.JDataBinder;
import j.jave.kernal.jave.support.databind.JDataBindException;
import j.jave.kernal.jave.support.dataconvert.JDataConvertor;
import j.jave.platform.data.common.MethodParamMeta;
import j.jave.platform.data.common.MethodParamObject;
import j.jave.platform.webcomp.web.youappmvc.HttpContext;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

public class HttpContextDataBinder implements JDataBinder{

	private HttpContext httpContext;
	
	public HttpContextDataBinder(HttpContext httpContext){
		this.httpContext=httpContext;
	}
	
	public void bind(MethodParamObject methodParamObject){
		
		Annotation[] annotations=methodParamObject.getMethodParamMeta().getAnnotations();
		int count=0;
		String prefix=null;
		for(Annotation annotation:annotations){
			if(RequestParam.class.isInstance(annotation)){
				prefix=((RequestParam)annotation).value();
				count++;
			}
		}
		MethodParamMeta methodParamMeta= methodParamObject.getMethodParamMeta();
		Class<?> clazz=methodParamMeta.getType();
		String paramName=methodParamMeta.getName();
		JDataConvertor dataConvertor=JDataConvertor.build(JConfiguration.get());
		try{
			if(Map.class.isAssignableFrom(clazz)){
				methodParamObject.setObject(httpContext.getParameters());
			}
			else if(JClassUtils.isSimpleTypeArray(clazz)){
				methodParamObject.setObject(dataConvertor.convert(clazz, httpContext.getParameterValue(paramName)));
			}
			else if(isSimpleType(clazz)){
				methodParamObject.setObject(dataConvertor.convert(clazz, httpContext.getParameterValue(paramName)));
			}
			else{
				RequestParamPopulate requestParamPopulate=new RequestParamPopulate(prefix, httpContext);
				newObject(methodParamObject);
				requestParamPopulate.bind(methodParamObject.getObject());
			}
		}catch(Exception e){
			throw new JDataBindException(e);
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
	
	
	
	
	
	
	
	
	
	
	
	
}
