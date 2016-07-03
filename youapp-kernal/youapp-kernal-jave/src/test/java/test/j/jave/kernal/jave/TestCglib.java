package test.j.jave.kernal.jave;

import j.jave.kernal.jave.proxy.JSimpleProxy;
import junit.framework.TestCase;

public class TestCglib extends TestCase {

	
	
	public void testCglib(){
		AShow testCglib= JSimpleProxy.proxy(this, AShow.class);
		testCglib.show();
		System.out.println("end");
		
		
		
	}
	
	
}
