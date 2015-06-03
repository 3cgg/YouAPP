package j.jave.framework.base64;

import j.jave.framework.extension.base64.JBase64;
import j.jave.framework.extension.base64.JIBase64Factory;


class JApachBase64Factory implements JIBase64Factory {

	private static JApachBase64Factory apachBase64Factory;
	
	private JApachBase64Factory() {
	}
	
	static JApachBase64Factory get(){
		if(apachBase64Factory==null){
			synchronized (JApachBase64Factory.class) {
				if(apachBase64Factory==null){
					apachBase64Factory=new JApachBase64Factory();
				}
			}
		}
		return apachBase64Factory;
	}
	
	@Override
	public JBase64 getBase64() {
		return new JBase64Wrapper();
	}


}
