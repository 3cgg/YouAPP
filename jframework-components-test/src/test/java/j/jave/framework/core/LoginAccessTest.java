/**
 * 
 */
package j.jave.framework.core;

import j.jave.framework.components.core.servicehub.ServiceHubDelegate;
import j.jave.framework.components.login.subhub.LoginAccessService;
import j.jave.framework.io.memory.JStaticMemoryCacheIO;
import junit.framework.Assert;

/**
 * @author J
 */
public class LoginAccessTest extends StandaloneTest {

	private LoginAccessService loginAccessService=
			ServiceHubDelegate.get().getService(this, LoginAccessService.class);
	
	public void testResourceIO(){
		JStaticMemoryCacheIO resourceIO=(JStaticMemoryCacheIO) loginAccessService;
		resourceIO.set();
		System.out.println("OK!");
		Assert.assertTrue(true);
	}
	
	
}
