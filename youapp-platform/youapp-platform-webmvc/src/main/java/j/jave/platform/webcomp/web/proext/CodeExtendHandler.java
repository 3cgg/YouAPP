package j.jave.platform.webcomp.web.proext;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.support.databind.proext.JPropertyExtendHandler;
import j.jave.platform.webcomp.web.cache.resource.coderef.CodeRefCacheModel;
import j.jave.platform.webcomp.web.cache.resource.coderef.CodeRefCacheService;
import j.jave.platform.webcomp.web.proext.annotation.CodeExtend;

import java.lang.reflect.Field;

public class CodeExtendHandler implements JPropertyExtendHandler {
	
	@SuppressWarnings("unchecked")
	private CodeRefCacheService<CodeRefCacheModel> codeRefCacheService=
			JServiceHubDelegate.get().getService(this, CodeRefCacheService.class);
	
	@Override
	public boolean accept(Field field, Object object) {
		boolean needPropertyExtend=false;
		CodeExtend codeExtend=field.getAnnotation(CodeExtend.class);
		needPropertyExtend=needPropertyExtend||(codeExtend!=null&&codeExtend.active());
		return needPropertyExtend;
	}
	
	@Override
	public Object handle(Field field, Object object) {
		CodeExtend codeExtend=field.getAnnotation(CodeExtend.class);
		String property=codeExtend.property();
		Object code=JClassUtils.getByField(property, object, false);
		String codeType=codeExtend.codeType();
		Object name=codeRefCacheService.getName(codeType, (String) code);
		JClassUtils.setOnField(field, name, object);
		return null;
	}
	
}
