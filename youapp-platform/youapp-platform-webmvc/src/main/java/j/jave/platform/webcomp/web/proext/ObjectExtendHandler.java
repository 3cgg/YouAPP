package j.jave.platform.webcomp.web.proext;

import j.jave.platform.webcomp.web.cache.resource.obectref.ObjectRefCacheService;
import j.jave.platform.webcomp.web.proext.annotation.ObjectExtend;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;
import me.bunny.kernel.jave.reflect.JClassUtils;
import me.bunny.kernel.jave.support.databind.proext.JPropertyExtendHandler;

import java.lang.reflect.Field;

public class ObjectExtendHandler implements JPropertyExtendHandler {
	
	private ObjectRefCacheService objectRefCacheService
	=JServiceHubDelegate.get().getService(this, ObjectRefCacheService.class);
	
	@Override
	public boolean accept(Field field, Object object) {
		boolean needPropertyExtend=false;
		ObjectExtend objectExtend=field.getAnnotation(ObjectExtend.class);
		needPropertyExtend=needPropertyExtend||(objectExtend!=null&&objectExtend.active());
		return needPropertyExtend;
	}
	
	@Override
	public Object handle(Field field, Object object) {
		ObjectExtend objectExtend=field.getAnnotation(ObjectExtend.class);
		String property=objectExtend.property();
		Object identifier=JClassUtils.getByField(property, object, false);
		Object value=objectRefCacheService.get((String) identifier);
		JClassUtils.setOnField(field, value, object);
		return null;
	}
}
