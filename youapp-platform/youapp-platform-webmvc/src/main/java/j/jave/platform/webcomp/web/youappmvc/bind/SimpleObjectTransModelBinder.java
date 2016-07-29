package j.jave.platform.webcomp.web.youappmvc.bind;

import j.jave.kernal.dataexchange.impl.interimpl.JObjectTransModel;
import j.jave.kernal.dataexchange.impl.interimpl.JObjectTransModelProtocol;
import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.kernal.jave.support.validate.annotationvalidator.annotation.JString;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.data.common.MethodParamObject;
import j.jave.platform.webcomp.core.service.DefaultServiceContext;
import j.jave.platform.webcomp.core.service.ServiceContext;
import j.jave.platform.webcomp.web.youappmvc.HttpContext;

import java.util.Map;

public class SimpleObjectTransModelBinder implements ObjectTransModelBinder {

	/**
	 * binder name
	 */
	public static final String name="simple";
	
	private HttpContext httpContext;

	public static final String serviceContext="serviceContext";
	
	public static final String formData="formData";
	
	public static final String paginationData="paginationData";
	
	@Override
	public void bind(MethodParamObject methodParamObject) {

		JObjectTransModel objectTransModel= httpContext.getObjectTransModel();
		Map<String, Object> params= objectTransModel.getParams();
		String name=methodParamObject.getMethodParamMeta().getName();
		Class<?> paramClass=methodParamObject.getMethodParamMeta().getType();
		JObjectTransModelProtocol protocol=objectTransModel.getProtocol();
		Object object=params.get(name);
		switch (protocol) {
		case OBJECT:
		{
			if(object==null){
				for(Object obj:params.values()){
					if(paramClass.isInstance(obj)){
						object=obj;
						break;
					}
				}
			}
			methodParamObject.setObject(object);
			break;
		}
		case JSON:
		{
			object="";
			if(ServiceContext.class==paramClass){
				object=params.get(serviceContext);
			}
			else if(JSimplePageable.class==paramClass){
				object=params.get(paginationData);
			}else{
				object=params.get(formData);
			}
			Object target=JJSON.get().parse(String.valueOf(object), paramClass);
			if(ServiceContext.class==paramClass&&target==null){
				target=DefaultServiceContext.getDefaultServiceContext();
			}
			methodParamObject.setObject(target);
			break;
		}
		default:
			break;
		}
	
		
	}

	@Override
	public void setHttpContext(HttpContext httpContext) {
		this.httpContext=httpContext;
	}

}
