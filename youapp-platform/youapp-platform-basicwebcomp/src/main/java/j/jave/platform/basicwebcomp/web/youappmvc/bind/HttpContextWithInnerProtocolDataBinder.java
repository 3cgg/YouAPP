package j.jave.platform.basicwebcomp.web.youappmvc.bind;

import j.jave.kernal.dataexchange.protocol.JObjectTransModel;
import j.jave.kernal.dataexchange.protocol.JObjectWrapper;
import j.jave.platform.basicwebcomp.web.util.MethodParamObject;
import j.jave.platform.basicwebcomp.web.youappmvc.HttpContext;

import java.util.List;

public class HttpContextWithInnerProtocolDataBinder extends DataBinder{

	private HttpContext httpContext;
	
	public HttpContextWithInnerProtocolDataBinder(HttpContext httpContext){
		this.httpContext=httpContext;
	}
	
	public void bind(MethodParamObject methodParamObject){
		JObjectTransModel objectTransModel= httpContext.getObjectTransModel();
		List<JObjectWrapper> objectWrappers=objectTransModel.getObjectWrappers();
		methodParamObject.setObject(objectWrappers.get(methodParamObject.getMethodParamMeta().getIndex()).getObject());
	}
	
//	private void newObject(MethodParamObject methodParamObject){
//		MethodParamMeta methodParamMeta= methodParamObject.getMethodParamMeta();
//		Class<?> clazz=methodParamMeta.getType();
//		methodParamObject.setObject(JClassUtils.newObject(clazz));
//	}
//	private boolean isSimpleType(Class<?> clazz){
//		return JClassUtils.isSimpleType(clazz);
//	}
	
	
}
