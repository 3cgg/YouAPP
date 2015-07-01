/**
 * 
 */
package j.jave.framework.core;

import j.jave.framework.commons.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.framework.commons.io.memory.JSingleStaticMemoryCacheIO;
import j.jave.framework.components.web.subhub.loginaccess.LoginAccessService;
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
