package j.jave.framework.temp.test;

import j.jave.framework.base64.JBase64ServiceFactory;
import j.jave.framework.extension.JExtensionManager;
import j.jave.framework.extension.base64.JBase64;
import j.jave.framework.servicehub.JServiceHubDelegate;

public class AA {

	public static void main(String[] args) {
		
		JExtensionManager extensionManager= JExtensionManager.get();
		System.out.println(extensionManager);
		AA aa=new AA();
		JServiceHubDelegate.get().register(aa, JBase64.class, new JBase64ServiceFactory());
		
//		JServiceHub serviceHub= JServiceHubDelegate.get().getService(aa, JServiceHub.class);
//		
////		JServiceHubDelegate.get().addImmediateEvent(new JServiceUninstallEvent(aa, JServiceHub.class));
////		serviceHub= JServiceHubDelegate.get().getService(aa, JServiceHub.class);
//		
//		
////		JServiceHubDelegate.get().addImmediateEvent(new JServiceListenerDisableEvent(aa, JServiceHub.class, JServiceUninstallListener.class));
//		
//		JServiceHubDelegate.get().addImmediateEvent(new JServiceUninstallEvent(aa, JServiceHub.class));
//		
////		JServiceHubDelegate.get().addImmediateEvent(new JServiceInstallEvent(aa, JServiceHub.class));
//		
//		serviceHub= JServiceHubDelegate.get().getService(aa, JServiceHub.class);
//		
//		System.out.println(serviceHub);
		
		
		
	}
}
