package test.j.jave.kernal.eventdriven;

import org.junit.Test;

import me.bunny.kernel.security.service.JMD5CipherService;

public class TestDataSourceEvent  extends TestEventSupport{
	
	
	@Test
	public void testMD5(){
		JMD5CipherService md5CipherService= serviceHubDelegate.getService(this, JMD5CipherService.class);
		
		String encrp=md5CipherService.encrypt("abc.def.ghi");
		System.out.println(encrp);
		
		TestUserService testUserService= serviceHubDelegate.getService(this,TestUserService.class);
		
		testUserService.save("test data source interceptor");
		String s=testUserService.describer();
		System.out.println(s);
		
		System.out.println("end");
		
		
		
		
	}
	
	
}
