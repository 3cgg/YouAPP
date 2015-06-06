package j.jave.framework.base64;

import j.jave.framework.context.JContext;
import j.jave.framework.exception.JInitializationException;
import j.jave.framework.extension.base64.JBase64;
import j.jave.framework.extension.base64.JIBase64Factory;
import j.jave.framework.reflect.JClassUtils;

public class JBase64Factory {
	
	private static JIBase64Factory base64Factory;
	
	public static JIBase64Factory getBase64Factory() {
		
		if(base64Factory==null){
			synchronized (JBase64Factory.class) {
				if(base64Factory==null){
					try {
						Object obj=JContext.get().getBase64FactoryProvider().getBase64Factory();
						if(obj==null){
							base64Factory=JApachBase64Factory.get();
						}
						else{
							if(JClassUtils.isClass(obj)){
								Class<? extends JIBase64Factory> clazz=(Class<? extends JIBase64Factory>) obj;
								base64Factory=(JIBase64Factory) clazz.newInstance();
							}
							else{
								base64Factory=(JIBase64Factory) obj;
							}
						}
					} catch (InstantiationException e) {
						throw new JInitializationException(e);
					} catch (IllegalAccessException e) {
						throw new JInitializationException(e);
					}
				}
			}
		}
		return base64Factory;
	}
	
	
	public static JBase64 getBase64(){
		return getBase64Factory().getBase64();
	}
	
}
