package j.jave.platform.webcomp.web.youappmvc.bind;

import j.jave.kernal.dataexchange.impl.interimpl.JObjectTransModel;
import j.jave.kernal.dataexchange.impl.interimpl.JObjectTransModelProtocol;
import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.support.JDataBinder;
import j.jave.platform.data.common.MethodParamObject;
import j.jave.platform.webcomp.web.youappmvc.HttpContext;

import java.util.Map;

public class HttpContextWithInnerProtocolDataBinder implements JDataBinder{

	private HttpContext httpContext;
	
	public HttpContextWithInnerProtocolDataBinder(HttpContext httpContext){
		this.httpContext=httpContext;
	}
	
	public void bind(MethodParamObject methodParamObject){
		JObjectTransModel objectTransModel= httpContext.getObjectTransModel();
		Map<String, Object> params= objectTransModel.getParams();
		String name=methodParamObject.getMethodParamMeta().getName();
		Object object= params.get(name);
		if(object==null){
			methodParamObject.setObject(object);
		}
		else{
			JObjectTransModelProtocol protocol=objectTransModel.getProtocol();
			switch (protocol) {
			case OBJECT:
				methodParamObject.setObject(object);
				break;
			case JSON:
				methodParamObject.setObject(
						JJSON.get().parse(JJSON.get().formatObject(object), methodParamObject.getClass())
						);
				break;	
			default:
				break;
			}
		}
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
