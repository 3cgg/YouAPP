package j.jave.platform.basicwebcomp.web.proext;

import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.platform.basicwebcomp.web.proext.annotation.CodeExtend;


public class CodeExtendBinder extends PropertyExtendBinder {

	private static String getKey(String codeType,String code){
		return codeType+"\\/\\"+code;
	}
	
	@Override
	protected void doBind(PropertyExtendModel propertyExtendModel) {
		CodeExtend codeExtend=propertyExtendModel.getCodeExtend();
		Object object=propertyExtendModel.getObject();
		String property=codeExtend.property();
		Object code=JClassUtils.getByField(property, object, false);
		String codeType=codeExtend.codeType();
		Object value=cacheService.get(getKey(codeType, String.valueOf(code)));
		JClassUtils.setOnField(propertyExtendModel.getField(), value, object);
	}

}
