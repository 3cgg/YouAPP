package j.jave.securityutil.securityclient;

import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.securityutil.securityclient.endpoint.JSecurityServiceEndpoint;


public class JRSSecurityHelper {
	
	public static String encryptOnDESede(String plain) throws Exception{
		Object obj=JRSClient.get().get(fullPath(JRSUtils.replace(JSecurityServiceEndpoint.encryptOnDESede, plain)));
		return JStringUtils.toString(obj);
	} 
	
	public static String decryptOnDESede(String encrypted) throws Exception{
		Object obj=JRSClient.get().get(fullPath(JRSUtils.replace(JSecurityServiceEndpoint.decryptOnDESede,encrypted)));
		return JStringUtils.toString(obj);
	} 
	
	public static String encryptOnMD5(String plain) throws Exception{
		Object obj=JRSClient.get().post(fullPath(JSecurityServiceEndpoint.encryptOnMD5), plain);
		return JStringUtils.toString(obj);
	} 
	
	private static final String fullPath(String relativePath){
		return JSecurityServiceEndpoint.prefix+"/"+relativePath;
	}
	
	
}
