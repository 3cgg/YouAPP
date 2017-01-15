package test.com.youappcorp.project.usermanager;

import me.bunny.app._c._web.core.service.DefaultServiceContext;
import me.bunny.app._c._web.core.service.ServiceContext;
import me.bunny.app._c.sps.support.security.subhub.DESedeCipherService;
import me.bunny.kernel._c.support.random.JSimpleObjectRandomBinder;
import me.bunny.kernel._c.utils.JUniqueUtils;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.youappcorp.project.usermanager.model.User;
import com.youappcorp.project.usermanager.service.DefaultUserManagerServiceImpl;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml"})
public class TestUserManager {
	
	@Autowired
	private DefaultUserManagerServiceImpl userManagerService;
	
	private DESedeCipherService deSedeCipherService=
			JServiceHubDelegate.get().getService(this, DESedeCipherService.class);
	
	@Test
	public void testSaveUser(){
		
		ServiceContext context=DefaultServiceContext.getDefaultServiceContext();
		User user=new User();
		try {
			new JSimpleObjectRandomBinder().bind(user);
			user.setId(JUniqueUtils.unique().replaceAll("-", ""));
			user.setUserName("N");
			user.setPassword(deSedeCipherService.encrypt("P"));
			userManagerService.saveUser( user);
			System.out.println("end");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		
	}
	
}
