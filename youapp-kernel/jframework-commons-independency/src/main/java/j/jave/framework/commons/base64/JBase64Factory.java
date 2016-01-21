package j.jave.framework.commons.base64;

import j.jave.framework.commons.context.JCommonsIndependencyContext;
import j.jave.framework.commons.exception.JInitializationException;

public class JBase64Factory {
	
	private static JIBase64Factory base64Factory;
	
	public static JIBase64Factory getBase64Factory() {
		
		if(base64Factory==null){
			synchronized (JBase64Factory.class) {
				if(base64Factory==null){
					try {
						
						boolean getBase64=false;
						
						// get custom base64
						JIBase64Factory base64Factory=JCommonsIndependencyContext.get().getBase64FactoryProvider().getBase64Factory();
						if(testBase64(base64Factory)){
							getBase64=true;
						}
						
						//get Apache base64  
						if(!getBase64){
							base64Factory=JApachBase64Factory.get();
							if(testBase64(base64Factory)){
								getBase64=true;
							}
						}
						//get system base64
//						if(!getBase64){
//							base64Factory=JApachBase64Factory.get();
//							if(testBase64(base64Factory)){
//								getBase64=true;
//							}
//						}
						
						if(getBase64){
							JBase64Factory.base64Factory=base64Factory;
						}
						else{
							System.out.println("Base64 not found.");
						}
					} catch (Exception e) {
						throw new JInitializationException(e);
					}
				}
			}
		}
		return base64Factory;
	}


	private static boolean testBase64(JIBase64Factory base64Factory) {
		return base64Factory!=null&&base64Factory.available();
	}
	
	
	public static JBase64 getBase64(){
		return getBase64Factory().getBase64();
	}
	
}
