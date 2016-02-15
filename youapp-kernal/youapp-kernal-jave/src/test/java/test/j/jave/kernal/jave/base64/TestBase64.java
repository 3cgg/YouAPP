package test.j.jave.kernal.jave.base64;

import j.jave.kernal.jave.base64.JBase64FactoryProvider;
import j.jave.kernal.jave.utils.JAssert;
import junit.framework.TestCase;

public class TestBase64 extends TestCase{

	
	
	public void testBase64(){
		try{
			String string="jia.zhong.jin-贾中进-jzj";
			String base64String=JBase64FactoryProvider.getBase64Factory().getBase64().encodeBase64String(string.getBytes("utf-8"));
			System.out.println(base64String);
			String plainString=new String(JBase64FactoryProvider.getBase64Factory().getBase64().decodeBase64(base64String), "utf-8");
			System.out.println(plainString);
			JAssert.state(string.equals(plainString), "error : ");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
}
