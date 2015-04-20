/**
 * 
 */
package j.jave.framework.core;

import j.jave.framework.components.core.servicehub.ServiceHubDelegate;
import j.jave.framework.components.login.ehcache.ResourceIO;
import j.jave.framework.components.login.subhub.LoginAccessService;
import junit.framework.Assert;

/**
 * @author J
 */
public class LoginAccessTest extends StandaloneTest {

	private LoginAccessService loginAccessService=
			new ServiceHubDelegate().getService(this, LoginAccessService.class);
	
	public void testResourceIO(){
		ResourceIO resourceIO=(ResourceIO) loginAccessService;
		resourceIO.set();
		System.out.println("OK!");
		Assert.assertTrue(true);
	}
	
	
}
