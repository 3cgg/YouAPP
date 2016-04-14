package test.com.youappcorp.project.usermanager;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.random.JSimpleObjectPopulate;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.platform.basicsupportcomp.support.security.subhub.DESedeCipherService;
import j.jave.platform.basicwebcomp.core.service.DefaultServiceContext;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.youappcorp.project.usermanager.model.User;
import com.youappcorp.project.usermanager.service.UserService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml"})
public class TestUserManager {
	
	@Autowired
	private UserService userService;
	
	private DESedeCipherService deSedeCipherService=
			JServiceHubDelegate.get().getService(this, DESedeCipherService.class);
	
	@Test
	public void testSaveUser(){
		
		ServiceContext context=DefaultServiceContext.getDefaultServiceContext();
		User user=new User();
		try {
			new JSimpleObjectPopulate().populate(user);
			user.setId(JUniqueUtils.unique().replaceAll("-", ""));
			user.setUserName("N");
			user.setPassword(deSedeCipherService.encrypt("P"));
			userService.saveUser(context, user);
			System.out.println("end");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		
	}
	
}