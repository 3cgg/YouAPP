package j.jave.platform.basicwebcomp.access.subhub;

import j.jave.securityutil.securityclient.JRSSecurityHelper;

public class RemoteSecurityService {

	public String encryptOnDESede(String plain) throws Exception {
		return JRSSecurityHelper.encryptOnDESede(plain);
	}

	public String decryptOnDESede(String encrypted) throws Exception{
		return JRSSecurityHelper.decryptOnDESede(encrypted);
	}
	
	public String encryptOnMD5(String plain) throws Exception{
		return JRSSecurityHelper.encryptOnMD5(plain);
	}

	private RemoteSecurityService() {
	}
	
	private static RemoteSecurityService instance=new RemoteSecurityService();
	
	public static RemoteSecurityService get(){
		return instance;
	}
	
	
}
