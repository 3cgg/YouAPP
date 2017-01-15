package j.jave.platform.webcomp.web.youappmvc.bind;

import j.jave.platform.webcomp.core.service.DefaultServiceContext;
import j.jave.platform.webcomp.core.service.ServiceContext;
import j.jave.platform.webcomp.web.youappmvc.HttpContext;
import j.jave.platform.webcomp.web.youappmvc.controller.SimpleControllerSupport;
import me.bunny.app._c.data.common.MethodParamObject;
import me.bunny.kernel._c.json.JJSON;
import me.bunny.kernel._c.model.JSimplePageable;
import me.bunny.kernel.dataexchange.impl.interimpl.JObjectTransModel;
import me.bunny.kernel.dataexchange.impl.interimpl.JObjectTransModelProtocol;

import java.util.Map;

public class SimpleObjectTransModelBinder implements ObjectTransModelBinder {

	/**
	 * binder name
	 */
	public static final String name="simple";
	
	private HttpContext httpContext;

	public static final String serviceContext="_youapp_serviceContext";
	
	public static final String formData="_youapp_formData";
	
	public static final String paginationData="_youapp_paginationData";
	
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
			Object target=null;
			if(ServiceContext.class==paramClass){
				object=params.get(serviceContext);
				target=JJSON.get().parse(String.valueOf(object), ServiceContext.class);
				if(target==null){
					target=DefaultServiceContext.getDefaultServiceContext();
				}
			}
			else if(JSimplePageable.class==paramClass){
				object=params.get(paginationData);
				target=JJSON.get().parse(String.valueOf(object), JSimplePageable.class);
			}else{
				object=params.get(formData);
				if(SimpleControllerSupport.isAcceptedSimpleClass(paramClass)){
					Map<String, Object> map=JJSON.get().parse(String.valueOf(object));
					if(map.size()>0){
						target=map.values().iterator().next();
					}
				}
				else{
					target=JJSON.get().parse(String.valueOf(object), paramClass);
				}
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
