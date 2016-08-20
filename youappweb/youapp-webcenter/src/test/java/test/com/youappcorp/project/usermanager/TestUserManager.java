package test.com.youappcorp.project.usermanager;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.support.random.JSimpleObjectRandomBinder;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.platform.sps.support.security.subhub.DESedeCipherService;
import j.jave.platform.webcomp.core.service.DefaultServiceContext;
import j.jave.platform.webcomp.core.service.ServiceContext;

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
			userManagerService.saveUser(context, user);
			System.out.println("end");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		
	}
	
}
