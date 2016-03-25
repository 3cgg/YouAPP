package j.jave.platform.basicwebcomp.web.proext;

import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.platform.basicwebcomp.web.proext.annotation.ObjectExtend;


public class ObjectExtendBinder extends PropertyExtendBinder {

	private static String getKey(Object identifier){
		return String.valueOf(identifier);
	}
	
	@Override
	protected void doBind(PropertyExtendModel propertyExtendModel) {
		ObjectExtend objectExtend=propertyExtendModel.getObjectExtend();
		Object object=propertyExtendModel.getObject();
		String property=objectExtend.property();
		Object identifier=JClassUtils.getByField(property, object, false);
		Object value=cacheService.get(getKey(identifier));
		JClassUtils.setOnField(propertyExtendModel.getField(), value, object);
	}

}
