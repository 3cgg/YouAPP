package j.jave.platform.webcomp.web.youappmvc.bind;

import j.jave.platform.webcomp.web.youappmvc.HttpContext;
import me.bunny.app._c.data.common.MethodParamObject;
import me.bunny.kernel._c.json.JJSON;
import me.bunny.kernel.dataexchange.impl.interimpl.JObjectTransModel;
import me.bunny.kernel.dataexchange.impl.interimpl.JObjectTransModelProtocol;

import java.util.Map;

public class DefaultObjectTransModelBinder implements ObjectTransModelBinder {

	public static final String name="default";
	
	private HttpContext httpContext;

	@Override
	public void bind(MethodParamObject methodParamObject) {

		JObjectTransModel objectTransModel= httpContext.getObjectTransModel();
		Map<String, Object> params= objectTransModel.getParams();
		String name=methodParamObject.getMethodParamMeta().getName();
		int index=methodParamObject.getMethodParamMeta().getIndex();
		Object object= params.get(index+"");
		if(object==null){
			object= params.get(name);
		}
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
						JJSON.get().parse(String.valueOf(object), methodParamObject.getClass())
						);
				break;	
			default:
				break;
			}
		}
	}

	@Override
	public void setHttpContext(HttpContext httpContext) {
		this.httpContext=httpContext;
	}

}
