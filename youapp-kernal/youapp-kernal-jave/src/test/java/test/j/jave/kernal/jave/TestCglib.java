package test.j.jave.kernal.jave;

import junit.framework.TestCase;
import me.bunny.kernel.jave.proxy.JSimpleProxy;

public class TestCglib extends TestCase {

	
	
	public void testCglib(){
		AShow testCglib= JSimpleProxy.proxy(this, AShow.class);
		testCglib.show();
		System.out.println("end");
		
		
		
	}
	
	
}
