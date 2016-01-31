package test.j.jave.framework.inner.support.rs;

import java.io.File;

import j.jave.kernal.jave.io.JFile;
import j.jave.kernal.jave.utils.JFileUtils;
import j.jave.securityutil.securityclient.JRSSecurityHelper;

public class A {

	public static void main(String[] args) throws Exception {
		
		String s=JRSSecurityHelper.encryptOnDESede("aaaaaaaaaee9977777");
		System.out.println(s);
		s=JRSSecurityHelper.decryptOnDESede(s);
		System.out.println(s);
		
		s=JRSSecurityHelper.encryptOnMD5(s);
		System.out.println(s);
		
		byte[] bytes=JFileUtils.getBytes(new File("D:\\java_\\JFramework1.1\\trunk\\jframework-inner-support-ws-client\\src\\test\\java\\test\\j\\jave\\framework\\inner\\support\\rs\\A.java"));
		
		s=JRSSecurityHelper.encryptOnMD5(new String(bytes));
		
		System.out.println("md5--->"+s);
		
		
		
		
		
		
		
		
	}
	
	
}
