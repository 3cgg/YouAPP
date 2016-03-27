package j.jave.platform.basicwebcomp.web.proext;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.platform.basicwebcomp.web.cache.resource.obectref.ObjectRefCacheService;
import j.jave.platform.basicwebcomp.web.proext.annotation.ObjectExtend;


public class ObjectExtendBinder extends PropertyExtendBinder {

	private ObjectRefCacheService objectRefCacheService
	=JServiceHubDelegate.get().getService(this, ObjectRefCacheService.class);
	
	
	@Override
	protected void doBind(PropertyExtendModel propertyExtendModel) {
		ObjectExtend objectExtend=propertyExtendModel.getObjectExtend();
		Object object=propertyExtendModel.getObject();
		String property=objectExtend.property();
		Object identifier=JClassUtils.getByField(property, object, false);
		Object value=objectRefCacheService.get((String) identifier);
		JClassUtils.setOnField(propertyExtendModel.getField(), value, object);
	}

}
