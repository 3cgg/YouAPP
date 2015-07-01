package j.jave.framework.temp.test;

import j.jave.framework.commons.base64.JBase64;
import j.jave.framework.commons.commonsmanager.extension.JExtensionManager;
import j.jave.framework.commons.eventdriven.servicehub.JServiceHubDelegate;

public class AA {

	public static void main(String[] args) {
		
		JExtensionManager extensionManager= JExtensionManager.get();
		System.out.println(extensionManager);
		AA aa=new AA();
		
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
