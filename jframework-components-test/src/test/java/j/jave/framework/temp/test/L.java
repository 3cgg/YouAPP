package j.jave.framework.temp.test;

import j.jave.framework.base64.JBase64ServiceFactory;
import j.jave.framework.extension.base64.JBase64;
import j.jave.framework.servicehub.JServiceHubDelegate;

public class L {

	
	public static void main(String[] args) {
		
		JServiceHubDelegate.get().register(new L(), JBase64.class, new JBase64ServiceFactory());
		
		JBase64 base64=JServiceHubDelegate.get().getService(new L(), JBase64.class);
		
		System.out.println(base64);
		
	}
}
