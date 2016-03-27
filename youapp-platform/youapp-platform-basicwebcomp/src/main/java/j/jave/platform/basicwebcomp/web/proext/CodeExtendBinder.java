package j.jave.platform.basicwebcomp.web.proext;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.platform.basicwebcomp.web.cache.resource.coderef.CodeRefCacheModel;
import j.jave.platform.basicwebcomp.web.cache.resource.coderef.CodeRefCacheService;
import j.jave.platform.basicwebcomp.web.proext.annotation.CodeExtend;


public class CodeExtendBinder extends PropertyExtendBinder {

	@SuppressWarnings("unchecked")
	private CodeRefCacheService<CodeRefCacheModel> codeRefCacheService=
			JServiceHubDelegate.get().getService(this, CodeRefCacheService.class);
	
	@Override
	protected void doBind(PropertyExtendModel propertyExtendModel) {
		CodeExtend codeExtend=propertyExtendModel.getCodeExtend();
		Object object=propertyExtendModel.getObject();
		String property=codeExtend.property();
		Object code=JClassUtils.getByField(property, object, false);
		String codeType=codeExtend.codeType();
		Object name=codeRefCacheService.getName(codeType, (String) code);
		JClassUtils.setOnField(propertyExtendModel.getField(), name, object);
	}

}
