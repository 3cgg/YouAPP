package j.jave.platform.webcomp.web.youappmvc.bind;

import j.jave.platform.webcomp.web.youappmvc.HttpContext;
import me.bunny.kernel.dataexchange.impl.interimpl.JObjectTransModel;
import me.bunny.kernel.jave.reflect.JClassUtils;

import java.util.HashMap;
import java.util.Map;

public class ObjectTransModelBinderFactory {
	
	private static Map<String, Class<? extends ObjectTransModelBinder>> binder=new HashMap<String, Class<? extends ObjectTransModelBinder>>();
	static{
		binder.put(SimpleObjectTransModelBinder.name, SimpleObjectTransModelBinder.class);
		binder.put(DefaultObjectTransModelBinder.name, DefaultObjectTransModelBinder.class);
		
	}
	public static ObjectTransModelBinder get(HttpContext httpContext){
		JObjectTransModel objectTransModel=httpContext.getObjectTransModel();
		
		Class<?> clazz=binder.get(objectTransModel.getParser());
		if(clazz!=null){
			ObjectTransModelBinder objectTransModelBinder=(ObjectTransModelBinder) JClassUtils.newObject(clazz);
			objectTransModelBinder.setHttpContext(httpContext);
			return objectTransModelBinder;
		}
		return null;
		
	}
	
	
}
