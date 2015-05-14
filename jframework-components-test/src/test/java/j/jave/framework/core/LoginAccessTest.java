/**
 * 
 */
package j.jave.framework.core;

import j.jave.framework.components.web.subhub.loginaccess.LoginAccessService;
import j.jave.framework.io.memory.JSingleStaticMemoryCacheIO;
import j.jave.framework.servicehub.JServiceHubDelegate;
import junit.framework.Assert;

/**
 * @author J
 */
public class LoginAccessTest extends StandaloneTest {

	private LoginAccessService loginAccessService=
			JServiceHubDelegate.get().getService(this, LoginAccessService.class);
	
	public void testResourceIO(){
		JSingleStaticMemoryCacheIO resourceIO=(JSingleStaticMemoryCacheIO) loginAccessService;
		resourceIO.set();
		System.out.println("OK!");
		Assert.assertTrue(true);
	}
	
	
}
